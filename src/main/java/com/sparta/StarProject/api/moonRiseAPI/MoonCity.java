package com.sparta.StarProject.api.moonRiseAPI;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MoonCity {

    강릉("강릉"),
    강화도("강화도"),
    거제("거제"),
    거창("거창"),
    경산("경산"),
    경주("경주"),
    고양("고양"),
    고흥("고흥"),
    광양("광양"),
    광주("광주"),
    구미("구미"),
    군산("군산"),
    김천("김천"),
    김해("김해"),
    남원("남원"),
    남해("남해"),
    대관령("대관령"),
    대구("대구"),
    대덕("대덕"),
    대전("대전"),
    독도("독도"),
    동해("동해"),
    마산("마산"),
    목포("목포"),
    무안("무안"),
    밀양("밀양"),
    변산("변산"),
    보령("보령"),
    보성("보성"),
    보현산("보현산"),
    부산("부산"),
    부안("부안"),
    부천("부천"),
    사천("사천"),
    삼척("삼척"),
    상주("상주"),
    서귀포("서귀포"),
    서산("서산"),
    서울("서울"),
    서천("서천"),
    성산일출봉("성산일출봉"),
    세종("세종"),
    소백산("소백산"),
    속초("속초"),
    수원("수원"),
    순천("순천"),
    승주("승주"),
    시흥("시흥"),
    아산("아산"),
    안동("안동"),
    안산("안산"),
    안양("안양"),
    양양("양양"),
    양평("양평"),
    여수("여수"),
    여주("여주"),
    영광("영광"),
    영덕("영덕"),
    영월("영월"),
    영주("영주"),
    영천("영천"),
    완도("완도"),
    용인("용인"),
    울릉도("울릉도"),
    울산("울산"),
    울진("울진"),
    원주("원주"),
    의성("의성"),
    익산("익산"),
    인천("인천"),
    임실("임실"),
    장수("장수"),
    장흥("장흥"),
    전주("전주"),
    정읍("정읍"),
    제주("제주"),
    제천("제천"),
    주문진("주문진"),
    진도("진도"),
    진주("진주"),
    진해("진해"),
    창원("창원"),
    천안("천안"),
    청주("청주"),
    추풍령("추풍령"),
    춘양("춘양"),
    춘천("춘천"),
    충주("충주"),
    태백("태백"),
    태안("태안"),
    통영("통영"),
    파주("파주"),
    평택("평택"),
    포항("포항"),
    해남("해남"),
    화성("화성"),
    흑산도("흑산도");

    private String korName;

    public static MoonCity getMoonCityByString(String text, String location){
        for (MoonCity value : MoonCity.values()) {
            if(value.getKorName().equals(text)){
                return value;
            }
        }
        if(location.equals("경기")){
            return MoonCity.서울;
        }
        else if(location.equals("강원")){
            return MoonCity.강릉;
        }
        else if(location.equals("충북")){
            return MoonCity.청주;
        }
        else if(location.equals("충남")){
            return MoonCity.천안;
        }
        else if(location.equals("전북")){
            return MoonCity.전주;
        }
        else if(location.equals("전남")){
            return MoonCity.목포;
        }
        else if(location.equals("경북")){
            return MoonCity.대구;
        }
        else if(location.equals("경남")){
            return MoonCity.부산;
        }

        return null;
    }

}
