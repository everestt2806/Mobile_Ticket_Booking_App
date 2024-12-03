package com.finalproject.movieticketbooking.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.finalproject.movieticketbooking.R;

import java.util.List;

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.GenreViewHolder> {

    private List<String> genres;

    public GenreAdapter(List<String> genres) {
        this.genres = genres;
    }

    @Override
    public GenreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_genre, parent, false);
        return new GenreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GenreViewHolder holder, int position) {
        holder.genreText.setText(genres.get(position));
    }

    @Override
    public int getItemCount() {
        return genres.size();
    }

    static class GenreViewHolder extends RecyclerView.ViewHolder {
        TextView genreText;

        public GenreViewHolder(View itemView) {
            super(itemView);
            genreText = itemView.findViewById(R.id.genreText);
        }
    }
}

