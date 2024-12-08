package com.finalproject.movieticketbooking.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.finalproject.movieticketbooking.R;
import com.finalproject.movieticketbooking.databinding.ItemCinemaShowtimeBinding;
import com.finalproject.movieticketbooking.models.CinemaWithShowtimes;
import com.finalproject.movieticketbooking.models.Showtime;
import com.google.android.flexbox.FlexboxLayout;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CinemaShowtimeAdapter extends RecyclerView.Adapter<CinemaShowtimeAdapter.ViewHolder> {
    private List<CinemaWithShowtimes> cinemaWithShowtimesList;
    private OnShowtimeSelectedListener listener;

    // Interface để xử lý sự kiện chọn showtime
    public interface OnShowtimeSelectedListener {
        void onShowtimeSelected(Showtime showtime);
    }

    // Constructor
    public CinemaShowtimeAdapter(List<CinemaWithShowtimes> cinemaWithShowtimesList, OnShowtimeSelectedListener listener) {
        this.cinemaWithShowtimesList = cinemaWithShowtimesList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Sử dụng ViewBinding để inflate layout
        ItemCinemaShowtimeBinding binding = ItemCinemaShowtimeBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Gắn dữ liệu vào ViewHolder
        CinemaWithShowtimes item = cinemaWithShowtimesList.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return cinemaWithShowtimesList == null ? 0 : cinemaWithShowtimesList.size();
    }

    // ViewHolder class
    class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemCinemaShowtimeBinding binding;

        ViewHolder(ItemCinemaShowtimeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(CinemaWithShowtimes item) {
            // Gắn tên và địa chỉ rạp
            binding.tvCinemaName.setText(item.getCinema().getName());
            binding.tvCinemaAddress.setText(item.getCinema().getAddress());

            // Xóa các showtimes trước đó (nếu có)
            binding.flexboxShowtimes.removeAllViews();

            // Định dạng giờ hiển thị
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
            List<Showtime> showtimes = item.getShowtimes();

            // Kiểm tra nếu không có suất chiếu
            if (showtimes == null || showtimes.isEmpty()) {
                TextView noShowtimes = new TextView(binding.getRoot().getContext());
                noShowtimes.setText("No showtimes available");
                noShowtimes.setTextSize(14);
                binding.flexboxShowtimes.addView(noShowtimes);
                return;
            }

            // Thêm các nút showtime vào FlexboxLayout
            for (Showtime showtime : showtimes) {
                Button btnShowtime = createShowtimeButton(showtime, sdf);
                binding.flexboxShowtimes.addView(btnShowtime);
            }
        }

        // Tạo nút showtime
        private Button createShowtimeButton(Showtime showtime, SimpleDateFormat outputFormat) {
            Button btnShowtime = new Button(binding.getRoot().getContext());

            try {
                // Định dạng thời gian từ chuỗi ISO 8601
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
                Date date = inputFormat.parse(showtime.getStartTime());
                String time = outputFormat.format(date);
                btnShowtime.setText(time);
            } catch (Exception e) {
                btnShowtime.setText("Invalid Time");
                e.printStackTrace();
            }

            // Thiết lập kiểu dáng cho nút
            btnShowtime.setTextSize(14);
            btnShowtime.setPadding(24, 12, 24, 12);
            btnShowtime.setBackgroundResource(R.drawable.bg_showtime_button);

            // Thiết lập layout params cho FlexboxLayout
            FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(8, 8, 8, 8);
            btnShowtime.setLayoutParams(params);

            // Xử lý sự kiện khi người dùng nhấn vào nút
            btnShowtime.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onShowtimeSelected(showtime);
                }
            });

            return btnShowtime;
        }
    }
}
