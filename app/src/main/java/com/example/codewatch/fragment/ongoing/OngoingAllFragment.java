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
import com.example.codewatch.adapter.ongoing.OngoingAllAdapter;
import com.example.codewatch.model.Objects;

import java.util.ArrayList;

public class OngoingAllFragment extends Fragment {

    private static final String CONTESTS_ALL_KEY = "CONTESTS_ALL";
    ArrayList<Objects> contestsAll=new ArrayList<>();
    RecyclerView ongoingAllRecyclerView;
    TextView emptyOngoingAll, emptyNetworkIssueOngoingAll;

    public OngoingAllFragment() {
        // Required empty public constructor
    }

    public static OngoingAllFragment newInstance(ArrayList<Objects> contestsAll) {
        OngoingAllFragment fragment = new OngoingAllFragment();
        Bundle args = new Bundle();
        //Log.i("OngoingAllFragment", "The contestsAll size in OngoingAllFragment is " + contestsAll.size());
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
        return inflater.inflate(R.layout.fragment_ongoing_all, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ongoingAllRecyclerView = view.findViewById(R.id.ongoing_all_rv);
        emptyOngoingAll=view.findViewById(R.id.empty_ongoing_all_tv);
        emptyNetworkIssueOngoingAll=view.findViewById(R.id.empty_network_issue_ongoing_all_tv);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        ongoingAllRecyclerView.setLayoutManager(linearLayoutManager);
        //Log.i("OngoingAllFragment", "OngoingAllFragment contestsAll : " + contestsAll.size());
        if(contestsAll!=null && contestsAll.size()!=0)
        {
            ongoingAllRecyclerView.setAdapter(new OngoingAllAdapter(contestsAll));
        }
        else {
            emptyOngoingAll.setVisibility(View.VISIBLE);
            emptyNetworkIssueOngoingAll.setVisibility(View.VISIBLE);
            //Log.i("OngoingAllFragment","The visibility is set for the empty placeholder text views ");
        }

        ItemClickSupport.addTo(ongoingAllRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                //Log.i("OngoingAllFragment", "position of click : " + position);
                showSelectedContestDetail(contestsAll.get(position));
            }
        });

    }

    private void showSelectedContestDetail(Objects contestsAll) {
        //Log.i("OngoingAllFragment", "on click contestsAll.getpos platform name is : " + contestsAll.getResource().getName());
        Intent intentContestDetail = new Intent(getActivity(), ContestDetail.class);
        Bundle extras = new Bundle();
        extras.putParcelable("EXTRA_CONTEST", contestsAll);
        extras.putParcelable("EXTRA_CONTEST_2", contestsAll.getResource());
        extras.putInt("EXTRA_INT",2);
        intentContestDetail.putExtras(extras);
        startActivity(intentContestDetail);
    }
}