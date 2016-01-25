package com.m2dl.helloandroid.wastelocator.backend.beans;

import com.m2dl.helloandroid.wastelocator.backend.models.Interest;
import com.m2dl.helloandroid.wastelocator.backend.models.Tag;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by flemoal on 25/01/16.
 */
public class ListInterestBean {
    private List<Interest> interests;
    private List<Tag> tags;

    public ListInterestBean() {
        this(new LinkedList<Interest>(), new LinkedList<Tag>());
    }

    public ListInterestBean(List<Interest> interests, List<Tag> tags) {
        this.interests = interests;
        this.tags = tags;
    }

    public List<Interest> getInterests() {
        return interests;
    }

    public void setInterests(List<Interest> interests) {
        this.interests = interests;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
