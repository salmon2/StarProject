package com.sparta.StarProject.weatherApi.accuweatherAPI;



import lombok.extern.slf4j.Slf4j;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Map;

@Component
@Slf4j
public class AccuWeatherApi {
    private final String apiKey = "WYPVfBdCy5hYrmNgjSj9ihfSJ45cDJQl";
    private final String StarGazingId = "12";

    public void getStarGazing(CityId cityId) throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        HttpEntity httpEntity = new HttpEntity(httpHeaders);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity responseEntity = restTemplate.exchange(
                "http://dataservice.accuweather.com/indices/v1/daily/5day" +
                        "/" + cityId.getId().toString() +
                        "/" + StarGazingId +
                        "?apikey=" + apiKey +
                        "&language=ko-kr", HttpMethod.GET, httpEntity, String.class);

        JSONArray jsonArray = new JSONArray(responseEntity.getBody().toString());

        for (int i = 0; i < jsonArray.length(); i++) {
            Map<String, Object> map = jsonArray.getJSONObject(i).toMap();
            String name = (String) map.get("Name");

            System.out.println("name = " + name);
        }

    }

    public static void main(String[] args) throws Exception {
        AccuWeatherApi accuWeatherApi = new AccuWeatherApi();
        accuWeatherApi.getStarGazing(CityId.Seoul);
    }

}
