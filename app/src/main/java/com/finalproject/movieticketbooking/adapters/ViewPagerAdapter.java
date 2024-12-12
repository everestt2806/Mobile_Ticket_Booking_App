package com.finalproject.movieticketbooking.adapters;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.finalproject.movieticketbooking.fragments.NowPlayingFragment;
import com.finalproject.movieticketbooking.fragments.UpcomingFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Log.e("ViewPagerAdapter", "createFragment called for position: " + position);
        switch (position) {
            case 0:
                return new NowPlayingFragment();
            case 1:
                return new UpcomingFragment();
            default:
                return new NowPlayingFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2; // Số lượng tab
    }
}


