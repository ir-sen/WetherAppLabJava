package com.example.wetherapplabjava;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SecondActivity extends AppCompatActivity {

    private FusedLocationProviderClient mFusedLocationClient;
    public static String BaseUrl = "http://api.openweathermap.org/";

    ArrayList<CitysTemp> citysTemp = new ArrayList<>();

    int PERMISSION_ID = 44;
    String latitude;
    String longitude;

    TextView textTest;

    TextView textV2;

    String[] city= {"Moscow", "Los Angeles", "Vienna", "Orlando", "Dublin", "Lisbon"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);


        //textTest.setText(message);
        getCurrentWeather();



    }



    void getCurrentWeather() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WetherService service = retrofit.create(WetherService.class);
        for (int i = 0; i < city.length; i++) {

            Call<WeatherResponse> call = service.getCurrentSityData(city[i], Constants.API_KEY);

            //Call<WeatherResponse> call = service.getCurrentWetherData(lat, lon, Constants.API_KEY);
            //Call<List> call = service.getCityList("12,32,15,37,10", Constants.API_KEY);
            int finalI = i;
            call.enqueue(new Callback<WeatherResponse>() {
                @Override
                public void onResponse(@NonNull Call<WeatherResponse> call, @NonNull Response<WeatherResponse> response) {
                    if (response.code() == 200) {
                        WeatherResponse weatherResponse = response.body();
                        assert weatherResponse != null;
                       // citysTemp.put(city[finalI], weatherResponse.main.temp + "");
                        String stringBuilder = "Country: " +
                                weatherResponse.sys.country +
                                "\n" +
                                "Temperature: " +
                                weatherResponse.main.temp +
                                "\n" +
                                "Temperature(Min): " +
                                weatherResponse.main.temp_min +
                                "\n" +
                                "Temperature(Max): " +
                                weatherResponse.main.temp_max +
                                "\n" +
                                weatherResponse.weather +
                                "\n" +
                                city[finalI];

                        Log.d("TAG", city[finalI]);


                        //textTest.setText(stringBuilder);
                    }
                }

                @Override
                public void onFailure(Call<WeatherResponse> call, Throwable t) {
                    //textTest.setText(t.getMessage());
                }

            });
        }

    }





}