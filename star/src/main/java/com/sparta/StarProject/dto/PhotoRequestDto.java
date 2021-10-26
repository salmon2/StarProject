package com.sparta.StarProject.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PhotoRequestDto {
    private String starImg;
    private String starName;
    private String comment;
}
