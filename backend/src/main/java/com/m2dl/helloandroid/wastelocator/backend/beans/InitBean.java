package com.m2dl.helloandroid.wastelocator.backend.beans;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by flemoal on 25/01/16.
 */
public class InitBean {

    private List<String> data;

    public InitBean() {
        this(new LinkedList<String>());
    }

    public InitBean(List<String> data) {
        this.data = data;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
