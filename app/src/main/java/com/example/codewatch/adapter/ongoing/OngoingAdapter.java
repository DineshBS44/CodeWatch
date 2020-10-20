package com.example.codewatch.adapter.ongoing;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.codewatch.fragment.ongoing.OngoingAllFragment;
import com.example.codewatch.fragment.ongoing.OngoingLongFragment;
import com.example.codewatch.fragment.ongoing.OngoingShortFragment;
import com.example.codewatch.model.Objects;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class OngoingAdapter extends FragmentStateAdapter {

    private static final int N_PAGES = 3;
    ArrayList<Objects> contestsAll, contestsShort, contestsLong;

    public OngoingAdapter(@NonNull Fragment fragment, ArrayList<Objects> contestsAll, ArrayList<Objects> contestsShort, ArrayList<Objects> contestsLong) {
        super(fragment);
        this.contestsAll = contestsAll;
        this.contestsShort = contestsShort;
        this.contestsLong = contestsLong;
    }


    @NotNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return OngoingAllFragment.newInstance(contestsAll);
            case 1:
                return OngoingShortFragment.newInstance(contestsShort);
            case 2:
                return OngoingLongFragment.newInstance(contestsLong);
        }
        return OngoingAllFragment.newInstance(contestsAll);
    }

    @Override
    public int getItemCount() {
        return N_PAGES;
    }
}
