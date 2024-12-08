package com.finalproject.movieticketbooking.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.finalproject.movieticketbooking.R;
import com.finalproject.movieticketbooking.models.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private List<Movie> movieList;
    private Context context;
    private OnMovieClickListener listener;

    public interface OnMovieClickListener {
        void onMovieClick(Movie movie);
    }

    public MovieAdapter(List<Movie> movieList, OnMovieClickListener listener) {
        this.movieList = movieList != null ? movieList : new ArrayList<>();
        this.listener = listener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (context == null) {
            context = parent.getContext();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        if (movie != null && context != null) {
            try {
                // Set movie title
                holder.movieTitle.setText(movie.getTitle() != null ? movie.getTitle() : "");

                holder.movieDuration.setText(context.getString(R.string.duration_format, movie.getDuration()));

                // Set age rating
                holder.movieAgeRating.setText(movie.getAgeRating() != null ? movie.getAgeRating() : "");

                // Load movie poster with error handling
                if (movie.getPoster() != null && !movie.getPoster().isEmpty()) {
                    try {
                        Glide.with(holder.itemView.getContext())
                                .load(movie.getPoster())
                                .placeholder(R.drawable.placeholder_movie)
                                .error(R.drawable.error_movie)
                                .centerCrop()
                                .into(holder.moviePoster);
                    } catch (Exception e) {
                        holder.moviePoster.setImageResource(R.drawable.error_movie);
                    }
                } else {
                    holder.moviePoster.setImageResource(R.drawable.placeholder_movie);
                }

                // Set click listener
                holder.itemView.setOnClickListener(v -> {
                    if (listener != null) {
                        listener.onMovieClick(movie);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getItemCount() {
        return movieList != null ? movieList.size() : 0;
    }

    public void updateMovies(List<Movie> newMovies) {
        this.movieList = newMovies != null ? newMovies : new ArrayList<>();
        notifyDataSetChanged();
    }

    // Thêm định nghĩa MovieViewHolder vào đây
    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        final ImageView moviePoster;
        final TextView movieTitle, movieDuration, movieAgeRating;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            moviePoster = itemView.findViewById(R.id.moviePoster);
            movieTitle = itemView.findViewById(R.id.movieTitle);
            movieDuration = itemView.findViewById(R.id.movieDuration);
            movieAgeRating = itemView.findViewById(R.id.movieAgeRating);
        }
    }
}

