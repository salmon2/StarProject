package com.sparta.StarProject.api.accuweatherAPI;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum StarGazingCity {
    서울("서울",  11, "서울" ),
    부산("부산",  26,"부산"),
    대구("대구",  27, "대구" ),
    인천("인천",  28, "인천"),
    광주("광주",  29, "광주"),
    대전("대전",  30, "대전"),
    울산("울산",31, "울산"),
    //경기
    세종("세종", 50, "경기"),
    수원("수원", 223670, "경기"),
    성남("성남", 223672, "경기"),
    의정부("의정부",223648, "경기" ),
    안양	("안양",223640 , "경기"),
    부천	("부천", 223671, "경기"),
    광명	("광명", 223645, "경기"),
    평택	("평택", 223646, "경기"),
    동두천("동두천",223651, "경기"),
    안산	("안산",223641, "경기"),
    고양	("고양",498, "경기"),
    과천	("과천",223653, "경기"),
    구리	("구리",223644, "경기"),
    남양주("남양주",223649, "경기"),
    오산	("오산",223634, "경기"),
    시흥	("시흥",223647, "경기"),
    군포	("군포",223643, "경기"),
    의왕	("의왕",223635, "경기"),
    하남	("하남",223633, "경기"),
    용인	("용인",223639, "경기"),
    파주	("파주",223659, "경기"),
    이천	("이천",223642, "경기"),
    안성	("안성",223632, "경기"),
    김포	("김포",223636, "경기"),
    화성	("화성",223650, "경기"),
    경기광주("경기광주",223637, "경기"),
    양주	("양주",2330414, "경기"),
    포천	("포천",2330409, "경기"),
    여주	("여주",223656, "경기"),
    연천	("연천",223667, "경기"),
    가평	("가평",223652, "경기"),
    양평	("양평",1315396, "경기"),
    //강원도
    춘천	("춘천",223554,"강원"),
    원주	("원주",223559,"강원"),
    강릉	("강릉",223558,"강원"),
    동해	("동해",223557,"강원"),
    태백	("태백",223569,"강원"),
    속초	("속초",223556,"강원"),
    삼척	("삼척",223555,"강원"),
    홍천	("홍천",223564,"강원"),
    횡성	("횡성",223561,"강원"),
    영월	("영월",223572,"강원"),
    평창	("평창",223567,"강원"),
    정선	("정선",223576,"강원"),
    철원	("철원",223560,"강원"),
    화천	("화천",223565,"강원"),
    양구	("양구",223595,"강원"),
    인제	("인제",223593,"강원"),
    고성	("고성",223810,"강원"),
    양양	("양양",223571,"강원"),
    //충북
    청주	("청주",223117 , "충북"),
    충주	("충주",223115, "충북"),
    제천	("제천",223116, "충북"),
    보은	("보은",223122, "충북"),
    옥천	("옥천",223132, "충북"),
    영동	("영동",223120, "충북"),
    증평	("증평",2330373, "충북"),
    진천	("진천",223118, "충북"),
    괴산	("괴산",223129, "충북"),
    음성	("음성",223119, "충북"),
    단양	("단양",223137, "충북"),
    //충남
    천안	("천안",223148, "충남"),
    공주	("공주",223143, "충남"),
    보령	("보령",2330375, "충남"),
    아산	("아산",223147, "충남"),
    서산	("서산",223146, "충남"),
    논산	("논산",223145, "충남"),
    계룡	("계룡",2330378, "충남"),
    당진	("당진",223184, "충남"),
    금산	("금산",223152, "충남"),
    부여	("부여",223154, "충남"),
    서천	("서천",223156, "충남"),
    청양	("청양",223159, "충남"),
    홍성	("홍성",223151, "충남"),
    예산	("예산",223158, "충남"),
    태안	("태안",2330380, "충남"),
    //전북
    전주	("전주",223078, "전북"),
    군산	("군산",223083, "전북"),
    익산	("익산",223082, "전북"),
    정읍	("정읍",223080, "전북"),
    남원	("남원",223081, "전북"),
    김제	("김제",223079, "전북"),
    완주	("완주",2330428, "전북"),
    진안	("진안",223084, "전북"),
    무주	("무주",223090, "전북"),
    장수	("장수",223093, "전북"),
    임실	("임실",223097, "전북"),
    순창	("순창",223112, "전북"),
    고창	("고창",223089, "전북"),
    부안	("부안",223086, "전북"),
    //전남
    목포	("목포",224257, "전남"),
    여수	("여수",224259, "전남"),
    순천	("순천",224258, "전남"),
    나주	("나주",224256, "전남"),
    광양	("광양",224255, "전남"),
    담양	("담양",224270, "전남"),
    곡성	("곡성",224267, "전남"),
    구례	("구례",224297, "전남"),
    고흥	("고흥",224342, "전남"),
    보성	("보성",224269, "전남"),
    화순	("화순",224341, "전남"),
    장흥	("장흥",224264, "전남"),
    강진	("강진",224262, "전남"),
    해남	("해남",224266, "전남"),
    영암	("영암",224273, "전남"),
    무안	("무안",224344, "전남"),
    함평	("함평",224261, "전남"),
    영광	("영광",224338, "전남"),
    장성	("장성",2330430, "전남"),
    완도	("완도",224271, "전남"),
    진도	("진도",224260, "전남"),
    신안	("신안",1530458, "전남"),
    //경북
    포항	("포항",223682, "경북"),
    경주	("경주",223681, "경북"),
    김천	("김천",223673, "경북"),
    안동	("안동",223679, "경북"),
    구미	("구미",223729, "경북"),
    영주	("영주",223677, "경북"),
    영천	("영천",223675, "경북"),
    상주	("상주",223676, "경북"),
    문경	("문경",223674, "경북"),
    경산	("경산",223674, "경북"),
    군위	("군위",223730, "경북"),
    의성	("의성",223693, "경북"),
    청송	("청송", 223700, "경북"),
    영양	("영양",223774, "경북"),
    영덕	("영덕",223692, "경북"),
    청도	("청도",223692, "경북"),
    고령	("고령",223726, "경북"),
    성주	("성주",223756, "경북"),
    칠곡	("칠곡",2330417, "경북"),
    예천	("예천",223691, "경북"),
    봉화	("봉화",223694, "경북"),
    울진	("울진",223690, "경북"),
    울릉	("울릉",226438, "경북"),

    //경남
    창원 ("창원",223791, "경남"),
    진주	("진주",223798, "경남"),
    통영	("통영",223796, "경남"),
    사천	("사천",223752, "경남"),
    김해	("김해",223799, "경남"),
    밀양	("밀양",223793, "경남"),
    거제	("거제",223797, "경남"),
    양산	("양산",223801, "경남"),
    의령	("의령",223851, "경남"),
    함안	("함안",223820, "경남"),
    창녕	("창녕",223802, "경남"),
    남해	("남해",223858, "경남"),
    하동	("하동",223803, "경남"),
    산청	("산청",223805, "경남"),
    함양	("함양",223808, "경남"),
    거창	("거창",223804, "경남"),
    합천	("합천",223821, "경남"),
    제주	("제주",224209, "경남"),
    서귀포시("서귀포시",224210, "경남");

    private String korName;
    private Integer id;
    private String state;

    public static StarGazingCity getStarGazingCityByString(String text){
        for (StarGazingCity value : StarGazingCity.values()) {
            if(value.getKorName().equals(text)){
                return value;
            }
        }
        return null;
    }



}
