package com.m2dl.helloandroid.wastelocator.backend.models;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * Created by flemoal on 21/01/16.
 */
@Entity
public class UserAccount {
    @Id
    private Long key;

    @Index
    private String username;

    public UserAccount(String username) {
        this.username = username;
    }

    public UserAccount() {
    }

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
