package com.example.android.popularmovies;

public class movieObject {
    private int id;
    private String name;
    private String description;
    private String photoUrl;
    private String backgroundUrl;
    private String rating;
    private String releaseDate;

    public movieObject(int id, String name, String photoUrl, String backgroundUrl, String description, String rating, String releaseDate){
        this.id = id;
        this.name = name;
        this.photoUrl = photoUrl;
        this.backgroundUrl = backgroundUrl;
        this.description = description;
        this.rating = rating;
        this.releaseDate = releaseDate;
    }
    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getPhoto(){
        return photoUrl;
    }

    public String getBackground(){
        return backgroundUrl;
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
