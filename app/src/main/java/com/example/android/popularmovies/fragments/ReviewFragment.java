package com.example.android.popularmovies.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.popularmovies.utils.NetworkUtils;
import com.example.android.popularmovies.R;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import static com.example.android.popularmovies.utils.NetworkUtils.REVIEW_JSON;
import static com.example.android.popularmovies.utils.NetworkUtils.REVIEW_URL;
import static com.example.android.popularmovies.utils.NetworkUtils.parseJSON;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewFragment extends Fragment {

    private List<String> reviewList;
    private RecyclerView rView;
    public ReviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_review, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = this.getArguments();
        String id = bundle.getString("id");
        rView = getView().findViewById(R.id.recycler_view);
        reviewList = null;

        ConnectivityManager cm = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if(isConnected){
            new ReviewQueryTask().execute(NetworkUtils.buildUrl(REVIEW_URL, id));
        }
    }

    private class ReviewQueryTask extends AsyncTask<URL, Void, String>{

        @Override
        protected String doInBackground(URL... urls) {
            URL searchUrl = urls[0];
            String reviewJSON = null;
            try {
                reviewJSON = NetworkUtils.getResponseFromHttpUrl(searchUrl);

            } catch (IOException e){
                e.printStackTrace();
            }
            return reviewJSON;
        }

        @Override
        protected void onPostExecute(String s) {
            try{
                reviewList = parseJSON(s, REVIEW_JSON);
                if(reviewList == null){
                    TextView noneMessage = getView().findViewById(R.id.none_message);
                    String error = "Unable to retrieve data";
                    noneMessage.setText(error);
                    noneMessage.setVisibility(View.VISIBLE);
                    return;
                }
            }catch (JSONException e){
                e.printStackTrace();
            }

            if(reviewList.isEmpty()){
                TextView noneMessage = getView().findViewById(R.id.none_message);
                noneMessage.setVisibility(View.VISIBLE);
            }
            else {

                RecyclerViewReviewsAdapter rcAdapter = new RecyclerViewReviewsAdapter(reviewList);
                LinearLayoutManager reviewLayout = new LinearLayoutManager(getContext());

                rView.setAdapter(rcAdapter);
                rView.setHasFixedSize(true);
                rView.setLayoutManager(reviewLayout);
            }

        }
    }

}
