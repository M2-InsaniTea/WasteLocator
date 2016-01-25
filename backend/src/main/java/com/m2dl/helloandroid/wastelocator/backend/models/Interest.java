package com.m2dl.helloandroid.wastelocator.backend.models;

import com.google.appengine.api.datastore.GeoPt;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Parent;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by flemoal on 21/01/16.
 */
@Entity
public class Interest {
    @Id
    private Long id;

    @Parent
    Key<UserAccount> submitter;
    public Interest() {
    }

    private String photoId;

    private Set<GeoPt> locations = new HashSet<>();

    private Set<Key<Tag>> tags = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Set<Key<Tag>> getTags() {
        return tags;
    }

    public void setTags(Set<Key<Tag>> tags) {
        this.tags = tags;
    }

    public Set<GeoPt> getLocations() {
        return locations;
    }

    public void setLocations(Set<GeoPt> locations) {
        this.locations = locations;
    }

    public Interest addLocation(GeoPt geoPt) {
        locations.add(geoPt);
        return this;
    }

    public Interest addLocation(double latitude, double longitude) {
        return addLocation(new GeoPt((float) latitude, (float) longitude));
    }

    public Interest addTag(Key<Tag> tagKey) {
        tags.add(tagKey);
        return this;
    }

}
