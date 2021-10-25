package com.sparta.StarProject.domain.board;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@DiscriminatorValue("UserMake")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class UserMake extends Board{
    private String userdata;

    public UserMake(String locationName, String content, String img, String userdata) {
        super(locationName, content, img);
        this.userdata = userdata;
    }
}
