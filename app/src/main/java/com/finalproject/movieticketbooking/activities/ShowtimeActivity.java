package com.finalproject.movieticketbooking.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.finalproject.movieticketbooking.R;
import com.finalproject.movieticketbooking.adapters.CinemaAdapter;
import com.finalproject.movieticketbooking.adapters.DateAdapter;
import com.finalproject.movieticketbooking.adapters.ShowtimeAdapter;
import com.finalproject.movieticketbooking.custom.SpacingItemDecoration;
import com.finalproject.movieticketbooking.models.Cinema;
import com.finalproject.movieticketbooking.models.Movie;
import com.finalproject.movieticketbooking.models.Showtime;
import com.finalproject.movieticketbooking.services.DatabaseService;
import com.google.android.material.chip.Chip;
import com.google.android.material.imageview.ShapeableImageView;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class ShowtimeActivity extends AppCompatActivity {

    private RecyclerView dateRecyclerView;
    private RecyclerView cinemaRecyclerView;

    private DateAdapter dateAdapter;
    private CinemaAdapter cinemaAdapter;

    private List<String> datesList = new ArrayList<>();
    private List<Cinema> cinemaList = new ArrayList<>();

    private DatabaseService databaseService;
    private Movie selectedMovie;

    ShapeableImageView poster;
    TextView movieTitle, movieDuration;
    Chip movieAgeRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showtime);

        poster = findViewById(R.id.movie_poster);
        movieTitle = findViewById(R.id.movie_title);
        movieDuration = findViewById(R.id.movie_duration);
        movieAgeRating = findViewById(R.id.movie_age_rating);
        // Nhận dữ liệu Movie từ Intent
        selectedMovie = getIntent().getParcelableExtra("MOVIE_DATA");

        if (selectedMovie == null) {
            Toast.makeText(this, "Movie data not found!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Glide.with(this)
                    .load(selectedMovie.getPoster())
                    .into(poster);
            movieTitle.setText(selectedMovie.getTitle());
            movieDuration.setText("Duration: "+ String.valueOf(selectedMovie.getDuration()) + "minutes");
            movieAgeRating.setText(selectedMovie.getAgeRating());

            Log.d("ShowtimeActivity", "Selected Movie: " + selectedMovie.getTitle());
        }

        // Khởi tạo DatabaseService
        databaseService = new DatabaseService();

        // Ánh xạ View
        dateRecyclerView = findViewById(R.id.date_list);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.item_spacing);
        dateRecyclerView.addItemDecoration(new SpacingItemDecoration(spacingInPixels));
        cinemaRecyclerView = findViewById(R.id.cinema_list);

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        // Thiết lập danh sách ngày
        setupDates();

        SimpleDateFormat apiDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String today = apiDateFormat.format(Calendar.getInstance().getTime());
        Log.d("Today is: ", today);
        // Thiết lập danh sách rạp
        setupCinemas();
        updateCinemasForDate(today);
    }

    private void setupDates() {
        // Tạo danh sách 7 ngày từ hôm nay
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM dd", Locale.getDefault());

        for (int i = 0; i < 7; i++) {
            datesList.add(dateFormat.format(calendar.getTime()));
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        Log.d("ShowtimeActivity", "Dates List: " + datesList);

        // Khởi tạo Adapter cho danh sách ngày
        dateAdapter = new DateAdapter(datesList, selectedDate -> {
            Log.d("ShowtimeActivity", "Raw selected date: " + selectedDate);

            try {
                // Thêm năm hiện tại vào ngày được chọn
                String currentYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
                String fullDate = selectedDate + " " + currentYear; // "Sat, Dec 14 2024"

                // Định dạng đầu vào và đầu ra
                SimpleDateFormat inputFormat = new SimpleDateFormat("EEE, MMM dd yyyy", Locale.getDefault());
                SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

                // Chuyển đổi ngày
                Date date = inputFormat.parse(fullDate);
                String formattedDate = outputFormat.format(date);

                Log.d("ShowtimeActivity", "Selected Date (formatted): " + formattedDate);

                // Gọi updateCinemasForDate với ngày đã định dạng đúng
                updateCinemasForDate(formattedDate);
            } catch (ParseException e) {
                Log.e("ShowtimeActivity", "Date parsing error: " + e.getMessage());
                e.printStackTrace();
            }
        });



        dateRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        dateRecyclerView.setAdapter(dateAdapter);
    }

    private void setupCinemas() {
        databaseService.getShowtimesByMovie(selectedMovie.getId(), new DatabaseService.OnShowtimesCallback() {
            @Override
            public void onSuccess(List<Showtime> showtimes) {
                Log.d("ShowtimeActivity", "Showtimes received: " + showtimes.size());
                for (Showtime showtime : showtimes) {
                    Log.d("ShowtimeActivity", "Showtime: " + showtime.toString());
                }

                Map<String, List<Showtime>> groupedShowtimes = groupShowtimesByCinema(showtimes);
                Log.d("ShowtimeActivity", "Grouped Showtimes: " + groupedShowtimes.size());

                databaseService.getAllCinemas(new DatabaseService.OnCinemasCallback() {
                    @Override
                    public void onSuccess(List<Cinema> cinemas) {
                        Log.d("ShowtimeActivity", "Cinemas received: " + cinemas.size());
                        cinemaList.clear();
                        for (Cinema cinema : cinemas) {
                            if (groupedShowtimes.containsKey(cinema.getId())) {
                                cinemaList.add(cinema);
                                Log.d("ShowtimeActivity", "Cinema added: " + cinema.toString());
                            }
                        }

                        cinemaAdapter = new CinemaAdapter(cinemaList, groupedShowtimes, selectedShowtime -> {
                            Log.d("ShowtimeActivity", "Showtime selected: " + selectedShowtime.toString());
                        });

                        cinemaRecyclerView.setLayoutManager(new LinearLayoutManager(ShowtimeActivity.this));
                        cinemaRecyclerView.setAdapter(cinemaAdapter);
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        Log.e("ShowtimeActivity", "Failed to load cinemas: " + errorMessage);
                        Toast.makeText(ShowtimeActivity.this, "Failed to load cinemas: " + errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e("ShowtimeActivity", "Failed to load showtimes: " + errorMessage);
                Toast.makeText(ShowtimeActivity.this, "Failed to load showtimes: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateCinemasForDate(String selectedDate) {
        Log.d("ShowtimeActivity", "Updating cinemas for selected date: " + selectedDate);

        databaseService.getShowtimesByMovie(selectedMovie.getId(), new DatabaseService.OnShowtimesCallback() {
            @Override
            public void onSuccess(List<Showtime> showtimes) {
                Log.d("ShowtimeActivity", "Showtimes received for date update: " + showtimes.size());
                for (Showtime showtime : showtimes) {
                    Log.d("ShowtimeActivity", "Showtime start time: " + showtime.getStartTime());
                }

                // Lọc danh sách showtimes theo ngày đã chọn
                List<Showtime> filteredShowtimes = new ArrayList<>();
                for (Showtime showtime : showtimes) {
                    if (showtime.getStartTime() != null && isSameDate(showtime.getStartTime(), selectedDate)) {
                        filteredShowtimes.add(showtime);
                        Log.d("ShowtimeActivity", "Filtered Showtime: " + showtime.toString());
                    }
                }

                Log.d("ShowtimeActivity", "Filtered Showtimes: " + filteredShowtimes.size());

                // Nhóm các suất chiếu đã lọc theo CinemaId
                Map<String, List<Showtime>> groupedShowtimes = groupShowtimesByCinema(filteredShowtimes);
                Log.d("ShowtimeActivity", "Grouped Showtimes after filtering: " + groupedShowtimes.size());

                if (groupedShowtimes.isEmpty()) {
                    showEmptyView();
                    return;
                }

                databaseService.getAllCinemas(new DatabaseService.OnCinemasCallback() {
                    @Override
                    public void onSuccess(List<Cinema> cinemas) {
                        Log.d("ShowtimeActivity", "Cinemas received for date update: " + cinemas.size());
                        cinemaList.clear();
                        for (Cinema cinema : cinemas) {
                            if (groupedShowtimes.containsKey(cinema.getId())) {
                                cinemaList.add(cinema);
                                Log.d("ShowtimeActivity", "Cinema added for date update: " + cinema.toString());
                            }
                        }

                        if (cinemaList.isEmpty()) {
                            showEmptyView();
                        } else {
                            showCinemaList(groupedShowtimes);
                        }
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        Log.e("ShowtimeActivity", "Failed to load cinemas for date update: " + errorMessage);
                        Toast.makeText(ShowtimeActivity.this, "Failed to load cinemas: " + errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e("ShowtimeActivity", "Failed to load showtimes for date update: " + errorMessage);
                Toast.makeText(ShowtimeActivity.this, "Failed to load showtimes: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void showEmptyView() {
        Log.d("ShowtimeActivity", "No data available, showing empty view.");
        RecyclerView cinemaListRecyclerView = findViewById(R.id.cinema_list);
        TextView emptyView = findViewById(R.id.empty_view);

        cinemaListRecyclerView.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);
    }

    private void showCinemaList(Map<String, List<Showtime>> groupedShowtimes) {
        Log.d("ShowtimeActivity", "Showing cinema list with size: " + cinemaList.size());
        RecyclerView cinemaListRecyclerView = findViewById(R.id.cinema_list);
        TextView emptyView = findViewById(R.id.empty_view);

        cinemaListRecyclerView.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);

        if (cinemaAdapter != null) {
            cinemaAdapter.updateData(cinemaList, groupedShowtimes);
        } else {
            Log.e("ShowtimeActivity", "CinemaAdapter is null!");
        }
    }

    private Map<String, List<Showtime>> groupShowtimesByCinema(List<Showtime> showtimes) {
        Map<String, List<Showtime>> groupedShowtimes = new HashMap<>();
        for (Showtime showtime : showtimes) {
            String cinemaId = showtime.getCinemaId();
            if (!groupedShowtimes.containsKey(cinemaId)) {
                groupedShowtimes.put(cinemaId, new ArrayList<>());
            }
            groupedShowtimes.get(cinemaId).add(showtime);
        }
        Log.d("ShowtimeActivity", "Grouped Showtimes: " + groupedShowtimes.size());
        return groupedShowtimes;
    }

    private boolean isSameDate(String showtimeDateTime, String selectedDate) {
        try {
            // Định dạng ngày giờ từ database
            SimpleDateFormat showtimeFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
            // Định dạng ngày từ bộ lọc
            SimpleDateFormat selectedDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

            // Chuyển đổi chuỗi thành đối tượng Date
            Date showtimeDate = showtimeFormat.parse(showtimeDateTime);
            Date selected = selectedDateFormat.parse(selectedDate);

            // So sánh ngày (chỉ lấy phần ngày, bỏ qua giờ)
            boolean isSame = selectedDateFormat.format(showtimeDate).equals(selectedDateFormat.format(selected));
            Log.d("ShowtimeActivity", "Comparing dates: Showtime Date = " + showtimeDate + ", Selected Date = " + selected + ", Result = " + isSame);
            return isSame;
        } catch (ParseException e) {
            Log.e("ShowtimeActivity", "Date comparison error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }


}


