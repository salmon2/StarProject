package com.sparta.StarProject.api.dustApi;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DustCity {
    Seoul("Seoul", 11L, "서울"),
    Busan("Busan", 26L, "부산"),
    Daegu("Daegu", 27L, "대구"),
    Incheon("Incheon", 28L, "인천"),
    Gwangju("Gwangju", 29L, "광주"),
    Daejeon("Daejeon", 30L, "대전"),
    Ulsan("Ulsan", 31L, "울산"),

    GyeonggiDo("Gyeonggi-do", 41L, "경기"),
    GangwonDo("Gangwon-do", 42L, "강원"),
    ChungcheongbukDo("Chungcheongbuk-do", 43L, "충북"),
    ChungcheongnamDo("Chungcheongnam-do", 44L, "충남"),

    GyeongsangbukDo("Gyeongsangbuk-do", 43L, "경북"),
    GyeongsangnamDo("Gyeongsangnam-do", 44L, "경남"),
    JeollabukDo("Jeollabuk-do", 45L, "전북"),
    JeollanamDo("Jeollanam-do", 48L, "전남"),
    JejuDo("Jeju-do", 49L, "제주"),
    Sejong("Sejong", 50L, "세종");

    private String name;
    private Long Id;
    private String korName;

    public static DustCity getDustCityByString(String text){
        for (DustCity value : DustCity.values()) {
            if(value.getKorName().equals(text)){
                return value;
            }
        }
        return null;
    }

}
