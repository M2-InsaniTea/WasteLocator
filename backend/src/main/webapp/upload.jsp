<%@ page import="com.google.appengine.api.blobstore.BlobstoreServiceFactory" %>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreService" %>

<%
    BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
%>


<html>
    <head>
        <title>Upload Test</title>
    </head>
    <body>
        <form action="<%= blobstoreService.createUploadUrl("/upload") %>" method="post" enctype="multipart/form-data">
            Tag: <input type="text" name="tagIds[]"><br/>
            User: <input type="text" name="userId"><br/>
            Latitude: <input type="text" name="latitude"><br/>
            Longitude: <input type="text" name="longitude"><br/>
            Photo: <input type="file" name="photo"><br/>
            <input type="submit" value="Submit">
        </form>
    </body>
</html>