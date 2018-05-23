package com.example.android.popularmovies;

public class movieObject {
    private String name;
    private String description;
    private String photoUrl;
    private int rating;

    public movieObject(String name, String photoUrl){
        this.name = name;
        this.photoUrl = photoUrl;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getPhoto(){
        return photoUrl;
    }

    public void setPhoto(String photoUrl){
        this.photoUrl = photoUrl;
    }

}
