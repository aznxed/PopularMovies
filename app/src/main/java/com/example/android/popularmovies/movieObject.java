package com.example.android.popularmovies;

public class movieObject {
    private String name;
    private String description;
    private String photoUrl;
    private String rating;
    private String releaseDate;

    public movieObject(String name, String photoUrl){
        this.name = name;
        this.photoUrl = photoUrl;
    }

    public movieObject(String name, String photoUrl, String description, String rating, String releaseDate){
        this.name = name;
        this.photoUrl = photoUrl;
        this.description = description;
        this.rating = rating;
        this.releaseDate = releaseDate;
    }

    public String getName(){
        return name;
    }

    public String getPhoto(){
        return photoUrl;
    }

    public String getDescription(){
        return description;
    }

    public String getRating(){
        return rating;
    }

    public String getReleaseDate(){
        return releaseDate;
    }


}
