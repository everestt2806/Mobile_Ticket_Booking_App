package com.finalproject.movieticketbooking.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.finalproject.movieticketbooking.R;
import com.finalproject.movieticketbooking.models.Banner;

import java.util.List;

// BannerAdapter.java
public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.BannerViewHolder> {
    private List<Banner> banners;

    public BannerAdapter(List<Banner> banners) {
        this.banners = banners;
    }

    @NonNull
    @Override
    public BannerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_banner, parent, false);
        return new BannerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BannerViewHolder holder, int position) {
        if (banners != null && banners.size() > 0) {
            Banner banner = banners.get(position % banners.size());
            // Load image using Glide with a check for null image URL
            Glide.with(holder.itemView.getContext())
                    .load(banner.getImage() != null ? banner.getImage() : R.drawable.placeholder_movie)
                    .into(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        return banners != null ? banners.size() : 0;  // Avoid returning Integer.MAX_VALUE if not necessary
    }

    static class BannerViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public BannerViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.bannerImage);
        }
    }
}

