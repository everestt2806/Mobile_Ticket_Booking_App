package com.finalproject.movieticketbooking.adapters;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.finalproject.movieticketbooking.R;
import com.finalproject.movieticketbooking.databinding.ItemDateBinding;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

public class DateAdapter extends RecyclerView.Adapter<DateAdapter.DateViewHolder> {
    private List<LocalDate> dates;
    private OnDateSelectedListener listener;
    private int selectedPosition = 0;

    public DateAdapter(List<LocalDate> dates, OnDateSelectedListener listener) {
        this.dates = dates;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemDateBinding binding = ItemDateBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new DateViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DateViewHolder holder, int position) {
        holder.bind(dates.get(position), position == selectedPosition);
    }

    @Override
    public int getItemCount() {
        return dates.size();
    }

    class DateViewHolder extends RecyclerView.ViewHolder {
        private final ItemDateBinding binding;

        DateViewHolder(ItemDateBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            itemView.setOnClickListener(v -> {
                int oldPosition = selectedPosition;
                selectedPosition = getAdapterPosition();
                notifyItemChanged(oldPosition);
                notifyItemChanged(selectedPosition);
                listener.onDateSelected(dates.get(selectedPosition));
            });
        }

        void bind(LocalDate date, boolean isSelected) {
            // Format day of week
            String dayOfWeek = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                dayOfWeek = date.getDayOfWeek()
                        .getDisplayName(TextStyle.SHORT, Locale.getDefault());
            }

            // Format day of month
            String dayOfMonth = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                dayOfMonth = String.valueOf(date.getDayOfMonth());
            }

            // Set text
            binding.tvDayOfWeek.setText(dayOfWeek);
            binding.tvDayOfMonth.setText(dayOfMonth);

            // Update selected state
            binding.getRoot().setSelected(isSelected);
            binding.tvDayOfWeek.setSelected(isSelected);
            binding.tvDayOfMonth.setSelected(isSelected);
        }
    }

    public interface OnDateSelectedListener {
        void onDateSelected(LocalDate date);
    }
}
