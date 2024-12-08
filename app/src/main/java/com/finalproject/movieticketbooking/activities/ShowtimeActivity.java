package com.finalproject.movieticketbooking.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.finalproject.movieticketbooking.R;
import com.finalproject.movieticketbooking.adapters.CinemaShowtimeAdapter;
import com.finalproject.movieticketbooking.models.Cinema;
import com.finalproject.movieticketbooking.models.CinemaWithShowtimes;
import com.finalproject.movieticketbooking.models.Showtime;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.HashMap;

public class ShowtimeActivity extends AppCompatActivity implements CinemaShowtimeAdapter.OnShowtimeSelectedListener {
    private String movieId, movieTitle, moviePoster, movieDuration, movieAgeRating;

    private RecyclerView rvShowtimes;
    private CinemaShowtimeAdapter adapter;
    private List<CinemaWithShowtimes> cinemaShowtimesList;
    private ProgressBar progressBar;
    private TextView tvNoShowtimes, tvErrorMessage, title, duration, ageRating;
    private ImageView btnBack;

    private ImageView poster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showtime);

        // Lấy dữ liệu từ Intent
        Intent intent = getIntent();
        if (intent != null) {
            movieId = intent.getStringExtra("movieId");
            movieTitle = intent.getStringExtra("movieTitle");
            moviePoster = intent.getStringExtra("moviePoster");
            movieDuration = intent.getStringExtra("movieDuration");
            movieAgeRating = intent.getStringExtra("movieAgeRating");
            // Kiểm tra dữ liệu bắt buộc
            if (movieId == null) {
                Toast.makeText(this, "Error: Movie data not found", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }
        }

        // Khởi tạo các view
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        rvShowtimes = findViewById(R.id.rvShowtimes);
        progressBar = findViewById(R.id.progressBar);
        tvNoShowtimes = findViewById(R.id.tvNoShowtimes);
        tvErrorMessage = findViewById(R.id.tvErrorMessage);
        title = findViewById(R.id.tvMovieTitle);
        duration = findViewById(R.id.tvDuration);
        ageRating = findViewById(R.id.tvAgeRating);
        poster = findViewById(R.id.ivMoviePoster);
        cinemaShowtimesList = new ArrayList<>();
        adapter = new CinemaShowtimeAdapter(cinemaShowtimesList, this);
        rvShowtimes.setLayoutManager(new LinearLayoutManager(this));
        rvShowtimes.setAdapter(adapter);

        // Load dữ liệu showtimes
        if (movieId != null) {
            title.setText(movieTitle);
            duration.setText("Duration: "+ movieDuration + " minutes");
            ageRating.setText("Age Rating: "+ movieAgeRating);
            if(moviePoster != null){
                Glide.with(this)
                        .load(moviePoster) // URL của hình ảnh
                        .placeholder(R.drawable.placeholder_movie) // Hình ảnh hiển thị khi đang tải
                        .error(R.drawable.placeholder_movie) // Hình ảnh hiển thị khi tải lỗi
                        .into(poster); // Đặt hình vào ImageView
            }
            loadCinemasAndShowtimes();
        }
    }

    @Override
    public void onShowtimeSelected(Showtime showtime) {
        // Xử lý khi người dùng chọn một showtime
        if (showtime != null) {
            Intent intent = new Intent(this, SeatSelectionActivity.class);
            intent.putExtra("showtime", showtime);
            intent.putExtra("movieId", movieId);
            intent.putExtra("movieTitle", movieTitle);
            intent.putExtra("moviePoster", moviePoster);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Invalid showtime selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadCinemasAndShowtimes() {
        showLoadingIndicator();

        // Query để lấy showtimes theo movieId
        DatabaseReference showtimesRef = FirebaseDatabase.getInstance().getReference("showtimes");
        Query query = showtimesRef.orderByChild("movieId").equalTo(movieId);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    Map<String, List<Showtime>> cinemaShowtimesMap = new HashMap<>();

                    // Duyệt qua các showtimes
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Showtime showtime = snapshot.getValue(Showtime.class);
                        if (showtime != null) {
                            String cinemaId = showtime.getCinemaId();
                            cinemaShowtimesMap
                                    .computeIfAbsent(cinemaId, k -> new ArrayList<>())
                                    .add(showtime);
                        }
                    }

                    if (cinemaShowtimesMap.isEmpty()) {
                        hideLoadingIndicator();
                        showNoShowtimesMessage();
                        return;
                    }

                    // Load thông tin rạp chiếu
                    loadCinemaDetails(cinemaShowtimesMap);

                } catch (Exception e) {
                    hideLoadingIndicator();
                    handleError("Error processing showtimes: " + e.getMessage());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                hideLoadingIndicator();
                handleError("Database error: " + error.getMessage());
            }
        });
    }

    private void loadCinemaDetails(Map<String, List<Showtime>> cinemaShowtimesMap) {
        DatabaseReference cinemasRef = FirebaseDatabase.getInstance().getReference("cinemas");

        cinemasRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    cinemaShowtimesList.clear();

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Cinema cinema = snapshot.getValue(Cinema.class);
                        if (cinema != null && cinemaShowtimesMap.containsKey(cinema.getId())) {
                            List<Showtime> showtimes = cinemaShowtimesMap.get(cinema.getId());
                            if (showtimes != null) {
                                // Sắp xếp showtimes theo thời gian bắt đầu
                                Collections.sort(showtimes, (s1, s2) -> {
                                    try {
                                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
                                        Date date1 = sdf.parse(s1.getStartTime());
                                        Date date2 = sdf.parse(s2.getStartTime());
                                        return date1.compareTo(date2);
                                    } catch (Exception e) {
                                        Log.e("ShowtimeActivity", "Error parsing showtime start times: " + e.getMessage());
                                        return 0;
                                    }
                                });

                                cinemaShowtimesList.add(new CinemaWithShowtimes(cinema, showtimes));
                            }
                        }
                    }

                    // Sắp xếp danh sách rạp theo tên
                    Collections.sort(cinemaShowtimesList, (c1, c2) ->
                            c1.getCinema().getName().compareTo(c2.getCinema().getName()));

                    adapter.notifyDataSetChanged();
                    hideLoadingIndicator();

                } catch (Exception e) {
                    hideLoadingIndicator();
                    handleError("Error processing cinema data: " + e.getMessage());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                hideLoadingIndicator();
                handleError("Database error: " + error.getMessage());
            }
        });
    }

    // Hiển thị loading indicator
    private void showLoadingIndicator() {
        progressBar.setVisibility(View.VISIBLE);
        rvShowtimes.setVisibility(View.GONE);
        tvNoShowtimes.setVisibility(View.GONE);
        tvErrorMessage.setVisibility(View.GONE);
    }

    // Ẩn loading indicator
    private void hideLoadingIndicator() {
        progressBar.setVisibility(View.GONE);
        rvShowtimes.setVisibility(View.VISIBLE);
    }

    // Hiển thị thông báo không có showtimes
    private void showNoShowtimesMessage() {
        tvNoShowtimes.setVisibility(View.VISIBLE);
        rvShowtimes.setVisibility(View.GONE);
    }

    // Hiển thị thông báo lỗi
    private void handleError(String errorMessage) {
        Log.e("ShowtimeActivity", errorMessage);
        tvErrorMessage.setVisibility(View.VISIBLE);
        tvErrorMessage.setText("An error occurred: " + errorMessage);
        rvShowtimes.setVisibility(View.GONE);
    }
}
