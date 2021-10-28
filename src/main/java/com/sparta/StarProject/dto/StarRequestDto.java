package com.sparta.StarProject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StarRequestDto {
    private Long id;
    private String moonrise;
    private String moonset;
    private String visibility;
    private String humidity;
    private String weather;
    private String temperature;
    private Long StarGazing;
}
