package com.khamlichi.myjavaweatherapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {
    final String API_KEY = "9f087e8e9e8122b915066d267667cd01";

    @GET("?units=metric&appid=" + API_KEY)
    public Call<WeatherResult> getWeatherByCity(@Query("q") String city);
}
