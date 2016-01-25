package com.m2dl.helloandroid.wastelocator.backend.models;

import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.ApiResourceProperty;
import com.google.appengine.api.datastore.GeoPt;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.Load;
import com.googlecode.objectify.annotation.OnLoad;
import com.googlecode.objectify.annotation.Parent;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

/**
 * Created by flemoal on 21/01/16.
 */
@Entity
public class Interest {
    @Id
    private Long id;

    @Load
    private Ref<UserAccount> submitter;

    public Interest() {
    }

    @Ignore List<Long> tagIds = new LinkedList<>();
    @OnLoad
    void loadTagIds()
    {
        tagIds = Lists.transform(new LinkedList<>(tags), new Function<Ref<Tag>, Long>() {

            @Override
            public Long apply(Ref<Tag> input) {
                return input.getKey().getId();
            }
        });
    }

    private String photoId;

    private Set<GeoPt> locations = new HashSet<>();

    @Load
    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    private Set<Ref<Tag>> tags = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserAccount getSubmitter() {
        return submitter == null ? null : submitter.get();
    }

    public void setSubmitter(UserAccount submitter) {
        this.submitter = Ref.create(submitter);
    }

    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

    public Set<Ref<Tag>> getTags() {
        return tags;
    }

    public void setTags(Set<Ref<Tag>> tags) {
        this.tags = tags;
    }

    public Set<GeoPt> getLocations() {
        return locations;
    }

    public void setLocations(Set<GeoPt> locations) {
        this.locations = locations;
    }

    public List<Long> getTagIds() {
        return tagIds;
    }

    // Additional methods
    public Interest addLocation(GeoPt geoPt) {
        locations.add(geoPt);
        return this;
    }

    public Interest addLocation(double latitude, double longitude) {
        return addLocation(new GeoPt((float) latitude, (float) longitude));
    }

    public Interest addTag(Tag tag) {
        tags.add(Ref.create(tag));
        return this;
    }

}
