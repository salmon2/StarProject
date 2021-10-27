package com.sparta.StarProject.weatherApi.weatherAPI;

import com.sparta.StarProject.dto.WeatherApiDto;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.net.URLEncoder;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.sparta.StarProject.weatherApi.weatherAPI.WeatherCategory.POP;

public class WeatherApi {
    private final String apiKey = "d0H0AaFc9Bq3uqyOHgbQ%2BfYrNjZXkTsepK6WlE4Ua6recSchagiHNTq6xOiiEr0PbFYD8mAiH82NCurTeHsKqA%3D%3D";
    public List<WeatherApiDto> getWeather(WeatherCity weatherCity) throws IOException, ParserConfigurationException, SAXException {

        List<String> date = getCurrentTime();
        for (String s : date) {
            System.out.println("s = " + s);
        }

        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "="+apiKey); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("50", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("XML", "UTF-8")); /*요청자료형식(XML/JSON) Default: XML*/
        urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode(date.get(0), "UTF-8")); /*‘21년 6월 28일발표*/
        urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode("2300", "UTF-8")); /*05시 발표*/  //3시간 단위
        urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode(weatherCity.getX().toString(), "UTF-8")); /*예보지점의 X 좌표값*/
        urlBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode(weatherCity.getY().toString(), "UTF-8")); /*예보지점의 Y 좌표값*/
//        System.out.println("urlBuilder.toString() = " + urlBuilder.toString());

        DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
        Document doc = dBuilder.parse(urlBuilder.toString());

        doc.getDocumentElement().normalize();
        //System.out.println("Root element: " + doc.getDocumentElement().getNodeName()); // Root element: result

        NodeList nList = doc.getElementsByTagName("item");
        //System.out.println("파싱할 리스트 수 : "+ nList.getLength());
        List<WeatherApiDto> weatherApiDtoList =  new ArrayList<>();

        for(int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;

                if (getTagValue("category",eElement).equals(POP.getName())){
                    WeatherApiDto weatherApiDto = new WeatherApiDto(
                            getTagValue("baseDate", eElement),
                            getTagValue("baseTime", eElement),
                            POP.getKorName(),
                            getTagValue("fcstDate", eElement),
                            getTagValue("fcstTime", eElement),
                            getTagValue("fcstValue", eElement)
                    );
                    weatherApiDtoList.add(weatherApiDto);
                }
                else if (getTagValue("category",eElement).equals(WeatherCategory.REH.getName())) {
                    WeatherApiDto weatherApiDto = new WeatherApiDto(
                            getTagValue("baseDate", eElement),
                            getTagValue("baseTime", eElement),
                            WeatherCategory.REH.getKorName(),
                            getTagValue("fcstDate", eElement),
                            getTagValue("fcstTime", eElement),
                            getTagValue("fcstValue", eElement)
                    );
                    weatherApiDtoList.add(weatherApiDto);
                }

                else if (getTagValue("category",eElement).equals(WeatherCategory.SKY.getName())) {
                    WeatherApiDto weatherApiDto = new WeatherApiDto(
                            getTagValue("baseDate", eElement),
                            getTagValue("baseTime", eElement),
                            WeatherCategory.SKY.getKorName(),
                            getTagValue("fcstDate", eElement),
                            getTagValue("fcstTime", eElement),
                            getTagValue("fcstValue", eElement)
                    );
                    weatherApiDtoList.add(weatherApiDto);
                }

                else if (getTagValue("category",eElement).equals(WeatherCategory.TMP.getName())) {
                    WeatherApiDto weatherApiDto = new WeatherApiDto(
                            getTagValue("baseDate", eElement),
                            getTagValue("baseTime", eElement),
                            WeatherCategory.TMP.getKorName(),
                            getTagValue("fcstDate", eElement),
                            getTagValue("fcstTime", eElement),
                            getTagValue("fcstValue", eElement)
                    );
                    weatherApiDtoList.add(weatherApiDto);
                }
            }
        }// end for

        return weatherApiDtoList;
    }

    private List<String> getCurrentTime() {
        SimpleDateFormat format1 = new SimpleDateFormat ( "yyyyMMdd");
        SimpleDateFormat format2 = new SimpleDateFormat ( "HH00");

        Date rawTime = new Date();

        String date = format1.format(rawTime);
        String time = format2.format(rawTime);

        List<String> result = new ArrayList<>();

        result.add(date);
        result.add(time);

        return result;
    }

    private static String getTagValue(String tag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
        Node nValue = (Node) nlList.item(0);
        if(nValue == null)
            return null;
        return nValue.getNodeValue();
    }


    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
        WeatherApi weatherApi = new WeatherApi();
        List<WeatherApiDto> weather = weatherApi.getWeather(WeatherCity.대구);
        for (WeatherApiDto weatherApiDto : weather) {
            System.out.println("weatherApiDto = " + weatherApiDto);
        }
    }

}