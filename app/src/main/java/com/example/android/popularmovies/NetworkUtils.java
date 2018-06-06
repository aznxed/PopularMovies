package com.example.android.popularmovies;

import android.net.Uri;
import android.util.Log;

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
    final private static String API_KEY = "c480a9adf41f702cf929f7fcb53a79dc";
    final private static String BASE_URL = "https://api.themoviedb.org/3/movie/";
    final private static String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w500/";
    final private static String ENGLISH = "en-US";

    public static URL buildUrl(int sort) {
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

        Log.d("Test", url.toString());
        return url;
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


    public static List<movieObject> parseJSON(String JSON) throws JSONException{
        List<movieObject> movieObjectList = new ArrayList<>();
        JSONObject movieJson = new JSONObject(JSON);
        JSONArray results = movieJson.getJSONArray("results");
        int numMovies = 0;
        if(results != null){
            numMovies = results.length();
        }

        for(int a = 0; a < numMovies; a++){
            JSONObject movie = (JSONObject) results.get(a);
            String movieTitle = movie.getString("title");
            String imageUrl = movie.getString("poster_path");
            String description = movie.getString("overview");
            String rating = movie.getString("vote_average");
            String release = movie.getString("release_date");
            String backgroundUrl = movie.getString("backdrop_path");
            movieObjectList.add(new movieObject(movieTitle, IMAGE_BASE_URL.concat(imageUrl), IMAGE_BASE_URL.concat(backgroundUrl),description, rating, release));
        }

        return movieObjectList;
    }

}
