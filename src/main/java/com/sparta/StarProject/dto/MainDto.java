package com.sparta.StarProject.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class MainDto {
    private String title;
    private String address;
    private Long StarGazing;
    private String img;

    public MainDto(String title, String address, Long starGazing, String img) {
        this.title = title;
        this.address = address;
        StarGazing = starGazing;
        this.img = img;
    }
}
