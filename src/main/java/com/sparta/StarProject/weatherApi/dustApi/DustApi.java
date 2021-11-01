package com.sparta.StarProject.weatherApi.dustApi;

import com.sparta.StarProject.dto.DustApiDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URL;
import java.net.URLEncoder;

@Component
@Slf4j
public class DustApi {
    private final String apiKey = "d0H0AaFc9Bq3uqyOHgbQ%2BfYrNjZXkTsepK6WlE4Ua6recSchagiHNTq6xOiiEr0PbFYD8mAiH82NCurTeHsKqA%3D%3D";

    public DustApiDto getDust(DustCity dustCity) throws Exception {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "="+apiKey); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("returnType","UTF-8") + "=" + URLEncoder.encode("xml", "UTF-8")); /*xml 또는 json*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("100", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("sidoName","UTF-8") + "=" + URLEncoder.encode(dustCity.getKorName(), "UTF-8")); /*시도 이름(전국, 서울, 부산, 대구, 인천, 광주, 대전, 울산, 경기, 강원, 충북, 충남, 전북, 전남, 경북, 경남, 제주, 세종)*/
        urlBuilder.append("&" + URLEncoder.encode("ver","UTF-8") + "=" + URLEncoder.encode("1.0", "UTF-8")); /*버전별 상세 결과 참고*/
        URL url = new URL(urlBuilder.toString());
        //System.out.println("urlBuilder = " + urlBuilder.toString());

        DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
        Document doc = dBuilder.parse(urlBuilder.toString());

        doc.getDocumentElement().normalize();
        //System.out.println("Root element: " + doc.getDocumentElement().getNodeName()); // Root element: result

        NodeList nList = doc.getElementsByTagName("item");

        for(int temp = 0; temp < nList.getLength(); temp++){
            Node nNode = nList.item(temp);
            if(nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                DustApiDto newDustApiDto = new DustApiDto(getTagValue("pm10Value", eElement));
                log.info("dust = {}", newDustApiDto);
                return newDustApiDto;

            }
        }
        log.info("dust = null");
        return null;
    }
    private static String getTagValue(String tag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
        Node nValue = (Node) nlList.item(0);
        if(nValue == null)
            return null;
        return nValue.getNodeValue();
    }


    public static void main(String[] args) throws Exception {
        DustApi dustApi = new DustApi();
        dustApi.getDust(DustCity.ChungcheongbukDo);
    }

}




