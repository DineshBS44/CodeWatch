package com.example.codewatch.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.codewatch.R;
import com.example.codewatch.fragment.about.AboutFragment;
import com.example.codewatch.fragment.ongoing.OngoingFragment;
import com.example.codewatch.fragment.upcoming.UpcomingFragment;
import com.example.codewatch.model.Contest;
import com.example.codewatch.model.Objects;
import com.example.codewatch.rest.ApiClient;
import com.example.codewatch.rest.ApiInterface;
import com.example.codewatch.utils.MyNotificationPublisher;
import com.example.codewatch.utils.OverlayFrame;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
    CoordinatorLayout rootView;

    public static final String MyPREFERENCES = "MyPrefs";
    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    private final static String default_notification_channel_id = "default" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progress_bar_overlay);
        rootView = findViewById(R.id.root_activty_main);
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

                if(contestsAll!=null || contestsAll.size()!=0)
                    setNotifications(contestsAll);

            }

            @Override
            public void onFailure(Call<Contest> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                Snackbar snackbar = Snackbar.make(rootView, R.string.no_internet, Snackbar.LENGTH_LONG);
                snackbar.setBackgroundTint(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
                snackbar.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
                View view = snackbar.getView();
                TextView tv = view.findViewById(com.google.android.material.R.id.snackbar_text);
                tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                tv.setTextSize(16);
                snackbar.show();
                //Toast.makeText(getApplicationContext(), "Check your internet connection or try again later", Toast.LENGTH_SHORT).show();
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

        fetchDataOngoing(contestsAll, contestsShort, contestsLong, currentDateandTime, dateAndTimeAfterOneWeek);

    }

    void fetchDataOngoing(ArrayList<Objects> contestsAll, ArrayList<Objects> contestsShort, ArrayList<Objects> contestsLong, String currentDateandTime, String dateAndTimeAfterOneWeek) {
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
                Snackbar snackbar = Snackbar.make(rootView, R.string.no_internet, Snackbar.LENGTH_LONG);
                snackbar.setBackgroundTint(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
                snackbar.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
                View view = snackbar.getView();
                TextView tv = view.findViewById(com.google.android.material.R.id.snackbar_text);
                tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                tv.setTextSize(16);
                snackbar.show();
                //Toast.makeText(getApplicationContext(), "Check your internet connection or try again later", Toast.LENGTH_SHORT).show();
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

        setNotifications(contestsAll);
    }

    void setNotifications(ArrayList<Objects> contestsAll)
    {
        long notiNumLong = 0;
        String key = "Key";
        SharedPreferences shref;
        SharedPreferences.Editor editor;
        shref = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        Gson gson = new Gson();
        String response=shref.getString(key , "");
        ArrayList<Objects> contests = gson.fromJson(response,
                new TypeToken<ArrayList<Objects>>(){}.getType());

        if(contestsAll!=null)
        if(contests!=contestsAll)
        {
            for(int i=0;i<contestsAll.size();i++)
            {
                Objects objects = contestsAll.get(i);
                int flag=0;
                if(contests!=null)
                for(int j=0;j<contests.size();j++)
                {
                    Objects objects1 = contests.get(j);
                    if(objects.getEvent().equals(objects1.getEvent()))
                    {
                        flag=1;
                        break;
                    }
                }
                if(flag==1)
                    continue;

                int mod = 1000000007;
                int notiNum = (int)notiNumLong%mod;

                scheduleNotification(getNotification(objects), notiNum);


            }
        }
    }


    /*public void scheduleNotification(Objects contests) {

        String notificationTitle = "";
        if(contests.getResource().getName().equals("codechef.com"))
        {
            notificationTitle="Codechef";
        }
        else if(contests.getResource().getName().equals("codeforces.com"))
        {
            notificationTitle="Codeforces";
        }
        else if(contests.getResource().getName().equals("topcoder.com"))
        {
            notificationTitle="Topcoder";
        }
        else if(contests.getResource().getName().equals("leetcode.com"))
        {
            notificationTitle="Leetcode";
        }
        else if(contests.getResource().getName().equals("hackerearth.com"))
        {
            notificationTitle="Hackerearth";
        }
        else if(contests.getResource().getName().equals("hackerrank.com"))
        {
            notificationTitle="Hackerrank";
        }
        else if(contests.getResource().getName().equals("atcoder.jp"))
        {
            notificationTitle="Atcoder";
        }
        else if(contests.getResource().getName().equals("codingcompetitions.withgoogle.com"))
        {
            notificationTitle="Kickstart";
        }

        Log.d("MainActivity","It is running : : ");
        *//*Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 22);
        calendar.set(Calendar.MINUTE, 49);
        calendar.set(Calendar.SECOND, 0);*//*
        Intent notifyIntent = new Intent(getApplicationContext(), ShowNotifications.class);
        notifyIntent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),0,notifyIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,  SystemClock. elapsedRealtime () + 10000, pendingIntent);




        *//*NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = "my_channel_id_01";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_HIGH);

            // Configure the notification channel.
            notificationChannel.setDescription("Channel description");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }


        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);

        notificationBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis()+10000000)
                .setSmallIcon(R.drawable.ic_code_watch_app_icon)
                .setTicker("Hearty365")
                .setContentTitle(notificationTitle)
                .setContentText("The contest will start in 1 hour")
                .setContentInfo("Info");

        notificationManager.notify(*//**//*notification id*//**//*1, notificationBuilder.build());*//*
    }*/







    private void scheduleNotification (Notification notification, int notiNum) {
        Intent notificationIntent = new Intent( this, MyNotificationPublisher. class ) ;
        notificationIntent.putExtra(MyNotificationPublisher. NOTIFICATION_ID , 1 ) ;
        notificationIntent.putExtra(MyNotificationPublisher. NOTIFICATION , notification) ;
        PendingIntent pendingIntent = PendingIntent. getBroadcast ( this, notiNum , notificationIntent , PendingIntent. FLAG_UPDATE_CURRENT ) ;
        long futureInMillis = SystemClock. elapsedRealtime ();
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context. ALARM_SERVICE ) ;
        assert alarmManager != null;
        alarmManager.set(AlarmManager. ELAPSED_REALTIME_WAKEUP , futureInMillis + 10000 , pendingIntent) ;
    }

    private Notification getNotification (Objects contests) {

        String notificationTitle = "";
        if(contests.getResource().getName().equals("codechef.com"))
        {
            notificationTitle="Codechef";
        }
        else if(contests.getResource().getName().equals("codeforces.com"))
        {
            notificationTitle="Codeforces";
        }
        else if(contests.getResource().getName().equals("topcoder.com"))
        {
            notificationTitle="Topcoder";
        }
        else if(contests.getResource().getName().equals("leetcode.com"))
        {
            notificationTitle="Leetcode";
        }
        else if(contests.getResource().getName().equals("hackerearth.com"))
        {
            notificationTitle="Hackerearth";
        }
        else if(contests.getResource().getName().equals("hackerrank.com"))
        {
            notificationTitle="Hackerrank";
        }
        else if(contests.getResource().getName().equals("atcoder.jp"))
        {
            notificationTitle="Atcoder";
        }
        else if(contests.getResource().getName().equals("codingcompetitions.withgoogle.com"))
        {
            notificationTitle="Kickstart";
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder( this, default_notification_channel_id ) ;
        builder.setContentTitle( notificationTitle ) ;
        builder.setContentText(contests.getEvent()) ;
        builder.setSmallIcon(R.drawable. ic_code_watch_app_icon ) ;
        builder.setAutoCancel( true ) ;
        builder.setChannelId( NOTIFICATION_CHANNEL_ID ) ;
        return builder.build() ;
    }

}
