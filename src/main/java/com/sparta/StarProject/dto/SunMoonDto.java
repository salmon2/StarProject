package com.sparta.StarProject.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SunMoonDto {
    private String moonrise;
    private String moonSet;
    private String sunrise;
    private String sunSet;
    private String location; //위치
    private String aste;    //천문박명
    private String date;    //날짜
}
