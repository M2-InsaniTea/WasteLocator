package com.m2dl.helloandroid.wastelocator.backend.models;

import com.google.appengine.api.datastore.GeoPt;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Parent;

/**
 * Created by flemoal on 21/01/16.
 */
@Entity
public class Interest {
    @Id
    private Long key;

    @Parent
    Key<UserAccount> submitter;

    public Interest(String photoId, GeoPt[] locations) {
        this.photoId = photoId;
        this.locations = locations;
    }

    public Interest() {
    }

    private String photoId;

    private GeoPt[] locations;

    private Key<Tag>[] tags;

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    public Key<UserAccount> getSubmitter() {
        return submitter;
    }

    public void setSubmitter(Key<UserAccount> submitter) {
        this.submitter = submitter;
    }

    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

    public Key<Tag>[] getTags() {
        return tags;
    }

    public void setTags(Key<Tag>[] tags) {
        this.tags = tags;
    }

    public GeoPt[] getLocations() {
        return locations;
    }

    public void setLocations(GeoPt[] locations) {
        this.locations = locations;
    }
}
