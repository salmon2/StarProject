package com.sparta.StarProject.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PhotoRequestDto {
    private String starImg;
    private String starName;
    private String comment;
}
