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

import java.util.ArrayList;
import java.util.List;

// BannerAdapter.java
public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.BannerViewHolder> {
    private List<Banner> banners;

    public BannerAdapter(List<Banner> banners) {
        this.banners = banners != null ? banners : new ArrayList<>();
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
        if (banners != null && !banners.isEmpty()) {
            try {
                Banner banner = banners.get(position % banners.size());
                if (banner != null) {
                    try {
                        Glide.with(holder.itemView.getContext())
                                .load(banner.getImage())
                                .placeholder(R.drawable.placeholder_movie)
                                .error(R.drawable.placeholder_movie)
                                .centerCrop()
                                .into(holder.imageView);
                    } catch (Exception e) {
                        holder.imageView.setImageResource(R.drawable.placeholder_movie);
                    }
                }
            } catch (Exception e) {
                holder.imageView.setImageResource(R.drawable.placeholder_movie);
            }
        }
    }

    @Override
    public int getItemCount() {
        return banners != null ? banners.size() : 0;
    }

    public void updateBanners(List<Banner> newBanners) {
        this.banners = newBanners != null ? newBanners : new ArrayList<>();
        notifyDataSetChanged();
    }

    static class BannerViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public BannerViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.bannerImage);
        }
    }
}

