package com.example.movie_app.adapters;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movie_app.R;
import com.example.movie_app.api.ApiInterface;
import com.example.movie_app.model.MovieVideosModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.example.movie_app.utils.Utils.getYoutubeThumbnail;

public class MovieTrailerAdapter extends RecyclerView.Adapter<MovieTrailerAdapter.ViewHolder>{
    Context mContext;
    ApiInterface mApiInterface;
    List<MovieVideosModel> mTrailers;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View itemView;
        public TextView trailerTitle;
        public ImageView trailerThumbnail;

        public ViewHolder(View view) {
            super(view);
            itemView = view;
            trailerTitle = (TextView) itemView.findViewById(R.id.trailer_name);
            trailerThumbnail = (ImageView) itemView.findViewById(R.id.movie_trailer_thumbnail);
        }
    }

    public MovieTrailerAdapter(List<MovieVideosModel> movieTrailers) {
        super();
        mTrailers = movieTrailers;
    }

    @Override
    public int getItemCount() {
        return mTrailers == null ? 0: mTrailers.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);

        // Inflate the custom layout
        View movieView = inflater.inflate(R.layout.movie_detail_trailer_item, parent, false);

        // Return a new holder instance
        return new ViewHolder(movieView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Get current movie
        MovieVideosModel movie = mTrailers.get(position);

        // Set movie name
        holder.trailerTitle.setText(movie.name);

        // Set trailer thumbnail poster
        String imgPath = getYoutubeThumbnail(mContext, movie.key);
        Picasso.get().load(imgPath).into(holder.trailerThumbnail);

        // Set movie item click listener
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    String youtubeVideoId = movie.key;
                    Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + youtubeVideoId));
                    Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + youtubeVideoId));
                    try {
                        mContext.startActivity(appIntent);
                    } catch (ActivityNotFoundException ex) {
                        mContext.startActivity(webIntent);
                    }
                }
        });
    }
}


