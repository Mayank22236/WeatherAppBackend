package com.omnify.weatherApp.service;

import com.omnify.weatherApp.cilent.WeatherClient;
import com.omnify.weatherApp.dao.WeatherDao;
import com.omnify.weatherApp.model.WeatherDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WeatherServiceImpl implements WeatherService {

    @Autowired
    WeatherDao weatherDao;

    @Autowired
    WeatherClient weatherClient;

    @Override
    public List<String> search(String key) {
        try {
            return weatherDao.search(key);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public WeatherDetail getWeatherInfo(String city) {
        try {
            WeatherDetail weatherDetail = weatherDao.getWeatherInfo(city);
            if(weatherDetail == null) {
                weatherDetail = weatherClient.getWeatherFromClient(city);
                if(weatherDetail != null) {
                    weatherDao.insertWeatherDetails(weatherDetail);
                }
            }
            return weatherDetail;
        } catch (Exception e) {
            throw e;
        }
    }
}
