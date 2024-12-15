package com.finalproject.movieticketbooking.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.finalproject.movieticketbooking.R;
import com.finalproject.movieticketbooking.adapters.BannerAdapter;
import com.finalproject.movieticketbooking.adapters.ViewPagerAdapter;
import com.finalproject.movieticketbooking.models.Banner;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ChipNavigationBar bottomNav;
    private FirebaseDatabase database;
    private DatabaseReference bannerRef, moviesRef;
    private ViewPager2 bannerViewPager;
    private ProgressBar progressBar3;
    private Handler handler;
    private Runnable bannerRunnable;
    private BannerAdapter bannerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            FirebaseApp.initializeApp(this);
            initViews();
            initFirebase();
            initAdapters();
            loadData();
            handler = new Handler(Looper.getMainLooper());
            setupViewPagerAndTabs();
            setupBottomNavigation();
        } catch (Exception e) {
            Log.e(TAG, "Error in onCreate: " + e.getMessage());
            showError("Failed to initialize app");
        }
    }

    private void initViews() {
        try {
            bannerViewPager = findViewById(R.id.banner);
            progressBar3 = findViewById(R.id.progressBar3);
        } catch (Exception e) {
            Log.e(TAG, "Error in initViews: " + e.getMessage());
        }
    }

    private void initAdapters() {
        try {
            bannerAdapter = new BannerAdapter(new ArrayList<>());
            bannerViewPager.setAdapter(bannerAdapter);
        } catch (Exception e) {
            Log.e(TAG, "Error in initAdapters: " + e.getMessage());
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    private void initFirebase() {
        try {
            database = FirebaseDatabase.getInstance();
            bannerRef = database.getReference("banners");
            moviesRef = database.getReference("movies");
        } catch (Exception e) {
            Log.e(TAG, "Error in initFirebase: " + e.getMessage());
            showError("Failed to connect to database");
        }
    }

    private void loadData() {
        if (isNetworkAvailable()) {
            loadBanners();
        } else {
            showError("No internet connection");
        }
    }

    private void setupViewPagerAndTabs() {
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager2 viewPagerTabs = findViewById(R.id.viewPagerTabs);

        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        viewPagerTabs.setAdapter(adapter);

        viewPagerTabs.setOffscreenPageLimit(ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT);

        new TabLayoutMediator(tabLayout, viewPagerTabs, (tab, position) -> {
            tab.setText(position == 0 ? "Đang khởi chiếu" : "Sắp khởi chiếu");
        }).attach();

        viewPagerTabs.setUserInputEnabled(true);
    }

    private void loadBanners() {
        progressBar3.setVisibility(View.VISIBLE);
        bannerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    List<Banner> banners = new ArrayList<>();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        try {
                            Banner banner = dataSnapshot.getValue(Banner.class);
                            if (banner != null && banner.getImage() != null && !banner.getImage().isEmpty()) {
                                // Validate URL
                                URL url = new URL(banner.getImage());
                                banners.add(banner);
                            }
                        } catch (Exception e) {
                            Log.e(TAG, "Error parsing banner: " + dataSnapshot.getKey(), e);
                        }
                    }

                    if (!banners.isEmpty()) {
                        setupBannerViewPager(banners);
                    } else {
                        Log.w(TAG, "No valid banners found");
                        // Maybe show a default banner or message
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Error loading banners", e);
                    showError("Failed to load banners");
                } finally {
                    progressBar3.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "Banner loading cancelled", error.toException());
                handleError(error, progressBar3);
            }
        });
    }

    private void setupBannerViewPager(List<Banner> banners) {
        if (!isFinishing() && banners != null && !banners.isEmpty()) {
            bannerAdapter = new BannerAdapter(banners);
            bannerViewPager.setAdapter(bannerAdapter);
            autoScrollBanner();
        }
    }

    private void autoScrollBanner() {
        if (handler != null && bannerViewPager.getAdapter() != null &&
                bannerViewPager.getAdapter().getItemCount() > 0) {

            bannerRunnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        if (!isFinishing()) {
                            int currentItem = bannerViewPager.getCurrentItem();
                            int totalItems = bannerViewPager.getAdapter().getItemCount();
                            bannerViewPager.setCurrentItem(currentItem < totalItems - 1 ? currentItem + 1 : 0, true);
                            handler.postDelayed(this, 3500);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Error in banner auto-scroll: " + e.getMessage());
                    }
                }
            };
            handler.postDelayed(bannerRunnable, 3500);
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
            return capabilities != null && (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR));
        }
        return false;
    }

    private void showError(String message) {
        if (!isFinishing()) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }

    private void handleError(DatabaseError error, ProgressBar progressBar) {
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
        showError(error.getMessage());
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null && bannerRunnable != null) {
            handler.removeCallbacks(bannerRunnable);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (bannerViewPager.getAdapter() != null &&
                bannerViewPager.getAdapter().getItemCount() > 0) {
            autoScrollBanner();
        }
        bottomNav.setItemSelected(R.id.home, true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null && bannerRunnable != null) {
            handler.removeCallbacks(bannerRunnable);
        }
        handler = null;
    }

    private void setupBottomNavigation() {
        bottomNav = findViewById(R.id.bottom_nav);
        SharedPreferences preferences = getSharedPreferences("appPreferences", MODE_PRIVATE);
        boolean isLoggedIn = preferences.getBoolean("isLoggedIn", false);
        bottomNav.setOnItemSelectedListener(itemId -> {
            if (itemId == R.id.home) {
                // Xử lý cho Home
            } else if (itemId == R.id.favorites) {
                // Xử lý cho Favorites
            } else if (itemId == R.id.blog) {
                // Xử lý cho Blog
            } else if (itemId == R.id.profile) {
                // Chuyển sang LoginActivity
                if (isLoggedIn) {
                    Intent intent = new Intent(MainActivity.this, UserActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
