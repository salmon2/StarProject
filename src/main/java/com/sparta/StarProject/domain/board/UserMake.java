package com.sparta.StarProject.domain.board;

import com.sparta.StarProject.domain.User;
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

    public UserMake(String locationName, String address, String content, String img, User user, String userdata) {
        super(locationName, address, content, img, user);
        this.userdata = userdata;
    }
}
