package com.finalproject.movieticketbooking.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.finalproject.movieticketbooking.R;
import com.finalproject.movieticketbooking.models.Cast;

import java.util.List;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.CastViewHolder> {

    private List<Cast> casts;

    public CastAdapter(List<Cast> casts) {
        this.casts = casts;
    }

    @Override
    public CastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cast, parent, false);
        return new CastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CastViewHolder holder, int position) {
        Cast cast = casts.get(position);
        holder.actorName.setText(cast.getActor());
    }

    @Override
    public int getItemCount() {
        return casts.size();
    }

    static class CastViewHolder extends RecyclerView.ViewHolder {
        TextView actorName;

        public CastViewHolder(View itemView) {
            super(itemView);
            actorName = itemView.findViewById(R.id.actorName);
        }
    }
}

