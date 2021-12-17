package com.example.wetherapplabjava;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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


    String[] city= {"Moscow", "Los Angeles", "Vienna", "Orlando", "Dublin", "Lisbon", "Tashkent", "Kiev",
    "Lagos", "Warsaw", "Madrid", "Dubai", "Cairo", "Paris", "Riga"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);


        //citysTemp.add(new CitysTemp("Moscow", "100"));
        RecyclerView recyclerView = findViewById(R.id.recycleView);
        getCurrentWeather(this, recyclerView);
//        recyclerView.setHasFixedSize(true);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        recyclerView.setLayoutManager(linearLayoutManager);
//        RecycleAdapter adapter = new RecycleAdapter(this, citysTemp);
//        recyclerView.setAdapter(adapter);

    }



    void getCurrentWeather(Context context, RecyclerView recyclerView) {
        for (int i = 0; i < city.length; i++) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WetherService service = retrofit.create(WetherService.class);


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
                        double gradus = weatherResponse.main.temp - 273.15;
                        citysTemp.add(new CitysTemp(city[finalI], "Градусов: " + gradus ));
                        Log.d("TAG", citysTemp.get(0).getCity());

                        recyclerView.setHasFixedSize(true);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        recyclerView.setLayoutManager(linearLayoutManager);
                        RecycleAdapter adapter = new RecycleAdapter(context, citysTemp);
                        recyclerView.setAdapter(adapter);
                        //Log.d("TAG", city[finalI] + " " + weatherResponse.main.temp);


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