package com.sparta.StarProject.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MainDto {
    private Long id;
    private String title;
    private String address;
    private String contents;
    private Long StarGazing;
    private String img;
    private Boolean bookmark;

}
