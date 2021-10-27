package com.sparta.StarProject.weatherApi.weatherAPI;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum WeatherCategory {
    POP("강수확률","POP"),
    PTY("강수형태", "PTY"),
    PCP("강수량","PCP"),
    REH("습도", "REH"),
    SNO("신적설", "SNO"),
    SKY("하늘상태", "SKY"),	//맑음(1), 구름많음(3), 흐림(4)
    TMP("기온℃", "TMP"),
    TMN("일 최저기온℃", "TMN"),
    TMX("일 최고기온℃", "TMX"),
    UUU("풍속(동서성분)","UUU"),
    VVV("풍속(남북성분)", "VVV"),
    WAV("파고", "WAV"),
    VEC("풍향", "VEC"),
    WSD("풍속", "WSD");

    private String korName;
    private String Name;
}
