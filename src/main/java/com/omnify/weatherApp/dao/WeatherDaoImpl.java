package com.omnify.weatherApp.dao;

import com.omnify.weatherApp.model.WeatherDetail;
import jakarta.annotation.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class WeatherDaoImpl implements WeatherDao {

    @Resource(name = "weatherJdbcTemplate")
    private JdbcTemplate weatherJdbcTemplate;

    @Override
    public List<String> search(String key) {
        List<String> cityList = new ArrayList<>();
        try {
            String like = "%";
            StringBuilder query = new StringBuilder("select city from weather_details where city like '%");
            query.append(key).append("%'");
            List<Map<String, Object>> rows = weatherJdbcTemplate.queryForList(query.toString());
            if (!rows.isEmpty()) {
                for (Map<String, Object> row : rows) {
                    cityList.add(row.get("city").toString());
                }
            }
        } catch (DataAccessException e) {
            throw e;
        }
        return cityList;
    }

    @Override
    public WeatherDetail getWeatherInfo(String city) {
        WeatherDetail weatherDetail = null;
        try {
            List<Map<String, Object>> rows = weatherJdbcTemplate.queryForList("Select * from weather_details where city = '" + city + "'");
            if (!rows.isEmpty()) {
                Map<String, Object> row = rows.get(0);
                weatherDetail = new WeatherDetail();
                weatherDetail.setCityName(row.get("city").toString());
                weatherDetail.setTemperature((Integer) row.get("temperature"));
                weatherDetail.setHumidity((Integer) row.get("humidity"));
                weatherDetail.setWeatherType(row.get("weather_type").toString());
            }
        } catch (DataAccessException e) {
            throw e;
        }
        return weatherDetail;
    }

    @Override
    public void insertWeatherDetails(WeatherDetail weatherDetail) {
        if(weatherDetail != null) {
            StringBuilder query = new StringBuilder("INSERT INTO `weather`.`weather_details` (`city`,`temperature`,`humidity`,`weather_type`) VALUES (");
            query.append("'").append(weatherDetail.getCityName()).append("', ")
                    .append(weatherDetail.getTemperature()).append(", ").append(weatherDetail.getHumidity()).append(", '").append(weatherDetail.getWeatherType()).append("')");
            try {
                weatherJdbcTemplate.execute(query.toString());
            } catch (DataAccessException e) {
                throw e;
            }
        }
    }
}
