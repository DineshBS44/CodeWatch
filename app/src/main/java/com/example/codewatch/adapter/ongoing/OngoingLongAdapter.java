package com.example.codewatch.adapter.ongoing;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codewatch.R;
import com.example.codewatch.model.Objects;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class OngoingLongAdapter extends RecyclerView.Adapter<OngoingLongAdapter.ViewHolder> {

    private ArrayList<Objects> contests;

    public OngoingLongAdapter(ArrayList<Objects> contests) {
        this.contests = contests;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_contest_rv, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(contests.get(position).getResource().getName().equals("atcoder.com"))
        {
            holder.platformImage.setImageResource(R.drawable.ic_atcoder);
            holder.platform.setText("ATCODER");
            holder.contestName.setText(contests.get(position).getEvent());
            setDateAndTime(holder,position);
        }
        else if(contests.get(position).getResource().getName().equals("codechef.com"))
        {
            holder.platformImage.setImageResource(R.drawable.ic_codechef);
            holder.platform.setText("CODECHEF");
            holder.contestName.setText(contests.get(position).getEvent());
            setDateAndTime(holder,position);
        }
        else if(contests.get(position).getResource().getName().equals("codeforces.com"))
        {
            holder.platformImage.setImageResource(R.drawable.ic_codeforces);
            holder.platform.setText("CODEFORCES");
            holder.contestName.setText(contests.get(position).getEvent());
            setDateAndTime(holder,position);
        }
        else if(contests.get(position).getResource().getName().equals("codingcompetitions.withgoogle.com"))
        {
            holder.platformImage.setImageResource(R.drawable.ic_google);
            holder.platform.setText("GOOGLE");
            holder.contestName.setText(contests.get(position).getEvent());
            setDateAndTime(holder,position);
        }
        else if(contests.get(position).getResource().getName().equals("hackerearth.com"))
        {
            holder.platformImage.setImageResource(R.drawable.ic_hackerearth);
            holder.platform.setText("HACKEREARTH");
            holder.contestName.setText(contests.get(position).getEvent());
            setDateAndTime(holder,position);
        }
        else if(contests.get(position).getResource().getName().equals("hackerrank.com"))
        {
            holder.platformImage.setImageResource(R.drawable.ic_hackerrank);
            holder.platform.setText("HACKERRANK");
            holder.contestName.setText(contests.get(position).getEvent());
            setDateAndTime(holder,position);
        }
        else if(contests.get(position).getResource().getName().equals("leetcode.com"))
        {
            holder.platformImage.setImageResource(R.drawable.ic_leetcode);
            holder.platform.setText("LEETCODE");
            holder.contestName.setText(contests.get(position).getEvent());
            setDateAndTime(holder,position);
        }
        else if(contests.get(position).getResource().getName().equals("topcoder.com"))
        {
            holder.platformImage.setImageResource(R.drawable.ic_topcoder);
            holder.platform.setText("TOPCODER");
            holder.contestName.setText(contests.get(position).getEvent());
            setDateAndTime(holder,position);
        }
        else
        {
            holder.rootView.setLayoutParams(holder.params);
        }
    }

    @Override
    public int getItemCount() {
        return contests.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ConstraintLayout.LayoutParams params;
        public ConstraintLayout rootView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            params = new ConstraintLayout.LayoutParams(0, 0);
            rootView=itemView.findViewById(R.id.rootView);
        }

        TextView platform=itemView.findViewById(R.id.contest_platform);
        TextView contestName=itemView.findViewById(R.id.contest_name);
        ImageView platformImage=itemView.findViewById(R.id.contest_image);
        TextView time=itemView.findViewById(R.id.contest_time);
        TextView date=itemView.findViewById(R.id.contest_date);

    }

    public void setDateAndTime(@NonNull ViewHolder holder, int position)
    {
        String startDateTime=contests.get(position).getStart();
        String endDateTime=contests.get(position).getEnd();

        SimpleDateFormat dfStart = new SimpleDateFormat("yyyy-MM-ddTHH:mm:ss", Locale.ENGLISH);
        SimpleDateFormat dfStart2=new SimpleDateFormat("dd/MM/yyyyTHH:mm",Locale.ENGLISH);
        dfStart.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date dateStart = null;
        try {
            dateStart = dfStart.parse(startDateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dfStart2.setTimeZone(TimeZone.getDefault());
        String formattedStartDateTime = dfStart2.format(dateStart);


        SimpleDateFormat dfEnd = new SimpleDateFormat("yyyy-MM-ddTHH:mm:ss", Locale.ENGLISH);
        SimpleDateFormat dfEnd2=new SimpleDateFormat("dd/MM/yyyyTHH:mm",Locale.ENGLISH);
        dfEnd.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date dateEnd = null;
        try {
            dateEnd = dfEnd.parse(endDateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dfEnd2.setTimeZone(TimeZone.getDefault());
        String formattedEndDateTime = dfEnd2.format(dateEnd);

        String[] start = formattedStartDateTime.split("T");
        String startDate = start[0];
        String startTime=start[1];
        String[] end = formattedEndDateTime.split("T");
        String endDate = end[0];
        String endTime=end[1];

        String startAndEndDate=startDate+" - " + endDate;
        String startAndEndTime=startTime+" - " + endTime;
        holder.date.setText(startAndEndDate);
        holder.time.setText(startAndEndTime);
    }

}
