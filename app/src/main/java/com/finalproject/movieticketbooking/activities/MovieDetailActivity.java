package com.finalproject.movieticketbooking.activities;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.finalproject.movieticketbooking.R;
import com.finalproject.movieticketbooking.adapters.ActorAdapter;
import com.finalproject.movieticketbooking.adapters.GenreAdapter;
import com.finalproject.movieticketbooking.models.Movie;
import java.util.List;
import eightbitlab.com.blurview.BlurView;

public class MovieDetailActivity extends AppCompatActivity {

    private ImageView movieImg;
    private TextView movieDetailTitle, year, imdb, summaryText, duration;
    private RecyclerView genreRecyclerView, castRecyclerView;
    private ImageView bookmarkIcon, shareIcon, btnBack;

    private Button buyTicket;


    private BlurView blurView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        // Ánh xạ các view
        blurView = findViewById(R.id.blurView);
        movieImg = findViewById(R.id.movieImg);
        movieDetailTitle = findViewById(R.id.movieDetailTitle);
        year = findViewById(R.id.year);
        duration = findViewById(R.id.duration);
        imdb = findViewById(R.id.imdb);
        summaryText = findViewById(R.id.textView15);
        genreRecyclerView = findViewById(R.id.genre);
        castRecyclerView = findViewById(R.id.castList);
        bookmarkIcon = findViewById(R.id.imageView5);
        shareIcon = findViewById(R.id.imageView6);
        btnBack = findViewById(R.id.btnBack);
        buyTicket = findViewById(R.id.btnBuyTicket);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed(); // hoặc finish()
            }
        });

        // Lấy dữ liệu từ Intent
        Movie movie;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            movie = getIntent().getParcelableExtra("movie", Movie.class);
        } else {
            movie = getIntent().getParcelableExtra("movie");
        }

        buyTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MovieDetailActivity.this, ShowtimeActivity.class);
                intent.putExtra("MOVIE_DATA", movie); // Truyền đối tượng Movie
                startActivity(intent);
            }
        });

        if (movie != null) {
            if ("COMING_SOON".equals(movie.getStatus())) {
                // Nếu phim sắp chiếu, ẩn nút Buy Ticket
                buyTicket.setVisibility(View.GONE);
            } else {
                // Nếu phim đang chiếu, hiện nút và set onClick listener
                buyTicket.setVisibility(View.VISIBLE);
            }
            // Hiển thị dữ liệu movie lên các TextView và ImageView
            movieDetailTitle.setText(movie.getTitle());
            year.setText("Độ tuổi: "+String.valueOf(movie.getAgeRating()));
            duration.setText("Thời lượng: "+ String.valueOf(movie.getDuration()) +" phút");
            imdb.setText("Ngôn ngữ: "+String.valueOf(movie.getLanguage()));
            summaryText.setText(movie.getDescription());


            Glide.with(this)
                    .load(movie.getPoster())
                    .into(movieImg);
            setupBlurView();
            // Hiển thị genre (thể loại)
            List<String> genres = movie.getGenres();
            GenreAdapter genreAdapter = new GenreAdapter(genres);
            genreRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            genreRecyclerView.setAdapter(genreAdapter);

            // Hiển thị danh sách Cast
            List<String> castList = movie.getActors();
            ActorAdapter castAdapter = new ActorAdapter(castList);
            castRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            castRecyclerView.setAdapter(castAdapter);
        }

        // Handle Bookmark and Share actions (you can add click listeners if needed)
        bookmarkIcon.setOnClickListener(v -> {
            // Handle bookmark action
        });

        shareIcon.setOnClickListener(v -> {
            // Handle share action
        });
    }

    private void setupBlurView() {
        float blurRadius = 10f; // Độ mờ
        ViewGroup rootView = findViewById(R.id.main);
        Drawable windowBackground = getWindow().getDecorView().getBackground();

        blurView.setupWith(rootView)
                .setFrameClearDrawable(windowBackground)
                .setBlurRadius(blurRadius) ;
        blurView.setOutlineProvider(ViewOutlineProvider.BACKGROUND);
        blurView.setClipToOutline(true);
    }
}
