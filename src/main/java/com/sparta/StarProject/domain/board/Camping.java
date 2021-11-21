package com.sparta.StarProject.domain.board;


import com.sparta.StarProject.domain.Location;
import com.sparta.StarProject.domain.User;
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

    public Camping(String locationName, String address, String content, String img, Double longitude, Double latitude, User user, Location location, String campingData, String type) {
        super(locationName, address, content, img, longitude, latitude, user, location, type);
        this.campingData = campingData;
    }
}
