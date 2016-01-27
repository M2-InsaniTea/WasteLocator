package com.m2dl.helloandroid.wastelocator.backend.apis;

/**
 * Created by flemoal on 26/01/16.
 */

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.m2dl.helloandroid.wastelocator.backend.models.Interest;

import static com.m2dl.helloandroid.wastelocator.backend.OfyService.ofy;

public class ServeServlet extends HttpServlet {
    private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException {
        String interestIdRaw = req.getParameter("interestId");
        Long interestId = null;
        try {
            interestId = Long.parseLong(interestIdRaw);
        } catch (NumberFormatException iea) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid interest id");
            return;
        }

        Interest interest = null;
        try {
            interest = ofy().load().type(Interest.class).id(interestId).now();
        } catch (Exception e) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid interest id");
            return;
        }

        if (interest == null) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown interest");
            return;
        }

        BlobKey blobKey = new BlobKey(interest.getPhoto().getKeyString());
        blobstoreService.serve(blobKey, res);
    }
}

