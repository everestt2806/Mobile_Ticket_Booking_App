package com.finalproject.movieticketbooking.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.finalproject.movieticketbooking.R;
import com.finalproject.movieticketbooking.activities.MovieDetailActivity;
import com.finalproject.movieticketbooking.models.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private final Context context;
    private List<Movie> movies;
    private OnMovieClickListener listener;

    public MovieAdapter(Context context) {
        this.context = context;
        this.movies = new ArrayList<>();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        if (position >= 0 && position < movies.size()) {
            Movie movie = movies.get(position);
            holder.bind(movie);
        }
    }

    @Override
    public int getItemCount() {
        return movies != null ? movies.size() : 0;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    public void setOnMovieClickListener(OnMovieClickListener listener) {
        this.listener = listener;
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        private final ImageView moviePoster;
        private final TextView movieTitle;
        private final TextView movieDuration;
        private final TextView movieAgeRating;

        MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            moviePoster = itemView.findViewById(R.id.moviePoster);
            movieTitle = itemView.findViewById(R.id.movieTitle);
            movieDuration = itemView.findViewById(R.id.movieDuration);
            movieAgeRating = itemView.findViewById(R.id.movieAgeRating);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && position < movies.size()) {
                    Movie movie = movies.get(position);
                    if (listener != null) {
                        listener.onMovieClick(movie);
                    }
                    navigateToMovieDetail(movie);
                }
            });
        }

        private void navigateToMovieDetail(Movie movie) {
            Intent intent = new Intent(itemView.getContext(), MovieDetailActivity.class);

            // Đơn giản hóa việc truyền dữ liệu
            intent.putExtra("movie", movie);

            // Thêm flags để clear stack
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            itemView.getContext().startActivity(intent);
        }



        void bind(Movie movie) {
            if (movie != null) {
                movieTitle.setText(movie.getTitle());
                movieDuration.setText(String.format("%s min", movie.getDuration()));
                movieAgeRating.setText(movie.getAgeRating());

                // Load image using Glide with error handling
                if (movie.getPoster() != null && !movie.getPoster().toString().isEmpty()) {
                    Glide.with(itemView.getContext())
                            .load(movie.getPoster().toString())
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .placeholder(R.drawable.placeholder_movie)
                            .error(R.drawable.error_movie)
                            .into(moviePoster);
                } else {
                    moviePoster.setImageResource(R.drawable.placeholder_movie);
                }
            }
        }
    }

    public interface OnMovieClickListener {
        void onMovieClick(Movie movie);
    }
}
