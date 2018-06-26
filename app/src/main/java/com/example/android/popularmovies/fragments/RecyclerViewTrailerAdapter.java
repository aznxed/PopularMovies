package com.example.android.popularmovies.fragments;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmovies.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import static java.security.AccessController.getContext;

public class RecyclerViewTrailerAdapter extends RecyclerView.Adapter<RecyclerViewTrailerAdapter.TrailerHolders>{

    private List<String> keys;
    private Context context;
    final private ListItemClickListener mOnClickListener;

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    public RecyclerViewTrailerAdapter (Context context, List<String> keys, ListItemClickListener mOnClickListener){
        this.context = context;
        this.keys = keys;
        this.mOnClickListener = mOnClickListener;
    }

    @Override
    public TrailerHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_view, null);
        return new TrailerHolders(layoutView);
    }

    @Override
    public void onBindViewHolder(TrailerHolders holder, int position) {
        holder.key = keys.get((position*2));
        holder.trailerName.setText(keys.get((position*2)+1));
        StringBuilder path = new StringBuilder("https://img.youtube.com/vi/");
        path.append(keys.get((position*2)));
        path.append("/mqdefault.jpg");
        Picasso.with(context).load(path.toString()).error(R.drawable.ic_broken_image_black_24dp).into(holder.trailerImage);
    }

    @Override
    public int getItemCount() {
        return this.keys.size()/2;
    }

    class TrailerHolders extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView trailerImage;
        private TextView trailerName;
        private String key;

        public TrailerHolders(View itemView){
            super(itemView);
            trailerImage = itemView.findViewById(R.id.trailer_image);
            trailerName = itemView.findViewById(R.id.trailer_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }
}
