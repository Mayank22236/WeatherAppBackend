package com.omnify.weatherApp.dao;

import com.omnify.weatherApp.cilent.WeatherClient;
import com.omnify.weatherApp.model.WeatherDetail;

import java.util.List;

public interface WeatherDao {

    List<String> search(String key);
    WeatherDetail getWeatherInfo(String city);
    void insertWeatherDetails(WeatherDetail weatherDetail);
}
