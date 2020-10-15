package com.example.codewatch.adapter.upcoming;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.codewatch.fragment.upcoming.UpcomingAllFragment;
import com.example.codewatch.fragment.upcoming.UpcomingLongFragment;
import com.example.codewatch.fragment.upcoming.UpcomingShortFragment;
import com.example.codewatch.model.Objects;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class UpcomingAdapter extends FragmentStateAdapter {

    private static final int N_PAGES = 3;
    ArrayList<Objects> contestsAll, contestsShort, contestsLong;

    public UpcomingAdapter(@NonNull Fragment fragment, ArrayList<Objects> contestsAll, ArrayList<Objects> contestsShort, ArrayList<Objects> contestsLong) {
        super(fragment);
        this.contestsAll = contestsAll;
        this.contestsShort = contestsShort;
        this.contestsLong = contestsLong;
        Log.i("UpcomingAdapter", "The contestsAll size in UpcomingAdapter2 is " + this.contestsAll.size());
    }


    @NotNull
    @Override
    public Fragment createFragment(int position) {
        Log.i("UpcomingAdapter", "The contestsAll size in UpcomingAdapter is " + contestsAll.size());
        switch (position) {
            case 0:
                return UpcomingAllFragment.newInstance(contestsAll);
            case 1:
                return UpcomingShortFragment.newInstance(contestsShort);
            case 2:
                return UpcomingLongFragment.newInstance(contestsLong);
        }
        return UpcomingAllFragment.newInstance(contestsAll);
    }

    @Override
    public int getItemCount() {
        return N_PAGES;
    }
}
