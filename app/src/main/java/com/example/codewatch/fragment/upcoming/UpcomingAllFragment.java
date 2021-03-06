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
import android.widget.TextView;

import com.example.codewatch.R;
import com.example.codewatch.activity.ContestDetail;
import com.example.codewatch.utils.ItemClickSupport;
import com.example.codewatch.adapter.upcoming.UpcomingAllAdapter;
import com.example.codewatch.model.Objects;
import com.example.codewatch.utils.OverlayFrame;

import java.util.ArrayList;

public class UpcomingAllFragment extends Fragment {

    private static final String CONTESTS_ALL_KEY = "CONTESTS_ALL";
    ArrayList<Objects> contestsAll=new ArrayList<>();
    RecyclerView upcomingAllRecyclerView;
    TextView emptyUpcomingAll, emptyNetworkIssueUpcomingAll;
    OverlayFrame overlayFrame;

    public UpcomingAllFragment() {
        // Required empty public constructor
    }

    public static UpcomingAllFragment newInstance(ArrayList<Objects> contestsAll) {
        UpcomingAllFragment fragment = new UpcomingAllFragment();
        Bundle args = new Bundle();
        //Log.i("UpcomingAllFragment", "The contestsAll size in UpcomingAllFragment is " + contestsAll.size());
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
        overlayFrame=getActivity().findViewById(R.id.overlay_frame);
        //overlayFrame.displayOverlay(true);
        upcomingAllRecyclerView = view.findViewById(R.id.upcoming_all_rv);
        emptyUpcomingAll=view.findViewById(R.id.empty_upcoming_all_tv);
        emptyNetworkIssueUpcomingAll=view.findViewById(R.id.empty_network_issue_upcoming_all_tv);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        upcomingAllRecyclerView.setLayoutManager(linearLayoutManager);
        //Log.i("UpcomingAllFragment", "UpcomingAllFragment contestsAll : " + contestsAll.size());
        if(contestsAll!=null && contestsAll.size()!=0)
        {
            upcomingAllRecyclerView.setAdapter(new UpcomingAllAdapter(contestsAll));
            //overlayFrame.displayOverlay(false);
        }
        else {
            emptyUpcomingAll.setVisibility(View.VISIBLE);
            //emptyNetworkIssueUpcomingAll.setVisibility(View.VISIBLE);
            //Log.i("UpcomingAllFragment","The visibility is set for the emtpy placeholder text views ");
            //overlayFrame.displayOverlay(false);
        }
        //overlayFrame.displayOverlay(false);

        ItemClickSupport.addTo(upcomingAllRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Log.i("UpcomingAllFragment", "position of click : " + position);
                //Log.i("UpcomingAllFragment", "on click contestsAll size is : " + contestsAll.size());
                showSelectedContestDetail(contestsAll.get(position));
            }
        });

    }

    private void showSelectedContestDetail(Objects contestsAll) {
        //Log.i("UpcomingAllFragment", "on click contestsAll.getpos platform name is : " + contestsAll.getResource().getName());
        Intent intentContestDetail = new Intent(getActivity(), ContestDetail.class);
        Bundle extras = new Bundle();
        extras.putParcelable("EXTRA_CONTEST", contestsAll);
        extras.putParcelable("EXTRA_CONTEST_2", contestsAll.getResource());
        extras.putInt("EXTRA_INT",1);
        intentContestDetail.putExtras(extras);
        startActivity(intentContestDetail);
    }
}