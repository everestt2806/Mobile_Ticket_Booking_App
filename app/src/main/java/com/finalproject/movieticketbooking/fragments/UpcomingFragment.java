package com.finalproject.movieticketbooking.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.finalproject.movieticketbooking.R;
import com.finalproject.movieticketbooking.activities.MovieDetailActivity;
import com.finalproject.movieticketbooking.adapters.MovieAdapter;
import com.finalproject.movieticketbooking.custom.GridSpacingItemDecoration;
import com.finalproject.movieticketbooking.models.Movie;
import com.finalproject.movieticketbooking.services.DatabaseService;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UpcomingFragment extends Fragment {
    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private ProgressBar progressBar;
    private DatabaseService databaseService;
    private ValueEventListener movieListener;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        setupRecyclerView();
        loadMovies();
    }

    private void initViews(@NonNull View view) {
        recyclerView = view.findViewById(R.id.movieRecyclerView);
        progressBar = view.findViewById(R.id.movieProgressBar);
        databaseService = new DatabaseService();
        movieAdapter = new MovieAdapter(requireContext());
    }

    private void setupRecyclerView() {
        if (recyclerView == null || getContext() == null) return;

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(movieAdapter);

        // Add spacing decoration only if not already added
        if (recyclerView.getItemDecorationCount() == 0) {
            int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.grid_spacing);
            recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, spacingInPixels, true));
        }

        movieAdapter.setOnMovieClickListener(this::navigateToMovieDetail);
    }

    private void navigateToMovieDetail(Movie movie) {
        if (getActivity() != null && movie != null) {
            Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
            intent.putExtra("movie_id", movie.getId());
            startActivity(intent);
        }
    }

    private void loadMovies() {
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }

        // Remove previous listener if exists
        if (movieListener != null) {
            databaseService.getNowShowingMovies().removeEventListener(movieListener);
        }

        movieListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!isAdded()) return; // Check if fragment is still attached

                List<Movie> movies = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Movie movie = dataSnapshot.getValue(Movie.class);
                    if (movie != null) {
                        movie.setId(dataSnapshot.getKey());
                        movies.add(movie);
                    }
                }

                updateUI(movies);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                if (!isAdded()) return;

                hideProgressBar();
                showError(error.getMessage());
            }
        };

        databaseService.getComingSoonMovies().addValueEventListener(movieListener);
    }

    private void updateUI(List<Movie> movies) {
        if (movieAdapter != null) {
            movieAdapter.setMovies(movies);
        }
        hideProgressBar();
    }

    private void hideProgressBar() {
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void showError(String message) {
        if (getContext() != null) {
            Toast.makeText(getContext(),
                    "Error loading movies: " + message,
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        if (movieListener != null) {
            databaseService.getComingSoonMovies().removeEventListener(movieListener);
        }

        if (recyclerView != null) {
            recyclerView.setAdapter(null);
        }

        movieListener = null;
        movieAdapter = null;
        recyclerView = null;
        progressBar = null;
        databaseService = null;

        super.onDestroyView();
    }
}

