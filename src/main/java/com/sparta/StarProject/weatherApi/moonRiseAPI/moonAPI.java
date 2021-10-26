package com.sparta.StarProject.weatherApi.moonRiseAPI;

import com.sparta.StarProject.dto.SunMoonDto;
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

public class moonAPI {

    public SunMoonDto getMoon() throws IOException, ParserConfigurationException, SAXException {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B090041/openapi/service/RiseSetInfoService/getAreaRiseSetInfo"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=d0H0AaFc9Bq3uqyOHgbQ%2BfYrNjZXkTsepK6WlE4Ua6recSchagiHNTq6xOiiEr0PbFYD8mAiH82NCurTeHsKqA%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("locdate","UTF-8") + "=" + URLEncoder.encode("20211026", "UTF-8")); /*날짜*/
        urlBuilder.append("&" + URLEncoder.encode("location","UTF-8") + "=" + URLEncoder.encode("광주(경기)", "UTF-8")); /*지역*/
        System.out.println("urlBuilder.toString() = " + urlBuilder.toString());

        DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
        Document doc = dBuilder.parse(urlBuilder.toString());

        doc.getDocumentElement().normalize();
        System.out.println("Root element: " + doc.getDocumentElement().getNodeName()); // Root element: result
        // 파싱할 tag
        NodeList nList = doc.getElementsByTagName("item");
        System.out.println("파싱할 리스트 수 : "+ nList.getLength());

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
                return newSunMoonDto;
            }	// for end
        }	// if end


        return null;
    }

    private static String getTagValue(String tag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
        Node nValue = (Node) nlList.item(0);
        if(nValue == null)
            return null;
        return nValue.getNodeValue();
    }

    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
        moonAPI moonAPI = new moonAPI();
        SunMoonDto moon = moonAPI.getMoon();
        System.out.println("moon = " + moon);
    }


}
