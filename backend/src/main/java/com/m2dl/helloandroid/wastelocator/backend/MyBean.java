package com.m2dl.helloandroid.wastelocator.backend;

/** The object model for the data we are sending through endpoints */
public class MyBean {

    private String myData;

    public String getData() {
        return myData;
    }

    public void setData(String imgUrl) {
        this.myData = imgUrl;
    }
}