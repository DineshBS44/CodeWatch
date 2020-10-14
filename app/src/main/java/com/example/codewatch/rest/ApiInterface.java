package com.example.codewatch.rest;

import com.example.codewatch.model.Contest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("contest/")
    Call<Contest> getUpcomingShortContest(@Query("format") String format, @Query("order_by") String orderBy, @Query("start_gt") String startGt, @Query("start_lte") String startLte, @Query("duration_lte") Integer duration, @Query("username") String userName, @Query("api_key") String api_key);
    @GET("contest/")
    Call<Contest> getUpcomingLongContest(@Query("format") String format, @Query("order_by") String orderBy, @Query("start_gt") String startGt, @Query("start_lte") String startLte, @Query("duration_gt") Integer duration, @Query("username") String userName, @Query("api_key") String api_key);
    @GET("contest/")
    Call<Contest> getUpcomingAllContest(@Query("format") String format, @Query("order_by") String orderBy, @Query("start_gt") String startGt, @Query("start_lte") String startLte, @Query("username") String userName, @Query("api_key") String api_key);

    @GET("contest/")
    Call<Contest> getOngoingShortContest(@Query("format") String format, @Query("order_by") String orderBy, @Query("start_lte") String startLte, @Query("end_gte") String endGte, @Query("duration_lte") Integer duration, @Query("username") String userName, @Query("api_key") String api_key);
    @GET("contest/")
    Call<Contest> getOngoingLongContest(@Query("format") String format, @Query("order_by") String orderBy, @Query("start_lte") String startLte, @Query("end_gte") String endGte, @Query("duration_gt") Integer duration, @Query("username") String userName, @Query("api_key") String api_key);
    @GET("contest/")
    Call<Contest> getOngoingAllContest(@Query("format") String format, @Query("order_by") String orderBy, @Query("start_lte") String startLte, @Query("end_gte") String endGte, @Query("username") String userName, @Query("api_key") String api_key);


}
