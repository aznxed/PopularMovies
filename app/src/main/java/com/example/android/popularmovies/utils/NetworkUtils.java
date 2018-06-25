package com.example.android.popularmovies.utils;

import android.net.Uri;
import android.util.Log;

import com.example.android.popularmovies.movieObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NetworkUtils {
    final private static String API_KEY = "INSERT API KEY";
    final private static String BASE_URL = "https://api.themoviedb.org/3/movie/";
    final private static String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w500/";
    final private static String ENGLISH = "en-US";

    //TODO Reduce boilerplate code
    public static URL buildMovieUrl(int sort) {
        String sortBy = "top_rated";
        if(sort == 0){
            sortBy = "popular";
        }

        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendEncodedPath(sortBy)
                .appendQueryParameter("api_key", API_KEY)
                .appendQueryParameter("language", ENGLISH)
                .appendQueryParameter("page", "1")
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static List<movieObject> parseMovieJSON(String JSON) throws JSONException{
        List<movieObject> movieObjectList = new ArrayList<>();
        JSONObject movieJson = new JSONObject(JSON);
        JSONArray results = movieJson.getJSONArray("results");
        int numMovies = 0;
        if(results != null){
            numMovies = results.length();
        }

        for(int a = 0; a < numMovies; a++){
            JSONObject movie = (JSONObject) results.get(a);
            int id = movie.getInt("id");
            String movieTitle = movie.getString("title");
            String imageUrl = movie.getString("poster_path");
            String description = movie.getString("overview");
            String rating = movie.getString("vote_average");
            String release = movie.getString("release_date");
            String backgroundUrl = movie.getString("backdrop_path");
            movieObjectList.add(new movieObject(id, movieTitle, IMAGE_BASE_URL.concat(imageUrl), IMAGE_BASE_URL.concat(backgroundUrl),description, rating, release));
        }

        return movieObjectList;
    }

    public static URL buildReviewUrl(String id){
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendEncodedPath(id)
                .appendEncodedPath("reviews")
                .appendQueryParameter("api_key", API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static List<String> parseReviewJSON(String JSON) throws JSONException{
        List<String> reviews = new ArrayList<>();
        JSONObject reviewJson = new JSONObject(JSON);
        JSONArray results = reviewJson.getJSONArray("results");
        int numReviews = 0;
        if(results != null){
            numReviews = results.length();
        }

        for(int a = 0; a < numReviews; a++){
            JSONObject review = (JSONObject) results.get(a);
            String author = review.getString("author");
            String content = review.getString("content");
            reviews.add(author);
            reviews.add(content);
        }

        return reviews;
    }

    public static URL buildTrailerUrl(String id){
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendEncodedPath(id)
                .appendEncodedPath("videos")
                .appendQueryParameter("api_key", API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static List<String> parseTrailerJSON(String JSON) throws JSONException{
        List<String> trailers = new ArrayList<>();
        JSONObject trailerJSON = new JSONObject(JSON);
        JSONArray results = trailerJSON.getJSONArray("results");
        int numTrailers = 0;
        if(results != null){
            numTrailers = results.length();
        }

        for(int a = 0; a < numTrailers; a++){
            JSONObject review = (JSONObject) results.get(a);
            String key = review.getString("key");
            String name = review.getString("name");
            trailers.add(key);
            trailers.add(name);
        }

        return trailers;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }



}
