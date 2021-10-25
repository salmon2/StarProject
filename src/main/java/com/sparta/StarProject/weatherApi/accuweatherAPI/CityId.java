package com.sparta.StarProject.weatherApi.accuweatherAPI;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public enum CityId {
    Seoul("Seoul", 11L), Busan("Busan", 26L),
    Daegu("Daegu", 27L), Incheon("Incheon", 28L),
    Gwangju("Gwangju", 29L), Daejeon("Daejeon", 30L),
    Ulsan("Ulsan", 31L), GyeonggiDo("Gyeonggi-do", 41L),
    GangwonDo("Gangwon-do", 42L), ChungcheongbukDo("Chungcheongbuk-do", 43L),
    ChungcheongnamDo("Chungcheongnam-do", 44L), JeollabukDo("Jeollabuk-do", 45L),
    JeollanamDo("Jeollanam-do", 48L), JejuDo("Jeju-do", 49L),
    Sejong("Sejong", 50L);

    private String name;
    private Long id;

    CityId(String name, Long id) {
        this.name = name;
        this.id = id;
    }

}
