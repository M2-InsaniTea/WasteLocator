package com.m2dl.helloandroid.wastelocator.backend.apis;

/**
 * Created by flemoal on 26/01/16.
 */

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.blobstore.FileInfo;
import com.google.appengine.api.datastore.GeoPt;
import com.m2dl.helloandroid.wastelocator.backend.models.Interest;
import com.m2dl.helloandroid.wastelocator.backend.models.Tag;
import com.m2dl.helloandroid.wastelocator.backend.models.UserAccount;

import static com.m2dl.helloandroid.wastelocator.backend.OfyService.ofy;

public class UploadServlet extends HttpServlet {
    private static final String PARAM_TAG_IDS = "tagIds[]";
    private static final String PARAM_USER_ID = "userId";
    private static final String PARAM_LATITUDE = "latitude";
    private static final String PARAM_LONGITUDE = "longitude";
    private static final String PARAM_PHOTO = "photo";

    private static final List<String> EXPECTED_ARGS = Arrays.asList(PARAM_TAG_IDS, PARAM_USER_ID, PARAM_LATITUDE, PARAM_LONGITUDE);

    private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        if (!req.getParameterMap().keySet().containsAll(EXPECTED_ARGS)) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing args");
            return;
        }

        String[] tagIdsRow = req.getParameterValues(PARAM_TAG_IDS);
        String userIdRow = req.getParameter(PARAM_USER_ID);
        String latitudeRow = req.getParameter(PARAM_LATITUDE);
        String longitudeRow = req.getParameter(PARAM_LONGITUDE);

        List<Long> tagIds = new LinkedList<>();
        try {
            for (String s : tagIdsRow) tagIds.add(Long.parseLong(s));
        } catch (NumberFormatException nfe) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid tag");
            return;
        }

        Long userId = null;
        try {
            userId = Long.parseLong(userIdRow);
        } catch (NumberFormatException nfe) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid user id");
            return;
        }

        GeoPt geoPt = null;
        try {
            Float latitude = Float.parseFloat(latitudeRow);
            Float longitude = Float.parseFloat(longitudeRow);
            geoPt = new GeoPt(latitude, longitude);
        } catch (IllegalArgumentException e) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid latitude or longitude id");
            return;
        }
        List<FileInfo> fileInfos = blobstoreService.getFileInfos(req).get(PARAM_PHOTO);
        if (fileInfos == null || fileInfos.isEmpty()) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "No photo provided");
            return;
        } else {
            FileInfo fileInfo = blobstoreService.getFileInfos(req).get("myFile").get(0);
            String filename = fileInfo.getFilename();
            int i = filename.lastIndexOf('.');
            String extension = null;
            if (i > 0) {
                extension = filename.substring(i + 1);
            }

            if (extension == null || !extension.equals("jpg")) {
                res.sendError(HttpServletResponse.SC_BAD_REQUEST, "JPG expected for the photo");
                return;
            }
        }

        Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);
        List<BlobKey> blobKeys = blobs.get(PARAM_PHOTO);

        BlobKey photoKey;
        if (blobKeys == null || blobKeys.isEmpty()) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "No photo saved");
            return;
        } else {
            photoKey = blobKeys.get(0);
        }

        UserAccount user = null;
        try {
            user = ofy().load().type(UserAccount.class).id(userId).now();
        } catch (Exception e) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid user id");
            return;
        }

        if (user == null) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown user");
            return;
        }

        Map<Long, Tag> tagMap = null;
        try {
            tagMap = ofy().load().type(Tag.class).ids(tagIds);
        } catch (Exception e) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid tag id");
            return;
        }

        if (tagMap == null || tagMap.isEmpty()) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "No tag provided");
            return;
        }

        Interest newInterest = new Interest();
        newInterest.addLocation(geoPt);
        newInterest.setSubmitter(user);
        newInterest.setPhoto(photoKey);
        for (Tag tag : tagMap.values()) {
            newInterest.addTag(tag);
        }

        ofy().save().entity(newInterest).now();
        res.setContentType("text/plain");
        res.getWriter().print("Photo uploaded");
    }
}

