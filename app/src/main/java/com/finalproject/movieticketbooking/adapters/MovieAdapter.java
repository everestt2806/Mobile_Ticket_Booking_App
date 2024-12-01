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

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private List<Movie> movieList;
    private Context context;
    private OnMovieClickListener listener;

    public interface OnMovieClickListener {
        void onMovieClick(Movie movie);
    }

    public MovieAdapter(List<Movie> movieList) {
        this.movieList = movieList;
        this.listener = null;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        if (movieList != null && !movieList.isEmpty()) {
            Movie movie = movieList.get(position);

            // Set movie title
            holder.movieTitle.setText(movie.getTitle());

            // Set movie year
            holder.movieYear.setText(String.valueOf(movie.getYear()));

            // Set IMDb rating
            holder.movieImdb.setText(String.format("%.1f", movie.getImdb()));

            // Load movie poster
            Glide.with(context)
                    .load(movie.getPoster())
                    .placeholder(R.drawable.placeholder_movie)
                    .error(R.drawable.error_movie)
                    .into(holder.moviePoster);

            // Set click listener
            if (listener != null) {
                holder.itemView.setOnClickListener(v -> listener.onMovieClick(movie));
            }
        }
    }

    @Override
    public int getItemCount() {
        return movieList == null ? 0 : movieList.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView moviePoster;
        TextView movieTitle, movieYear, movieImdb;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            moviePoster = itemView.findViewById(R.id.moviePoster);
            movieTitle = itemView.findViewById(R.id.movieTitle);
            movieYear = itemView.findViewById(R.id.movieYear);
            movieImdb = itemView.findViewById(R.id.movieImdb);
        }
    }
}
