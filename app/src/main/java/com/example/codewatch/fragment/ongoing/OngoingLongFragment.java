package com.example.codewatch.fragment.ongoing;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codewatch.R;
import com.example.codewatch.activity.ContestDetail;
import com.example.codewatch.utils.ItemClickSupport;
import com.example.codewatch.adapter.ongoing.OngoingLongAdapter;
import com.example.codewatch.model.Objects;

import java.util.ArrayList;

public class OngoingLongFragment extends Fragment {

    private static final String CONTESTS_LONG_KEY = "CONTESTS_LONG";
    ArrayList<Objects> contestsLong = new ArrayList<>();
    RecyclerView ongoingLongRecyclerView;
    TextView emptyOngoingLong, emptyNetworkIssueOngoingLong;

    public OngoingLongFragment() {
        // Required empty public constructor
    }

    public static OngoingLongFragment newInstance(ArrayList<Objects> contestsLong) {
        OngoingLongFragment fragment = new OngoingLongFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(CONTESTS_LONG_KEY, contestsLong);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            contestsLong = getArguments().getParcelableArrayList(CONTESTS_LONG_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ongoing_long, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ongoingLongRecyclerView = view.findViewById(R.id.ongoing_long_rv);
        emptyOngoingLong=view.findViewById(R.id.empty_ongoing_long_tv);
        emptyNetworkIssueOngoingLong=view.findViewById(R.id.empty_network_issue_ongoing_long_tv);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        ongoingLongRecyclerView.setLayoutManager(linearLayoutManager);

        if(contestsLong==null || contestsLong.size()==0)
        {
            emptyOngoingLong.setVisibility(View.VISIBLE);
            //emptyNetworkIssueOngoingLong.setVisibility(View.VISIBLE);
        }
        else
            ongoingLongRecyclerView.setAdapter(new OngoingLongAdapter(contestsLong));

        ItemClickSupport.addTo(ongoingLongRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                showSelectedContestDetail(contestsLong.get(position));
            }
        });

    }

    private void showSelectedContestDetail(Objects contestsLong) {
        Intent intentContestDetail = new Intent(getActivity(), ContestDetail.class);
        Bundle extras = new Bundle();
        extras.putParcelable("EXTRA_CONTEST", contestsLong);
        extras.putParcelable("EXTRA_CONTEST_2", contestsLong.getResource());
        extras.putInt("EXTRA_INT",2);
        intentContestDetail.putExtras(extras);
        startActivity(intentContestDetail);
    }
}