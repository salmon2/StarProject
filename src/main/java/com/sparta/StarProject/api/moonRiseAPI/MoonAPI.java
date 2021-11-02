package com.sparta.StarProject.api.moonRiseAPI;

import com.sparta.StarProject.dto.SunMoonDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
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

@Component
@Slf4j
public class MoonAPI {
    private final String apiKey = "d0H0AaFc9Bq3uqyOHgbQ%2BfYrNjZXkTsepK6WlE4Ua6recSchagiHNTq6xOiiEr0PbFYD8mAiH82NCurTeHsKqA%3D%3D";

    public SunMoonDto getMoon(MoonCity moonCity) throws IOException, ParserConfigurationException, SAXException {

        List<String> date = getCurrentTime();

        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B090041/openapi/service/RiseSetInfoService/getAreaRiseSetInfo"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "="+ apiKey); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("locdate","UTF-8") + "=" + URLEncoder.encode(date.get(0), "UTF-8")); /*날짜*/

        //urlBuilder.append("&" + URLEncoder.encode("location","UTF-8") + "=" + URLEncoder.encode("광주(경기)", "UTF-8")); /*지역*/
        urlBuilder.append("&" + URLEncoder.encode("location","UTF-8") + "=" + URLEncoder.encode(moonCity.getKorName(), "UTF-8")); /*지역*/

        //System.out.println("urlBuilder.toString() = " + urlBuilder.toString());

        DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
        Document doc = dBuilder.parse(urlBuilder.toString());

        doc.getDocumentElement().normalize();
        //System.out.println("Root element: " + doc.getDocumentElement().getNodeName()); // Root element: result

        // 파싱할 tag
        NodeList nList = doc.getElementsByTagName("item");
        //System.out.println("파싱할 리스트 수 : "+ nList.getLength());

        for(int temp = 0; temp < nList.getLength(); temp++){
            Node nNode = nList.item(temp);
            if(nNode.getNodeType() == Node.ELEMENT_NODE){

                Element eElement = (Element) nNode;
//                System.out.println("######################");
//                //System.out.println(eElement.getTextContent());
//                System.out.println("월출  : " + getTagValue("moonrise", eElement));
//                System.out.println("월몰  : " + getTagValue("moonset", eElement));
//                System.out.println("일몰 : " + getTagValue( "sunrise", eElement));
//                System.out.println("일출  : " + getTagValue("sunset", eElement));
//                System.out.println("지역  : " + getTagValue("location", eElement));
//                System.out.println("지역  : " + getTagValue("aste", eElement));


                SunMoonDto newSunMoonDto = new SunMoonDto(
                        getTagValue("moonrise", eElement),
                        getTagValue("moonset", eElement),
                        getTagValue("sunrise", eElement),
                        getTagValue("sunset", eElement),
                        getTagValue("location", eElement),
                        getTagValue("aste", eElement),
                        getTagValue("locdate", eElement));
                log.info("moon = {}", newSunMoonDto);
                return newSunMoonDto;
            }	// for end
        }	// if end

        log.info("moon = NULL");
        return null;
    }

    private static String getTagValue(String tag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
        Node nValue = (Node) nlList.item(0);
        if(nValue == null)
            return null;
        return nValue.getNodeValue();
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

    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
        MoonAPI moonAPI = new MoonAPI();
        SunMoonDto moon = moonAPI.getMoon(MoonCity.대구);
        System.out.println("moon = " + moon);
    }


}
