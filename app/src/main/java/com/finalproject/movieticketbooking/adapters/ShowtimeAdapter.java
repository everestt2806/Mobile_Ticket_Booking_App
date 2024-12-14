package com.finalproject.movieticketbooking.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.finalproject.movieticketbooking.R;
import com.finalproject.movieticketbooking.models.Showtime;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ShowtimeAdapter extends RecyclerView.Adapter<ShowtimeAdapter.ShowtimeViewHolder> {
    private List<Showtime> showtimes;
    private ShowtimeSelectedListener listener;

    public interface ShowtimeSelectedListener {
        void onShowtimeSelected(Showtime showtime);
    }

    public ShowtimeAdapter(List<Showtime> showtimes, ShowtimeSelectedListener listener) {
        this.showtimes = showtimes;
        this.listener = listener;
    }

    public class ShowtimeViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTime;
        private TextView tvPrice;
        private View itemView;

        public ShowtimeViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            tvTime = itemView.findViewById(R.id.tvTime);
            tvPrice = itemView.findViewById(R.id.tvPrice);
        }

        public void bind(Showtime showtime) {
            try {
                // Xử lý thời gian
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
                SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
                Date date = inputFormat.parse(showtime.getStartTime());
                String time = outputFormat.format(date);
                tvTime.setText(time);

                // Xử lý giá tiền
                NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
                Map<String, Integer> prices = showtime.getPrice();
                // Giả sử "standard" là key cho giá vé thường
                int standardPrice = prices.getOrDefault("standard", 0);
                tvPrice.setText(formatPrice(standardPrice));


                itemView.setOnClickListener(v -> listener.onShowtimeSelected(showtime));
            } catch (ParseException e) {
                e.printStackTrace();
                // Fallback nếu parse thời gian bị lỗi
                tvTime.setText(showtime.getStartTime());
                tvPrice.setText("N/A");
            }
        }


        private String formatPrice(int price) {
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
            return currencyFormat.format(price).replace("₫", "VND");
        }

    }

    @NonNull
    @Override
    public ShowtimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_showtime, parent, false);
        return new ShowtimeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowtimeViewHolder holder, int position) {
        Log.d("CinemaShowtimeAdapter", "Binding cinema: " + showtimes.get(position).getCinemaId() + showtimes.get(position).getMovieId());
        holder.bind(showtimes.get(position));
    }

    @Override
    public int getItemCount() {
        return showtimes.size();
    }
}

