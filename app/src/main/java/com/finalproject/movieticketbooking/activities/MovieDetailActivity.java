package com.finalproject.movieticketbooking.activities;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.finalproject.movieticketbooking.R;
import com.finalproject.movieticketbooking.adapters.CastAdapter;
import com.finalproject.movieticketbooking.adapters.GenreAdapter;
import com.finalproject.movieticketbooking.models.Cast;
import com.finalproject.movieticketbooking.models.Movie;
import java.util.List;
import eightbitlab.com.blurview.BlurView;

public class MovieDetailActivity extends AppCompatActivity {

    private ImageView movieImg;
    private TextView movieDetailTitle, year, imdb, summaryText, castTitle, duration;
    private RecyclerView genreRecyclerView, castRecyclerView;
    private ImageView bookmarkIcon, shareIcon, btnBack;


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
        castTitle = findViewById(R.id.textView16);
        genreRecyclerView = findViewById(R.id.genre);
        castRecyclerView = findViewById(R.id.castList);
        bookmarkIcon = findViewById(R.id.imageView5);
        shareIcon = findViewById(R.id.imageView6);
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        // Lấy dữ liệu từ Intent
        Intent intent = getIntent();
        Movie movie = intent.getParcelableExtra("movie");

        if (movie != null) {
            // Hiển thị dữ liệu movie lên các TextView và ImageView
            movieDetailTitle.setText(movie.getTitle());
            year.setText("Year: "+String.valueOf(movie.getYear()));
            duration.setText("Duration: "+ String.valueOf(movie.getTime()));
            imdb.setText("IMDb Rating: "+String.valueOf(movie.getImdb())+ "/10");
            summaryText.setText(movie.getDescription());

            // Tải hình ảnh cho poster sử dụng Glide (hoặc Picasso)
            Glide.with(this)
                    .load(movie.getPoster()) // URL của poster
                    .into(movieImg);
            setupBlurView();
            // Hiển thị genre (thể loại)
            List<String> genres = movie.getGenre();
            GenreAdapter genreAdapter = new GenreAdapter(genres);
            genreRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            genreRecyclerView.setAdapter(genreAdapter);

            // Hiển thị danh sách Cast
            List<Cast> castList = movie.getCasts();
            CastAdapter castAdapter = new CastAdapter(castList);
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
