package com.sparta.StarProject.weatherApi.accuweatherAPI;



import com.sparta.StarProject.dto.StarGazingDto;
import lombok.extern.slf4j.Slf4j;

import org.json.JSONArray;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class AccuWeatherApi {
    private final String apiKey = "WYPVfBdCy5hYrmNgjSj9ihfSJ45cDJQl";
    private final String StarGazingId = "12";

    public List<StarGazingDto> getStarGazing(StarGazingCity cityId) throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType( new MediaType("application", "json", Charset.forName("UTF-8")));

        HttpEntity httpEntity = new HttpEntity(httpHeaders);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity responseEntity = restTemplate.exchange(
                "http://dataservice.accuweather.com/indices/v1/daily/5day" +
                        "/" + cityId.getId().toString() +
                        "/" + StarGazingId +
                        "?apikey=" + apiKey +
                        "&language=ko-kr", HttpMethod.GET, httpEntity, String.class);

        JSONArray jsonArray = new JSONArray(responseEntity.getBody().toString());

        List<StarGazingDto> starGazingDtoList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            Map<String, Object> map = jsonArray.getJSONObject(i).toMap();
            StarGazingDto newStarGazingDto = new StarGazingDto(map);
            starGazingDtoList.add(newStarGazingDto);
        }

        return starGazingDtoList;
    }

    public static void main(String[] args) throws Exception {
        AccuWeatherApi accuWeatherApi = new AccuWeatherApi();
        List<StarGazingDto> starGazing = accuWeatherApi.getStarGazing(StarGazingCity.광주);
        for (StarGazingDto starGazingDto : starGazing) {
            System.out.println("starGazingDto = " + starGazingDto);
        }
    }


}
