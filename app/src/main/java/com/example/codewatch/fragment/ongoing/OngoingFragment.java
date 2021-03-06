package com.example.codewatch.fragment.ongoing;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.codewatch.R;
import com.example.codewatch.adapter.ongoing.OngoingAdapter;
import com.example.codewatch.model.Objects;

import java.util.ArrayList;

public class OngoingFragment extends Fragment {

    private static final String TAG = "OngoingFragment";
    private static final String CONTESTS_KEY_ALL = "CONTESTS_ALL";
    private static final String CONTESTS_KEY_SHORT = "CONTESTS_SHORT";
    private static final String CONTESTS_KEY_LONG = "CONTESTS_LONG";
    ArrayList<Objects> contestsAll = new ArrayList<>();
    ArrayList<Objects> contestsShort = new ArrayList<>();
    ArrayList<Objects> contestsLong = new ArrayList<>();
    ViewPager2 viewPager;
    TextView allTextView, shortTextView, longTextView;

    public static OngoingFragment newInstance(ArrayList<Objects> contestsAll, ArrayList<Objects> contestsShort, ArrayList<Objects> contestsLong) {
        OngoingFragment fragment = new OngoingFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(CONTESTS_KEY_ALL, contestsAll);
        args.putParcelableArrayList(CONTESTS_KEY_SHORT, contestsShort);
        args.putParcelableArrayList(CONTESTS_KEY_LONG, contestsLong);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            contestsAll = getArguments().getParcelableArrayList(CONTESTS_KEY_ALL);
            contestsShort = getArguments().getParcelableArrayList(CONTESTS_KEY_SHORT);
            contestsLong = getArguments().getParcelableArrayList(CONTESTS_KEY_LONG);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //getActivity().setTitle(Html.fromHtml("<font color='#2E2E2E'>Ongoing </font>"));
        return inflater.inflate(R.layout.fragment_ongoing, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPager = view.findViewById(R.id.ongoing_viewpager);
        allTextView = view.findViewById(R.id.ongoing_all_tv);
        shortTextView = view.findViewById(R.id.ongoing_short_tv);
        longTextView = view.findViewById(R.id.ongoing_long_tv);
        viewPager.setNestedScrollingEnabled(true);
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        shortTextView.setTextAppearance(R.style.TitleTextDisabled);
                        longTextView.setTextAppearance(R.style.TitleTextDisabled);
                        allTextView.setTextAppearance(R.style.TitleText);
                        break;
                    case 1:
                        shortTextView.setTextAppearance(R.style.TitleText);
                        longTextView.setTextAppearance(R.style.TitleTextDisabled);
                        allTextView.setTextAppearance(R.style.TitleTextDisabled);
                        break;
                    case 2:
                        shortTextView.setTextAppearance(R.style.TitleTextDisabled);
                        longTextView.setTextAppearance(R.style.TitleText);
                        allTextView.setTextAppearance(R.style.TitleTextDisabled);
                        break;
                }
            }
        });
        allTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0, true);
            }
        });
        shortTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1, true);
            }
        });
        longTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(2, true);
            }
        });

        fetchDataAll();

    }

    private void fetchDataAll() {
        viewPager.setAdapter(new OngoingAdapter(OngoingFragment.this, contestsAll, contestsShort, contestsLong));
    }


}