package com.example.codewatch.fragment.upcoming;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.codewatch.R;
import com.example.codewatch.activity.ContestDetail;
import com.example.codewatch.adapter.ItemClickSupport;
import com.example.codewatch.adapter.upcoming.UpcomingLongAdapter;
import com.example.codewatch.model.Objects;

import java.util.ArrayList;

public class UpcomingLongFragment extends Fragment {

    private static final String CONTESTS_LONG_KEY = "CONTESTS_LONG";
    ArrayList<Objects> contestsLong = new ArrayList<>();
    RecyclerView upcomingLongRecyclerView;

    public UpcomingLongFragment() {
        // Required empty public constructor
    }

    public static UpcomingLongFragment newInstance(ArrayList<Objects> contestsLong) {
        UpcomingLongFragment fragment = new UpcomingLongFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(CONTESTS_LONG_KEY,contestsLong);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            contestsLong=getArguments().getParcelableArrayList(CONTESTS_LONG_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_upcoming_long, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        upcomingLongRecyclerView = view.findViewById(R.id.upcoming_long_rv);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        upcomingLongRecyclerView.setLayoutManager(linearLayoutManager);
        Log.i("UpcomingLongFragment","UpcomingLongFragment contestsLong : "+contestsLong.size());
        upcomingLongRecyclerView.setAdapter(new UpcomingLongAdapter(contestsLong));

        ItemClickSupport.addTo(upcomingLongRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                showSelectedContestDetail(contestsLong.get(position));
            }
        });

    }

    private void showSelectedContestDetail(Objects contestsLong) {
        Intent intentContestDetail = new Intent(getActivity(), ContestDetail.class);
        intentContestDetail.putExtra("EXTRA_CONTEST", contestsLong);
        startActivity(intentContestDetail);
    }
}