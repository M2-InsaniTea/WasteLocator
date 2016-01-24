package com.m2dl.helloandroid.wastelocator.backend.models;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.awt.Color;

/**
 * Created by flemoal on 21/01/16.
 */
@Entity
public class Tag {
    @Id
    private Long key;

    @Index
    private String name;

    private Color color;

    public Tag(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    public Long getKey() {
        return key;
    }

    public Tag() {
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

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
