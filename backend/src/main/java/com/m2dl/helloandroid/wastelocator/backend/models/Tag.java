package com.m2dl.helloandroid.wastelocator.backend.models;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.beans.Transient;

/**
 * Created by flemoal on 21/01/16.
 */
@Entity
public class Tag {
    @Id
    private Long id;

    @Index
    private String name;

    private String color;

    public Tag(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public Tag() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Transient
    public Key<Tag> getKey() {
        return Key.create(Tag.class, id);
    }

}
