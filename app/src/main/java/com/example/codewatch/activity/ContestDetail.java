package com.example.codewatch.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.codewatch.R;
import com.example.codewatch.adapter.upcoming.UpcomingAllAdapter;
import com.example.codewatch.model.Objects;
import com.example.codewatch.model.Resource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class ContestDetail extends AppCompatActivity {

    TextView platformName, contestName, contestTime, contestDate, contestNotified, contestLink;
    ImageView platformImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contest_detail);

        platformImage = findViewById(R.id.contest_detail_platform_image);
        platformName = findViewById(R.id.contest_detail_platform);
        contestTime = findViewById(R.id.contest_detail_timings);
        contestName = findViewById(R.id.contest_detail_contest_name);
        contestDate = findViewById(R.id.contest_detail_date);
        contestNotified = findViewById(R.id.contest_detail_notified_tv);
        contestLink=findViewById(R.id.contest_detail_link);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        Objects contests = extras.getParcelable("EXTRA_CONTEST");
        Resource resource = extras.getParcelable("EXTRA_CONTEST_2");
        //final Objects contests = getIntent().getParcelableExtra("EXTRA_CONTEST");
        //Log.i("ContestDetail", "contests.size event name inside ContestDetail is : " + contests.getEvent());

        if (resource.getName().equals("atcoder.jp")) {
            platformImage.setImageResource(R.drawable.ic_atcoder);
            platformName.setText("ATCODER");
            contestName.setText(contests.getEvent());
            setDateAndTime(contests);
        } else if (resource.getName().equals("codechef.com")) {
            platformImage.setImageResource(R.drawable.ic_codechef);
            platformName.setText("CODECHEF");
            contestName.setText(contests.getEvent());
            setDateAndTime(contests);
        } else if (resource.getName().equals("codeforces.com")) {
            platformImage.setImageResource(R.drawable.ic_codeforces);
            platformName.setText("CODEFORCES");
            contestName.setText(contests.getEvent());
            setDateAndTime(contests);
        } else if (resource.getName().equals("codingcompetitions.withgoogle.com")) {
            platformImage.setImageResource(R.drawable.ic_google);
            platformName.setText("GOOGLE");
            contestName.setText(contests.getEvent());
            setDateAndTime(contests);
        } else if (resource.getName().equals("hackerearth.com")) {
            platformImage.setImageResource(R.drawable.ic_hackerearth);
            platformName.setText("HACKEREARTH");
            contestName.setText(contests.getEvent());
            setDateAndTime(contests);
        } else if (resource.getName().equals("hackerrank.com")) {
            platformImage.setImageResource(R.drawable.ic_hackerrank);
            platformName.setText("HACKERRANK");
            contestName.setText(contests.getEvent());
            setDateAndTime(contests);
        } else if (resource.getName().equals("leetcode.com")) {
            platformImage.setImageResource(R.drawable.ic_leetcode);
            platformName.setText("LEETCODE");
            contestName.setText(contests.getEvent());
            setDateAndTime(contests);
        } else if (resource.getName().equals("topcoder.com")) {
            platformImage.setImageResource(R.drawable.ic_topcoder);
            platformName.setText("TOPCODER");
            contestName.setText(contests.getEvent());
            setDateAndTime(contests);
        }

        contestLink.setText(Html.fromHtml("<a href=\""+ contests.getHref() + "\">" + "Click here" + "</a>"));
        contestLink.setClickable(true);
        contestLink.setMovementMethod (LinkMovementMethod.getInstance());

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.HOUR, cal.get(Calendar.HOUR) + 1);

        Date timeAfterOneHour = cal.getTime();
        String startDateTime = contests.getStart();
        startDateTime = startDateTime.replace("T", ",");
        SimpleDateFormat dfStart = new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss", Locale.ENGLISH);
        dfStart.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date dateStart = null;
        try {
            dateStart = dfStart.parse(startDateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dfStart.setTimeZone(TimeZone.getDefault());
        String startTimeDate = dfStart.format(dateStart);
        Date startTime = null;
        try {
            startTime = dfStart.parse(startTimeDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (startTime.before(timeAfterOneHour)) {
            contestNotified.setText(R.string.contest_starts_soon);
        }
    }

    public void setDateAndTime(Objects contests) {
        String startDateTime = contests.getStart();
        String endDateTime = contests.getEnd();
        startDateTime = startDateTime.replace("T", ",");
        endDateTime = endDateTime.replace("T", ",");
        SimpleDateFormat dfStart = new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss", Locale.ENGLISH);
        SimpleDateFormat dfStart2 = new SimpleDateFormat("dd/MM/yy,HH:mm", Locale.ENGLISH);
        dfStart.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date dateStart = null;
        try {
            dateStart = dfStart.parse(startDateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dfStart2.setTimeZone(TimeZone.getDefault());
        String formattedStartDateTime = dfStart2.format(dateStart);

        SimpleDateFormat dfEnd = new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss", Locale.ENGLISH);
        SimpleDateFormat dfEnd2 = new SimpleDateFormat("dd/MM/yy,HH:mm", Locale.ENGLISH);
        dfEnd.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date dateEnd = null;
        try {
            dateEnd = dfEnd.parse(endDateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dfEnd2.setTimeZone(TimeZone.getDefault());
        String formattedEndDateTime = dfEnd2.format(dateEnd);

        String[] start = formattedStartDateTime.split(",");
        String startDate = start[0];
        String startTime = start[1];
        String[] end = formattedEndDateTime.split(",");
        String endDate = end[0];
        String endTime = end[1];

        String startAndEndDate = startDate + " - " + endDate;
        String startAndEndTime = startTime + " - " + endTime;
        contestDate.setText(startAndEndDate);
        contestTime.setText(startAndEndTime);
    }

}