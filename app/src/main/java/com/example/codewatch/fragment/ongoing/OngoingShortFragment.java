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
import com.example.codewatch.adapter.ItemClickSupport;
import com.example.codewatch.adapter.ongoing.OngoingShortAdapter;
import com.example.codewatch.model.Objects;

import java.util.ArrayList;

public class OngoingShortFragment extends Fragment {

    private static final String CONTESTS_SHORT_KEY = "CONTESTS_SHORT";
    ArrayList<Objects> contestsShort = new ArrayList<>();
    RecyclerView ongoingShortRecyclerView;
    TextView emptyOngoingShort, emptyNetworkIssueOngoingShort;

    public OngoingShortFragment() {
        // Required empty public constructor
    }

    public static OngoingShortFragment newInstance(ArrayList<Objects> contestsShort) {
        OngoingShortFragment fragment = new OngoingShortFragment();
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
        return inflater.inflate(R.layout.fragment_ongoing_short, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ongoingShortRecyclerView = view.findViewById(R.id.ongoing_short_rv);
        emptyOngoingShort=view.findViewById(R.id.empty_ongoing_short_tv);
        emptyNetworkIssueOngoingShort=view.findViewById(R.id.empty_network_issue_ongoing_short_tv);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        ongoingShortRecyclerView.setLayoutManager(linearLayoutManager);

        if(contestsShort==null || contestsShort.size()==0)
        {
            emptyOngoingShort.setVisibility(View.VISIBLE);
            emptyNetworkIssueOngoingShort.setVisibility(View.VISIBLE);
        }
        else {
            ongoingShortRecyclerView.setAdapter(new OngoingShortAdapter(contestsShort));
        }

        ItemClickSupport.addTo(ongoingShortRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
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
        intentContestDetail.putExtras(extras);
        startActivity(intentContestDetail);
    }
}