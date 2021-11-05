package com.sparta.StarProject.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class StarInfo {
    @Id
    @GeneratedValue
    private Long id;

    private String starImg;

    private String starName;
    private String comment;
    private String month;

    public StarInfo(String starImg, String starName, String comment, String month) {
        this.starImg = starImg;
        this.starName = starName;
        this.comment = comment;
        this.month = month;
    }
}
