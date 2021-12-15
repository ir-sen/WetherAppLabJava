package com.example.wetherapplabjava;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WetherService {
    @GET("/data/2.5/weather?")
    Call<WeatherResponse> getCurrentWetherData(@Query("lat") String lat, @Query("lon") String lon, @Query("APPID") String app_id);

    @GET("/data/2.5/weather?")
    Call<WeatherResponse> getCurrentSityData(@Query("q") String sity, @Query("appid") String app_id);

}
