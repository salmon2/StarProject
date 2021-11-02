package com.sparta.StarProject.api.locationAPI;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;

import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GpsToAddress {
    String apiKey = "AIzaSyDLM06kFfE59mV-VFN9GcAmnvebdWir7xY";

    public String getAddress(double latitude, double longitude) throws Exception {
        String regionAddress = getRegionAddress(getJSONData(getApiAddress(latitude, longitude)));

        return regionAddress;
    }

    private String getApiAddress(double latitude, double longitude) {
        String apiURL = "https://maps.googleapis.com/maps/api/geocode/json?latlng="
                + latitude + "," + longitude + "&language=ko"+
                "&key="+apiKey;
        return apiURL;
    }

    private String getJSONData(String apiURL) throws Exception {
        String jsonString = new String();
        String buf;
        URL url = new URL(apiURL);
        URLConnection conn = url.openConnection();
        BufferedReader br = new BufferedReader(new InputStreamReader(
                conn.getInputStream(), "UTF-8"));
        while ((buf = br.readLine()) != null) {
            jsonString += buf;
        }
        return jsonString;
    }

    private String getRegionAddress(String jsonString) {
        JSONObject jObj = (JSONObject) JSONValue.parse(jsonString);
        JSONArray jArray = (JSONArray) jObj.get("results");
        jObj = (JSONObject) jArray.get(0);
        return (String) jObj.get("formatted_address");
    }


    public static void main(String[] args) throws Exception {
        double latitude = 37.3645764;   //위도
        double longitude = 127.8340380; //경도

        GpsToAddress gps = new GpsToAddress();
        String address = gps.getAddress(latitude, longitude);

        log.info("Address = {}", address);
    }

}
