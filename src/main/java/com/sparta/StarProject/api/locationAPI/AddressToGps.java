package com.sparta.StarProject.api.locationAPI;


import com.sparta.StarProject.dto.GeographicDto;
import lombok.extern.slf4j.Slf4j;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Component;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class AddressToGps {
    public GeographicDto getAddress(String address) throws Exception {
        String addr = URLEncoder.encode(address,"utf-8");
        String api = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query="+addr;
        StringBuffer sb = new StringBuffer();
        try {
            URL url = new URL(api);
            HttpsURLConnection http = (HttpsURLConnection)url.openConnection();
            http.setRequestProperty("Content-Type", "application/json");
            http.setRequestProperty("X-NCP-APIGW-API-KEY-ID", "st5qvd1jn8");
            http.setRequestProperty("X-NCP-APIGW-API-KEY", "vNwmtJeX7FNgxYnr3DhpoKjgrDptjd9gbpsyIAB5");
            http.setRequestMethod("GET");
            http.connect();

            InputStreamReader in = new InputStreamReader(http.getInputStream(),"utf-8");
            BufferedReader br = new BufferedReader(in);

            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }

            JSONParser parser = new JSONParser();
            JSONObject jsonObject;
            JSONObject jsonObject2;
            JSONArray jsonArray;
            String x = "";
            String y = "";

            //트리형태로 온 JSON 파싱
            jsonObject = (JSONObject)parser.parse(sb.toString());
            jsonArray = (JSONArray)jsonObject.get("addresses");
            for(int i=0;i<jsonArray.size();i++){
                jsonObject2 = (JSONObject) jsonArray.get(i);
                if(null != jsonObject2.get("x")){
                    x = (String) jsonObject2.get("x").toString();
                }
                if(null != jsonObject2.get("y")){
                    y = (String) jsonObject2.get("y").toString();
                }
            }

            br.close();
            in.close();
            http.disconnect();

            List<String> result = new ArrayList<>();
            result.add(x);
            result.add(y);

            GeographicDto geographicDto = new GeographicDto(y, x);
            return geographicDto;

        } catch (IOException e) {
            log.info("execption = {}",e);
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        AddressToGps addressToGps = new AddressToGps();
        GeographicDto address = addressToGps.getAddress("경기도");
        log.info("위도 = {}, 경도 = {}", address.getY_location(), address.getX_location());
    }

}
