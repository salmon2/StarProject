package com.sparta.StarProject.api.accuweatherAPI;



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
    private final String StarGazingId = "12";
    private final String[] apiKeyList = {
            "WYPVfBdCy5hYrmNgjSj9ihfSJ45cDJQl",
            "p6wV66GJIR4kIkpqe4DlHIXLg4f6GE5r",
            "8HAczaRzd9THEjlcmTrzucgtfFNDl8LK",
            "LsVoEqE65kPf3Sz91zBhJaMlO4scciuB",
            "GAeuwGP4vCAKeVLY47RyEzoKeozEJG82",
            "AqXAn7GgSIBTsCOFCHvL7asizcukERry"
    };

    public List<StarGazingDto> getStarGazing(StarGazingCity cityId, int count) throws Exception {

        String apiKey = apiKeyList[count%apiKeyList.length];

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
        log.info("AcuuWeather URL = {}", "http://dataservice.accuweather.com/indices/v1/daily/5day" +
                "/" + cityId.getId().toString() +
                "/" + StarGazingId +
                "?apikey=" + apiKey +
                "&language=ko-kr");

        JSONArray jsonArray = new JSONArray(responseEntity.getBody().toString());

        List<StarGazingDto> starGazingDtoList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            Map<String, Object> map = jsonArray.getJSONObject(i).toMap();
            StarGazingDto newStarGazingDto = new StarGazingDto(map);
            starGazingDtoList.add(newStarGazingDto);
        }

        log.info("stargazing = {}", starGazingDtoList);
        log.info("star count = {}", count);

        return starGazingDtoList;
    }

    public static void main(String[] args) throws Exception {
        AccuWeatherApi accuWeatherApi = new AccuWeatherApi();
        List<StarGazingDto> starGazing = accuWeatherApi.getStarGazing(StarGazingCity.가평, 0);
        for (StarGazingDto starGazingDto : starGazing) {
            System.out.println("starGazingDto = " + starGazingDto);
        }
    }


}
