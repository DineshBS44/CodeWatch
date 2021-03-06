package com.example.codewatch.fragment.upcoming;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.codewatch.R;
import com.example.codewatch.activity.ContestDetail;
import com.example.codewatch.utils.ItemClickSupport;
import com.example.codewatch.adapter.upcoming.UpcomingShortAdapter;
import com.example.codewatch.model.Objects;

import java.util.ArrayList;

public class UpcomingShortFragment extends Fragment {

    private static final String CONTESTS_SHORT_KEY = "CONTESTS_SHORT";
    ArrayList<Objects> contestsShort = new ArrayList<>();
    RecyclerView upcomingShortRecyclerView;
    TextView emptyUpcomingShort, emptyNetworkIssueUpcomingShort;

    public UpcomingShortFragment() {
        // Required empty public constructor
    }

    public static UpcomingShortFragment newInstance(ArrayList<Objects> contestsShort) {
        UpcomingShortFragment fragment = new UpcomingShortFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(CONTESTS_SHORT_KEY, contestsShort);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            contestsShort = getArguments().getParcelableArrayList(CONTESTS_SHORT_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_upcoming_short, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        upcomingShortRecyclerView = view.findViewById(R.id.upcoming_short_rv);
        emptyUpcomingShort=view.findViewById(R.id.empty_upcoming_short_tv);
        emptyNetworkIssueUpcomingShort=view.findViewById(R.id.empty_network_issue_upcoming_short_tv);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        upcomingShortRecyclerView.setLayoutManager(linearLayoutManager);
        //Log.i("UpcomingShortFragment", "UpcomingShortFragment contestsShort : " + contestsShort.size());

        if(contestsShort==null || contestsShort.size()==0)
        {
            emptyUpcomingShort.setVisibility(View.VISIBLE);
            //emptyNetworkIssueUpcomingShort.setVisibility(View.VISIBLE);
        }
        else {
            upcomingShortRecyclerView.setAdapter(new UpcomingShortAdapter(contestsShort));
        }

        ItemClickSupport.addTo(upcomingShortRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                showSelectedContestDetail(contestsShort.get(position));
            }
        });

    }

    private void showSelectedContestDetail(Objects contestsShort) {
        Intent intentContestDetail = new Intent(getActivity(), ContestDetail.class);
        Bundle extras = new Bundle();
        extras.putParcelable("EXTRA_CONTEST", contestsShort);
        extras.putParcelable("EXTRA_CONTEST_2", contestsShort.getResource());
        extras.putInt("EXTRA_INT",1);
        intentContestDetail.putExtras(extras);
        startActivity(intentContestDetail);
    }
}