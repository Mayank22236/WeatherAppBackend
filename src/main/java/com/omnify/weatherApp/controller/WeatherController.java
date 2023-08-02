package com.omnify.weatherApp.controller;

import com.omnify.weatherApp.model.WeatherDetail;
import com.omnify.weatherApp.service.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;

import java.util.List;

@RestController
@RequestMapping("service")

public class WeatherController {

    @Autowired
    WeatherService weatherService;

    private static final Logger LOG = LoggerFactory.getLogger(WeatherController.class);

    @CrossOrigin(origins = {"*"})
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public List<String> searchCity(@RequestParam(value = "key") String key) {
        LOG.info("search called.");
        try {
            return weatherService.search(key);
        } catch (RestClientException ex) {
            throw ex;
        }
    }

    @CrossOrigin(origins = {"*"})
    @RequestMapping(value = "/getWeatherInfo", method = RequestMethod.GET)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public WeatherDetail getWeatherInfo(@RequestParam(value = "city") String city) {
        LOG.info("getWeatherInfo called.");
        try {
            return weatherService.getWeatherInfo(city);
        } catch (RestClientException ex) {
            throw ex;
        }
    }
}
