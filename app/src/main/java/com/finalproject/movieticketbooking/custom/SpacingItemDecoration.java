package com.finalproject.movieticketbooking.custom;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SpacingItemDecoration extends RecyclerView.ItemDecoration {
    private final int spacing; // Khoảng cách giữa các item

    public SpacingItemDecoration(int spacing) {
        this.spacing = spacing;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        // Áp dụng khoảng cách cho các item (chỉ áp dụng khoảng cách dưới)
        outRect.left = spacing;
        outRect.right = spacing;
    }
}
