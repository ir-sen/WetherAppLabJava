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

// массив городов у которых мы хотим знать погоду можно добавлять и удалять
    String[] city= {"Moscow", "Los Angeles", "Vienna", "Orlando", "Dublin", "Lisbon", "Tashkent", "Kiev",
    "Lagos", "Warsaw", "Madrid", "Dubai", "Cairo", "Paris", "Riga"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);


        RecyclerView recyclerView = findViewById(R.id.recycleView);
        getCurrentWeather(this, recyclerView);


    }


// функция запроса и вывода в recycle view
    void getCurrentWeather(Context context, RecyclerView recyclerView) {
        // проходимся по массиву городов
        for (int i = 0; i < city.length; i++) {
            // делаем запрос на сервер через ретрофит
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WetherService service = retrofit.create(WetherService.class);


            Call<WeatherResponse> call = service.getCurrentSityData(city[i], Constants.API_KEY);

            // пишем функцию callback
            int finalI = i;
            call.enqueue(new Callback<WeatherResponse>() {
                @Override
                public void onResponse(@NonNull Call<WeatherResponse> call, @NonNull Response<WeatherResponse> response) {
                    if (response.code() == 200) {
                        WeatherResponse weatherResponse = response.body();
                        assert weatherResponse != null;
                        // превращяем все в градусы
                        double gradus = weatherResponse.main.temp - 273.15;
                        citysTemp.add(new CitysTemp(city[finalI], "Градусов: " + gradus ));
                        Log.d("TAG", citysTemp.get(0).getCity());
                        // выводим в recycleview
                        recyclerView.setHasFixedSize(true);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        recyclerView.setLayoutManager(linearLayoutManager);
                        RecycleAdapter adapter = new RecycleAdapter(context, citysTemp);
                        recyclerView.setAdapter(adapter);

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