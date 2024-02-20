package com.khamlichi.myjavaweatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    EditText editCityName;
    Button btnSearch;
    ImageView imageWeather;
    TextView tvTemperature, tvCityName;

    LinearLayout layoutWeather;
    private Call<WeatherResult> result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editCityName = findViewById(R.id.editCity);
        btnSearch = findViewById(R.id.btnSearch);
        imageWeather = findViewById(R.id.imageWeather);
        tvCityName = findViewById(R.id.tvCityName);
        tvTemperature = findViewById(R.id.tvTemperature);
        layoutWeather = findViewById(R.id.layoutWeather);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city = editCityName.getText().toString();
                if (city.isEmpty()) {
                } else {
                    getWeatherByCity(city);
                }
            }
        });

    }
    void getWeatherByCity(String city) {
        // Create retrofit instance
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/weather/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WeatherService weatherService = retrofit.create(WeatherService.class);

        // Call Weather api
        result = weatherService.getWeatherByCity(city);

        // Appeler le résultat dans une pile
        result.enqueue(new Callback<WeatherResult>() {

            @Override
            public void onResponse(Call<WeatherResult> call, Response<WeatherResult> response) {
                if (response.isSuccessful()) {
                    WeatherResult result = response.body();

                    tvTemperature.setText(result.main.temp.toString() + " °C");
                    tvCityName.setText(result.name);
                    Picasso.get().load("https://openweathermap.org/img/w/" + result.weather.get(0).icon +".png").into(imageWeather);

                    layoutWeather.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<WeatherResult> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Erreur Serveur", Toast.LENGTH_SHORT).show();
            }
        });


    }
}