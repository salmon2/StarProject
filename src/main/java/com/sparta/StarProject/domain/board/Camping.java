package com.sparta.StarProject.domain.board;

import lombok.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@DiscriminatorValue("Camping")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Camping extends Board{

    private String campingData;

    public Camping(String locationName, String content, String img, String campingData) {
        super(locationName, content, img);
        this.campingData = campingData;
    }
}
