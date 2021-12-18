package com.example.wetherapplabjava;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

// формируем get запросы для Retrofit
public interface WetherService {
    @GET("/data/2.5/weather?")
    Call<WeatherResponse> getCurrentWetherData(@Query("lat") String lat, @Query("lon") String lon, @Query("APPID") String app_id);

    @GET("/data/2.5/weather?")
    Call<WeatherResponse> getCurrentSityData(@Query("q") String sity, @Query("appid") String app_id);

    //api.openweathermap.org/data/2.5/box/city?bbox=12,32,15,37,10&appid=156f1434f604e39edc7f4cab0e4414ce
    @GET("/data/2.5/box/city?")
    Call<List> getCityList(@Query("bbox") String number, @Query("appid") String app_id);

}
