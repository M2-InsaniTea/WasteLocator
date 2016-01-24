package com.m2dl.helloandroid.wastelocator.backend.models;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * Created by flemoal on 21/01/16.
 */
@Entity
public class Tag {
    @Id
    private Long key;

    private String name;

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
