package com.example.codewatch.fragment.upcoming;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.codewatch.R;
import com.example.codewatch.adapter.upcoming.UpcomingAllAdapter;
import com.example.codewatch.model.Objects;

import java.util.ArrayList;

public class UpcomingAllFragment extends Fragment {

    private static final String CONTESTS_ALL_KEY = "CONTESTS_ALL";
    ArrayList<Objects> contestsAll = new ArrayList<>();
    RecyclerView upcomingAllRecyclerView;

    public UpcomingAllFragment() {
        // Required empty public constructor
    }

    public static UpcomingAllFragment newInstance(ArrayList<Objects> contestsAll) {
        UpcomingAllFragment fragment = new UpcomingAllFragment();
        Bundle args = new Bundle();
        Log.i("UpcomingAllFragment","The contestsAll size in UpcomingAllFragment is "+contestsAll.size());
        args.putParcelableArrayList(CONTESTS_ALL_KEY, contestsAll);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            contestsAll = getArguments().getParcelableArrayList(CONTESTS_ALL_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_upcoming_all, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        upcomingAllRecyclerView = view.findViewById(R.id.upcoming_all_rv);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        upcomingAllRecyclerView.setLayoutManager(linearLayoutManager);
        Log.i("UpcomingAllFragment","The contestsAll size in UpcomingAllFragment is "+contestsAll.size());
        upcomingAllRecyclerView.setAdapter(new UpcomingAllAdapter(contestsAll));

    }
}