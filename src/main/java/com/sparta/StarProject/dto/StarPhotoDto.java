package com.sparta.StarProject.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StarPhotoDto {
    private String starImg;
    private String starName;
    private String comment;
}