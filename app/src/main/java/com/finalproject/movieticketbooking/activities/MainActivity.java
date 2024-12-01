package com.finalproject.movieticketbooking.activities;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.finalproject.movieticketbooking.R;
import com.finalproject.movieticketbooking.adapters.BannerAdapter;
import com.finalproject.movieticketbooking.adapters.MovieAdapter;
import com.finalproject.movieticketbooking.models.Banner;
import com.finalproject.movieticketbooking.models.Movie;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FirebaseDatabase database;
    private DatabaseReference bannerRef, itemsRef, upcomingRef;

    private ViewPager2 bannerViewPager;
    private RecyclerView topMoviesRecycler, upcomingRecycler;

    private ProgressBar progressBar3, progressBarTopMovie, progressBarUpcoming;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase
        FirebaseApp.initializeApp(this);

        initViews();
        initFirebase();
        loadData();
    }

    private void initViews() {
        bannerViewPager = findViewById(R.id.banner);
        topMoviesRecycler = findViewById(R.id.topMovie);
        upcomingRecycler = findViewById(R.id.recycleUpcoming);

        progressBar3 = findViewById(R.id.progressBar3);
        progressBarTopMovie = findViewById(R.id.progressBarTopMovie);
        progressBarUpcoming = findViewById(R.id.progressBarUpcoming);

        // Set up RecyclerViews
        topMoviesRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        upcomingRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    private void initFirebase() {
        database = FirebaseDatabase.getInstance();
        bannerRef = database.getReference("Banners");
        itemsRef = database.getReference("Items");
        upcomingRef = database.getReference("Upcomming");
    }

    private void loadData() {
        // Load Banners
        bannerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Banner> banners = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Banner banner = dataSnapshot.getValue(Banner.class);
                    if (banner != null) {
                        banners.add(banner);
                    }
                }
                if (!banners.isEmpty()) {
                    setupBannerViewPager(banners);
                } else {
                    // Handle empty list scenario
                    Log.e("MainActivity", "No banners data available.");
                }
                progressBar3.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar3.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Load Top Movies
        itemsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Movie> movies = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Movie movie = dataSnapshot.getValue(Movie.class);
                    if (movie != null) {
                        movies.add(movie);
                    }
                }
                setupTopMoviesRecycler(movies);
                progressBarTopMovie.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar3.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });

        // Load Upcoming Movies
        upcomingRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Movie> upcomingMovies = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Movie movie = dataSnapshot.getValue(Movie.class);
                    if (movie != null) {
                        upcomingMovies.add(movie);
                    }
                }
                setupUpcomingMoviesRecycler(upcomingMovies);
                progressBarUpcoming.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBarUpcoming.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupBannerViewPager(List<Banner> banners) {
        if (banners != null && !banners.isEmpty()) {
            BannerAdapter bannerAdapter = new BannerAdapter(banners);
            bannerViewPager.setAdapter(bannerAdapter);
            autoScrollBanner();
        } else {
            Log.e("MainActivity", "Banner data is empty.");
        }
    }


    private void setupTopMoviesRecycler(List<Movie> movies) {
        if (movies != null && !movies.isEmpty()) {
            MovieAdapter movieAdapter = new MovieAdapter(movies);
            topMoviesRecycler.setAdapter(movieAdapter);
        } else {
            Log.e("MainActivity", "Top movies data is empty.");
        }
    }


    private void setupUpcomingMoviesRecycler(List<Movie> movies) {
        if (movies != null && !movies.isEmpty()) {
            MovieAdapter upcomingAdapter = new MovieAdapter(movies);
            upcomingRecycler.setAdapter(upcomingAdapter);
        }
        else {
            Log.e("MainActivity", "Top movies data is empty.");
        }
    }
    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable bannerRunnable;
    private void autoScrollBanner() {
        bannerRunnable = new Runnable() {
            @Override
            public void run() {
                int currentItem = bannerViewPager.getCurrentItem();
                bannerViewPager.setCurrentItem(currentItem + 1, true);
                handler.postDelayed(this, 3000);
            }
        };
        handler.postDelayed(bannerRunnable, 3000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Hủy bỏ runnable khi activity bị phá hủy
        handler.removeCallbacks(bannerRunnable);
    }
}
