package com.example.codewatch.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.codewatch.R;
import com.example.codewatch.adapter.upcoming.UpcomingAdapter;
import com.example.codewatch.fragment.AboutFragment;
import com.example.codewatch.fragment.ongoing.OngoingFragment;
import com.example.codewatch.fragment.upcoming.UpcomingFragment;
import com.example.codewatch.model.Contest;
import com.example.codewatch.model.Objects;
import com.example.codewatch.rest.ApiClient;
import com.example.codewatch.rest.ApiInterface;
import com.example.codewatch.utils.OverlayFrame;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "MainActivity";
    private final static String API_KEY = "c456cba4bdd1f92b7c221887d5b37e0a233cbefe";
    private final static String USER_NAME = "dinesh_b_s";
    private final static String format = "json";
    private final static String orderBy = "start";
    private final static Integer duration = 21600;
    OverlayFrame overlayFrame;
    NavController navController;
    BottomNavigationView bottomNavigationView;
    ProgressBar progressBar;
    ImageView overlayImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progress_bar_overlay);
        overlayImage = findViewById(R.id.overlay_frame_image);
        overlayFrame = findViewById(R.id.overlay_frame);
        overlayFrame.displayOverlay(true);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_container);
        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
        }
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        fetchDataUpcoming();
    }

    @Override
    public void onBackPressed() {
        finish();
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    private void fetchDataUpcoming() {

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());

        Date currentDate = cal.getTime();

        cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) + 7);

        Date dateAfterOneWeek = cal.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss", Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("utc"));
        String currentDateTime = sdf.format(currentDate);
        final String currentDateandTime = currentDateTime.replace(",", "T");
        String dateTimeAfterOneWeek = sdf.format(dateAfterOneWeek);
        final String dateAndTimeAfterOneWeek = dateTimeAfterOneWeek.replace(",", "T");

        //Log.i("MainActivity", "current date and time is : " + currentDateandTime);
        //Log.i("MainActivity", "next week date and time is : " + dateAndTimeAfterOneWeek);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Contest> call = apiService.getUpcomingAllContest(format, orderBy, currentDateandTime, dateAndTimeAfterOneWeek, USER_NAME, API_KEY);
        call.enqueue(new Callback<Contest>() {
            @Override
            public void onResponse(Call<Contest> call, Response<Contest> response) {
                ArrayList<Objects> contestsAll = new ArrayList<>();
                ArrayList<Objects> contestsShort = new ArrayList<>();
                ArrayList<Objects> contestsLong = new ArrayList<>();
                if (response.body() != null) {
                    int statusCode = response.code();
                    contestsAll = response.body().getObjects();
                } else {
                    contestsAll = null;
                    contestsShort = null;
                    contestsLong = null;
                }

                sendDataUpcoming(contestsAll, contestsShort, contestsLong, currentDateandTime, dateAndTimeAfterOneWeek);
                //Log.i(TAG,"The size of contestsAll is "+contestsAll.size());

            }

            @Override
            public void onFailure(Call<Contest> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                Toast.makeText(getApplicationContext(), "Check your internet connection or try again later", Toast.LENGTH_SHORT).show();
            }
        });

    }

    void sendDataUpcoming(ArrayList<Objects> contestsAll, ArrayList<Objects> contestsShort, ArrayList<Objects> contestsLong, String currentDateandTime, String dateAndTimeAfterOneWeek) {
        if (contestsAll != null)
            for (int j = contestsAll.size() - 1; j >= 0; j--) {
                Objects objects = contestsAll.get(j);
                if ((!objects.getResource().getName().equals("atcoder.jp")) && (!objects.getResource().getName().equals("codechef.com")) && (!objects.getResource().getName().equals("codeforces.com")) && (!objects.getResource().getName().equals("codingcompetitions.withgoogle.com")) && (!objects.getResource().getName().equals("hackerearth.com")) && (!objects.getResource().getName().equals("hackerrank.com")) && (!objects.getResource().getName().equals("leetcode.com")) && (!objects.getResource().getName().equals("topcoder.com"))) {
                    contestsAll.remove(objects);
                }
            }

        if (contestsAll != null)
            for (int j = 0; j < contestsAll.size(); j++) {
                Objects objects = contestsAll.get(j);
                if (objects.getDuration() <= duration) {
                    contestsShort.add(objects);
                } else
                    contestsLong.add(objects);
            }

        /*bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_upcoming:
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        UpcomingFragment upcomingFragment = UpcomingFragment.newInstance(contestsAll, contestsShort, contestsLong);
                        ft.replace(R.id.nav_host_fragment_container, upcomingFragment);
                        ft.commit();
                        break;
                    case R.id.nav_ongoing:
                        FragmentManager manager = getSupportFragmentManager();
                        manager.beginTransaction().replace(R.id.nav_host_fragment_container, new OngoingFragment()).commit();
                        break;
                    case R.id.nav_about:
                        FragmentManager manager2 = getSupportFragmentManager();
                        manager2.beginTransaction().replace(R.id.nav_host_fragment_container, new AboutFragment()).commit();
                        break;
                }
                return true;
            }
        });

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if (destination.getId() == R.id.nav_upcoming) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    UpcomingFragment upcomingFragment = UpcomingFragment.newInstance(contestsAll, contestsShort, contestsLong);
                    ft.replace(R.id.nav_host_fragment_container, upcomingFragment);
                    ft.commit();
                } else if (destination.getId() == R.id.nav_ongoing) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    OngoingFragment ongoingFragment = new OngoingFragment();
                    ft.replace(R.id.nav_host_fragment_container, ongoingFragment);
                    ft.commit();
                } else {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    AboutFragment aboutFragment = new AboutFragment();
                    ft.replace(R.id.nav_host_fragment_container, aboutFragment);
                    ft.commit();
                }
            }
        });

        overlayFrame.displayOverlay(false);*/

        fetchDataOngoing(contestsAll,contestsShort,contestsLong,currentDateandTime,dateAndTimeAfterOneWeek);

    }

    void fetchDataOngoing(ArrayList<Objects> contestsAll, ArrayList<Objects> contestsShort, ArrayList<Objects> contestsLong, String currentDateandTime, String dateAndTimeAfterOneWeek)
    {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Contest> call = apiService.getOngoingAllContest(format, orderBy, currentDateandTime, currentDateandTime, USER_NAME, API_KEY);
        call.enqueue(new Callback<Contest>() {
            @Override
            public void onResponse(Call<Contest> call, Response<Contest> response) {
                ArrayList<Objects> contestsAllOngoing = new ArrayList<>();
                ArrayList<Objects> contestsShortOngoing = new ArrayList<>();
                ArrayList<Objects> contestsLongOngoing = new ArrayList<>();
                if (response.body() != null) {
                    int statusCode = response.code();
                    contestsAllOngoing = response.body().getObjects();
                } else {
                    contestsAllOngoing = null;
                    contestsShortOngoing = null;
                    contestsLongOngoing = null;
                }

                sendDataOngoing(contestsAll, contestsShort, contestsLong, contestsAllOngoing, contestsShortOngoing, contestsLongOngoing, currentDateandTime, dateAndTimeAfterOneWeek);
                //Log.i(TAG,"The size of contestsAll is "+contestsAll.size());

            }

            @Override
            public void onFailure(Call<Contest> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                Toast.makeText(getApplicationContext(), "Check your internet connection or try again later", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void sendDataOngoing(ArrayList<Objects> contestsAll, ArrayList<Objects> contestsShort, ArrayList<Objects> contestsLong, ArrayList<Objects> contestsAllOngoing, ArrayList<Objects> contestsShortOngoing, ArrayList<Objects> contestsLongOngoing, String currentDateandTime, String dateAndTimeAfterOneWeek) {
        if (contestsAllOngoing != null)
            for (int j = contestsAllOngoing.size() - 1; j >= 0; j--) {
                Objects objects = contestsAllOngoing.get(j);
                if ((!objects.getResource().getName().equals("atcoder.jp")) && (!objects.getResource().getName().equals("codechef.com")) && (!objects.getResource().getName().equals("codeforces.com")) && (!objects.getResource().getName().equals("codingcompetitions.withgoogle.com")) && (!objects.getResource().getName().equals("hackerearth.com")) && (!objects.getResource().getName().equals("hackerrank.com")) && (!objects.getResource().getName().equals("leetcode.com")) && (!objects.getResource().getName().equals("topcoder.com"))) {
                    contestsAllOngoing.remove(objects);
                }
            }

        if (contestsAllOngoing != null)
            for (int j = 0; j < contestsAllOngoing.size(); j++) {
                Objects objects = contestsAllOngoing.get(j);
                if (objects.getDuration() <= duration) {
                    contestsShortOngoing.add(objects);
                } else
                    contestsLongOngoing.add(objects);
            }

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_upcoming:
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        UpcomingFragment upcomingFragment = UpcomingFragment.newInstance(contestsAll, contestsShort, contestsLong);
                        ft.replace(R.id.nav_host_fragment_container, upcomingFragment);
                        ft.commit();
                        break;
                    case R.id.nav_ongoing:
                        FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
                        OngoingFragment ongoingFragment = OngoingFragment.newInstance(contestsAllOngoing, contestsShortOngoing, contestsLongOngoing);
                        ft2.replace(R.id.nav_host_fragment_container, ongoingFragment);
                        ft2.commit();
                        break;
                    case R.id.nav_about:
                        FragmentManager manager = getSupportFragmentManager();
                        manager.beginTransaction().replace(R.id.nav_host_fragment_container, new AboutFragment()).commit();
                        break;
                }
                return true;
            }
        });

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if (destination.getId() == R.id.nav_upcoming) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    UpcomingFragment upcomingFragment = UpcomingFragment.newInstance(contestsAll, contestsShort, contestsLong);
                    ft.replace(R.id.nav_host_fragment_container, upcomingFragment);
                    ft.commit();
                } else if (destination.getId() == R.id.nav_ongoing) {
                    FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
                    OngoingFragment ongoingFragment = OngoingFragment.newInstance(contestsAllOngoing, contestsShortOngoing, contestsLongOngoing);
                    ft2.replace(R.id.nav_host_fragment_container, ongoingFragment);
                    ft2.commit();
                } else {
                    FragmentTransaction ft3 = getSupportFragmentManager().beginTransaction();
                    AboutFragment aboutFragment = new AboutFragment();
                    ft3.replace(R.id.nav_host_fragment_container, aboutFragment);
                    ft3.commit();
                }
            }
        });

        overlayFrame.displayOverlay(false);
    }

}
