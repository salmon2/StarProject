package com.sparta.StarProject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StarInfoResponseDto {
    private String moonrise;
    private String moonset;
    private Long StarGazing;

    public StarInfoResponseDto(String moonrise, String moonset, Long starGazing) {
        this.moonrise = moonrise;
        this.moonset = moonset;
        StarGazing = starGazing;
    }
}
