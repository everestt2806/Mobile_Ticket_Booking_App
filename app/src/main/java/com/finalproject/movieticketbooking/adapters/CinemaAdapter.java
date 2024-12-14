package com.finalproject.movieticketbooking.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.finalproject.movieticketbooking.R;
import com.finalproject.movieticketbooking.models.Cinema;
import com.finalproject.movieticketbooking.models.Showtime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CinemaAdapter extends RecyclerView.Adapter<CinemaAdapter.CinemaViewHolder> {

    private List<Cinema> cinemas; // Danh sách các rạp
    private Map<String, List<Showtime>> groupedShowtimes; // Map nhóm các suất chiếu theo id rạp
    private final OnShowtimeSelectedListener listener; // Listener xử lý sự kiện click vào suất chiếu

    public CinemaAdapter(List<Cinema> cinemas, Map<String, List<Showtime>> groupedShowtimes, OnShowtimeSelectedListener listener) {
        this.cinemas = cinemas != null ? cinemas : new ArrayList<>();
        this.groupedShowtimes = groupedShowtimes != null ? groupedShowtimes : new HashMap<>();
        this.listener = listener;
    }

    @NonNull
    @Override
    public CinemaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cinema, parent, false);
        return new CinemaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CinemaViewHolder holder, int position) {
        // Kiểm tra để tránh lỗi IndexOutOfBoundsException
        if (cinemas == null || position >= cinemas.size()) {
            Log.e("CinemaAdapter", "Invalid position: " + position + ", List size: " + (cinemas == null ? 0 : cinemas.size()));
            return;
        }

        // Lấy thông tin rạp tại vị trí hiện tại
        Cinema cinema = cinemas.get(position);

        // Hiển thị thông tin rạp
        holder.cinemaName.setText(cinema.getName());
        holder.cinemaAddress.setText(cinema.getAddress());

        // Lấy danh sách suất chiếu của rạp này từ groupedShowtimes
        List<Showtime> showtimes = groupedShowtimes.get(cinema.getId());

        // Nếu không có suất chiếu, khởi tạo danh sách rỗng
        if (showtimes == null) {
            showtimes = new ArrayList<>();
        }

        // Xóa các nút cũ để tránh lỗi khi tái sử dụng ViewHolder
        holder.showtimeContainer.removeAllViews();

        // Tạo nút động cho từng suất chiếu
        for (Showtime showtime : showtimes) {
            Button showtimeButton = new Button(holder.itemView.getContext());
            showtimeButton.setText(formatTime(showtime.getStartTime())); // Hiển thị giờ chiếu
            showtimeButton.setBackgroundTintList(holder.itemView.getContext().getResources().getColorStateList(R.color.main_color)); // Đặt màu nền
            showtimeButton.setTextColor(holder.itemView.getContext().getResources().getColor(android.R.color.white)); // Đặt màu chữ
            showtimeButton.setPadding(16, 8, 16, 8);

            // Xử lý sự kiện click vào suất chiếu
            showtimeButton.setOnClickListener(v -> listener.onShowtimeSelected(showtime));

            // Thêm nút vào container
            holder.showtimeContainer.addView(showtimeButton);
        }
    }

    @Override
    public int getItemCount() {
        return cinemas == null ? 0 : cinemas.size(); // Trả về số lượng rạp
    }

    /**
     * Cập nhật dữ liệu cho Adapter
     *
     * @param cinemas           Danh sách rạp
     * @param groupedShowtimes  Map nhóm suất chiếu theo id rạp
     */
    public void updateData(List<Cinema> cinemas, Map<String, List<Showtime>> groupedShowtimes) {
        this.cinemas = cinemas != null ? cinemas : new ArrayList<>();
        this.groupedShowtimes = groupedShowtimes != null ? groupedShowtimes : new HashMap<>();
        notifyDataSetChanged(); // Cập nhật RecyclerView
    }

    /**
     * Format giờ chiếu từ chuỗi thời gian
     *
     * @param startTime Chuỗi thời gian (ISO format)
     * @return Chuỗi thời gian đã được format (ví dụ: "10:00 AM")
     */
    private String formatTime(String startTime) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        try {
            return outputFormat.format(inputFormat.parse(startTime));
        } catch (Exception e) {
            e.printStackTrace();
            return ""; // Trả về chuỗi rỗng nếu có lỗi
        }
    }

    /**
     * ViewHolder cho CinemaAdapter
     */
    static class CinemaViewHolder extends RecyclerView.ViewHolder {
        TextView cinemaName, cinemaAddress;
        LinearLayout showtimeContainer; // Container để chứa các nút suất chiếu

        public CinemaViewHolder(@NonNull View itemView) {
            super(itemView);
            cinemaName = itemView.findViewById(R.id.cinema_name);
            cinemaAddress = itemView.findViewById(R.id.cinema_address);
            showtimeContainer = itemView.findViewById(R.id.showtime_container); // Ánh xạ container
        }
    }

    /**
     * Interface để xử lý sự kiện chọn suất chiếu
     */
    public interface OnShowtimeSelectedListener {
        void onShowtimeSelected(Showtime showtime);
    }
}
