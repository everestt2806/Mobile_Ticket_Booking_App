package com.finalproject.movieticketbooking.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.finalproject.movieticketbooking.R;

import java.util.List;

public class ActorAdapter extends RecyclerView.Adapter<ActorAdapter.ActorViewHolder> {

    private List<String> actors;

    public ActorAdapter(List<String> actors) {
        this.actors = actors;
    }

    @Override
    public ActorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_actor, parent, false);
        return new ActorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ActorViewHolder holder, int position) {
        holder.actorNameText.setText(actors.get(position));
    }

    @Override
    public int getItemCount() {
        return actors.size();
    }

    static class ActorViewHolder extends RecyclerView.ViewHolder {
        TextView actorNameText;

        public ActorViewHolder(View itemView) {
            super(itemView);
            actorNameText = itemView.findViewById(R.id.actorName);
        }
    }
}
