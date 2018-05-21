package com.example.android.popularmovies;

public class movieObject {
    private String name;
    private int photo;

    public movieObject(String name, int photo){
        this.name = name;
        this.photo = photo;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public int getPhoto(){
        return photo;
    }

    public void setPhoto(){
        this.photo = photo;
    }

}
