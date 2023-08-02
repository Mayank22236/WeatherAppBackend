package com.omnify.weatherApp.cilent;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.omnify.weatherApp.model.WeatherDetail;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class WeatherClient {

    public WeatherDetail getWeatherFromClient(String city) {

        if(city == null) return null;

        HttpClient httpClient = HttpClient.newHttpClient();

        // Set the URL for the request
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city.toLowerCase() + "&appid=f56f24967aaf51182d1d4df628297c6d";

        // Create an HTTP GET request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        try {
            WeatherDetail weatherDetail = null;
            // Send the HTTP GET request and get the response
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // Check if the request was successful
            if (response.statusCode() == 200) {
                // Read the response body as a string
                String responseBody = response.body();
                System.out.println("Response:");
                System.out.println(responseBody);
                if(responseBody != null) {
                    try {
                        // Create an ObjectMapper instance
                        ObjectMapper objectMapper = new ObjectMapper();

                        // Parse the JSON string to a JsonNode
                        JsonNode jsonNode = objectMapper.readTree(responseBody);

                        // Now you can work with the JsonNode object
                        weatherDetail = new WeatherDetail();
                        JsonNode tempJson = jsonNode.get("main");
                        Double temp = tempJson.get("temp").asDouble() - 273; // Converting into celsius
                        Integer humidity = tempJson.get("humidity").asInt();
                        weatherDetail.setCityName(city.toLowerCase());
                        weatherDetail.setTemperature(temp.intValue());
                        weatherDetail.setHumidity(humidity);
                        for (JsonNode weatherNode : jsonNode.get("weather")) {
                            String description = weatherNode.get("description").asText();
                            weatherDetail.setWeatherType(description);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return weatherDetail;
            } else {
                System.out.println("Request failed with status code: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Error making the request: " + e.getMessage());
        }
        return null;
    }
}
