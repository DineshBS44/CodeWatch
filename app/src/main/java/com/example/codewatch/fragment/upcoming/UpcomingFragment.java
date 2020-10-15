package com.example.codewatch.fragment.upcoming;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.codewatch.R;
import com.example.codewatch.adapter.upcoming.UpcomingAdapter;
import com.example.codewatch.model.Contest;
import com.example.codewatch.model.Objects;
import com.example.codewatch.rest.ApiClient;
import com.example.codewatch.rest.ApiInterface;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpcomingFragment extends Fragment {

    private static final String TAG = "UpcomingFragment";
    private final static String API_KEY = "c456cba4bdd1f92b7c221887d5b37e0a233cbefe";
    private final static String USER_NAME = "dinesh_b_s";
    private final static String format = "json";
    private final static String orderBy = "start";
    private final static Integer duration = 21600;
    /*final ArrayList<Objects> contestsAll=new ArrayList<>();
    final ArrayList<Objects> contestsShort=new ArrayList<>();
    final ArrayList<Objects> contestsLong=new ArrayList<>();*/
    ViewPager2 viewPager;
    TextView allTextView, shortTextView, longTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_upcoming, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPager = view.findViewById(R.id.upcoming_viewpager);
        allTextView = view.findViewById(R.id.upcoming_all_tv);
        shortTextView = view.findViewById(R.id.upcoming_short_tv);
        longTextView = view.findViewById(R.id.upcoming_long_tv);
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

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());

        Date currentDate = cal.getTime();

        cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) + 7);

        Date dateAfterOneWeek = cal.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss", Locale.getDefault());
        String currentDateTime = sdf.format(currentDate);
        final String currentDateandTime = currentDateTime.replace(",", "T");
        String dateTimeAfterOneWeek = sdf.format(dateAfterOneWeek);
        final String dateAndTimeAfterOneWeek = dateTimeAfterOneWeek.replace(",", "T");


        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Contest> call = apiService.getUpcomingAllContest(format, orderBy, currentDateandTime, dateAndTimeAfterOneWeek, USER_NAME, API_KEY);
        call.enqueue(new Callback<Contest>() {
            @Override
            public void onResponse(Call<Contest> call, Response<Contest> response) {
                int statusCode = response.code();
                ArrayList<Objects> contestsAll = java.util.Objects.requireNonNull(response).body().getObjects();
                fetchDataShort(contestsAll, currentDateandTime, dateAndTimeAfterOneWeek);
                //viewPager.setAdapter(new UpcomingAdapter(UpcomingFragment.this,contests,contests,contests));
                //Log.i(TAG,"The size of contestsAll is "+contestsAll.size());
            }

            @Override
            public void onFailure(Call<Contest> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });

    }

    private void fetchDataShort(ArrayList<Objects> contestsAll, String currentDateandTime, String dateAndTimeAfterOneWeek) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Contest> call = apiService.getUpcomingShortContest(format, orderBy, currentDateandTime, dateAndTimeAfterOneWeek, duration, USER_NAME, API_KEY);
        call.enqueue(new Callback<Contest>() {
            @Override
            public void onResponse(Call<Contest> call, Response<Contest> response) {
                int statusCode = response.code();
                ArrayList<Objects> contestsShort = java.util.Objects.requireNonNull(response).body().getObjects();
                fetchDataLong(contestsAll, contestsShort, currentDateandTime, dateAndTimeAfterOneWeek);
                //Log.i(TAG,"The size of contestsShort is "+contestsShort.size());
            }

            @Override
            public void onFailure(Call<Contest> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }

    private void fetchDataLong(ArrayList<Objects> contestsAll, ArrayList<Objects> contestsShort, String currentDateandTime, String dateAndTimeAfterOneWeek) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Contest> call = apiService.getUpcomingLongContest(format, orderBy, currentDateandTime, dateAndTimeAfterOneWeek, duration, USER_NAME, API_KEY);
        call.enqueue(new Callback<Contest>() {
            @Override
            public void onResponse(Call<Contest> call, Response<Contest> response) {
                int statusCode = response.code();
                ArrayList<Objects> contestsLong = java.util.Objects.requireNonNull(response).body().getObjects();

                for (int j = contestsAll.size() - 1; j >= 0; j--) {
                    Objects objects = contestsAll.get(j);
                    if ((!objects.getResource().getName().equals("atcoder.jp")) && (!objects.getResource().getName().equals("codechef.com")) && (!objects.getResource().getName().equals("codeforces.com")) && (!objects.getResource().getName().equals("codingcompetitions.withgoogle.com")) && (!objects.getResource().getName().equals("hackerearth.com")) && (!objects.getResource().getName().equals("hackerrank.com")) && (!objects.getResource().getName().equals("leetcode.com")) && (!objects.getResource().getName().equals("topcoder.com"))) {
                        contestsAll.remove(objects);
                    }
                }
                for (int j = contestsShort.size() - 1; j >= 0; j--) {
                    Objects objects = contestsShort.get(j);
                    if ((!objects.getResource().getName().equals("atcoder.jp")) && (!objects.getResource().getName().equals("codechef.com")) && (!objects.getResource().getName().equals("codeforces.com")) && (!objects.getResource().getName().equals("codingcompetitions.withgoogle.com")) && (!objects.getResource().getName().equals("hackerearth.com")) && (!objects.getResource().getName().equals("hackerrank.com")) && (!objects.getResource().getName().equals("leetcode.com")) && (!objects.getResource().getName().equals("topcoder.com"))) {
                        contestsShort.remove(objects);
                    }
                }
                for (int j = contestsLong.size() - 1; j >= 0; j--) {
                    Objects objects = contestsLong.get(j);
                    if ((!objects.getResource().getName().equals("atcoder.jp")) && (!objects.getResource().getName().equals("codechef.com")) && (!objects.getResource().getName().equals("codeforces.com")) && (!objects.getResource().getName().equals("codingcompetitions.withgoogle.com")) && (!objects.getResource().getName().equals("hackerearth.com")) && (!objects.getResource().getName().equals("hackerrank.com")) && (!objects.getResource().getName().equals("leetcode.com")) && (!objects.getResource().getName().equals("topcoder.com"))) {
                        contestsLong.remove(objects);
                    }
                }
                Log.i(TAG, "contestsAll : " + contestsAll.size());
                Log.i(TAG, "contestsShort : " + contestsShort.size());
                Log.i(TAG, "contestsLong : " + contestsLong.size());

                Log.i(TAG, "The size of contestsLong after all changes is " + contestsLong.size());

                viewPager.setAdapter(new UpcomingAdapter(UpcomingFragment.this, contestsAll, contestsShort, contestsLong));
            }

            @Override
            public void onFailure(Call<Contest> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }

}