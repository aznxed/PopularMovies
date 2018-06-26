package com.example.android.popularmovies.utils;

import android.net.Uri;

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
    final private static String API_KEY = "Insert API key";
    final private static String BASE_URL = "https://api.themoviedb.org/3/movie/";
    final private static String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w500/";
    final private static String ENGLISH = "en-US";

    final public static int MOVIE_URL = 0;
    final public static int TRAILER_URL = 1;
    final public static int REVIEW_URL = 2;
    final public static String TOP_RATED = "top_rated";
    final public static String POPULAR = "popular";
    final public static int REVIEW_JSON = 0;
    final public static int TRAILER_JSON = 1;

    public static URL buildUrl(int urlType, String id){
        Uri builtUri = null;

        switch (urlType){
            case MOVIE_URL:
                builtUri = Uri.parse(BASE_URL).buildUpon()
                    .appendEncodedPath(id)
                    .appendQueryParameter("api_key", API_KEY)
                    .appendQueryParameter("language", ENGLISH)
                    .appendQueryParameter("page", "1")
                    .build();
                break;
            case TRAILER_URL:
                builtUri = Uri.parse(BASE_URL).buildUpon()
                    .appendEncodedPath(id)
                    .appendEncodedPath("videos")
                    .appendQueryParameter("api_key", API_KEY)
                    .build();
                break;
            case REVIEW_URL:
                builtUri = Uri.parse(BASE_URL).buildUpon()
                    .appendEncodedPath(id)
                    .appendEncodedPath("reviews")
                    .appendQueryParameter("api_key", API_KEY)
                    .build();
                break;

        }

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
        if (JSON == null){
            return null;
        }

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

    public static List<String> parseJSON(String JSON, int type) throws JSONException{
        List<String> list = new ArrayList<>();
        if (JSON == null){
            return null;
        }
        JSONObject trailerJSON = new JSONObject(JSON);
        JSONArray results = trailerJSON.getJSONArray("results");
        int numResults = 0;
        if(results != null){
            numResults = results.length();
        }

        for(int a = 0; a < numResults; a++){
            JSONObject result = (JSONObject) results.get(a);
            if(type == TRAILER_JSON){
                list.add(result.getString("key"));
                list.add(result.getString("name"));
            }
            else {
                list.add(result.getString("author"));
                list.add(result.getString("content"));
            }

        }

        return list;
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
