package com.example.design;

public class StorageClass {


    private String ProTitle;
    private String ProDecs;
    private String ProLoc;
    private String ProBud;
    private String Photo;

    private String key;

    public void setKey(String key) {
        this.key = key;
    }
    public String getProTitle() {
        return ProTitle;
    }

    public String getProDecs() {
        return ProDecs;
    }

    public String getProLoc() {
        return ProLoc;
    }

    public String getProBud() {
        return ProBud;
    }
    public String getPhoto() {
        return Photo;
    }

    public StorageClass(String ProTitle, String ProDecs, String ProLoc, String ProBud,String Photo) {
        this.ProTitle = ProTitle;
        this.ProDecs = ProDecs;
        this.ProLoc = ProLoc;
        this.ProBud = ProBud;
        this.Photo= Photo;
    }
    public StorageClass(){


    }



}

