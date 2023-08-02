package com.omnify.weatherApp.service;

import com.omnify.weatherApp.model.WeatherDetail;

import java.util.List;

public interface WeatherService {

    List<String> search(String key);

    WeatherDetail getWeatherInfo(String city);
}
