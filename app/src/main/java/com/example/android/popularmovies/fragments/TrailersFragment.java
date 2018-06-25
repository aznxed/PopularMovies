package com.example.android.popularmovies.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.utils.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.List;


import static com.example.android.popularmovies.utils.NetworkUtils.parseTrailerJSON;


/**
 * A simple {@link Fragment} subclass.
 */
public class TrailersFragment extends Fragment
        implements RecyclerViewTrailerAdapter.ListItemClickListener{

    private RecyclerViewTrailerAdapter.ListItemClickListener listItemClickListener;
    private List<String> trailerList;
    private RecyclerView rView;

    public TrailersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        trailerList = null;
        listItemClickListener = this;
        return inflater.inflate(R.layout.fragment_trailers, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rView = getView().findViewById(R.id.recycler_view);
        ConnectivityManager cm = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if(isConnected){
            Bundle bundle = this.getArguments();
            String id = bundle.getString("id");
            new TrailerQueryTask().execute(NetworkUtils.buildTrailerUrl(id));
        }

    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        playTrailer(clickedItemIndex);
    }

    public void playTrailer(int index){
        String videoId = trailerList.get(index*2);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:"+videoId));
        if(intent.resolveActivity(getActivity().getPackageManager())!= null){
            startActivity(intent);
        }
    }

    private class TrailerQueryTask extends AsyncTask<URL, Void, String>{

        @Override
        protected String doInBackground(URL... urls) {
            URL searchUrl = urls[0];
            String trailerJSON = null;
            try {
                trailerJSON = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return trailerJSON;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                trailerList = parseTrailerJSON(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            RecyclerViewTrailerAdapter rcAdapter = new RecyclerViewTrailerAdapter(getContext(), trailerList, listItemClickListener);
            GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
            rView.setAdapter(rcAdapter);
            rView.setHasFixedSize(true);
            rView.setLayoutManager(layoutManager);

        }
    }





}
