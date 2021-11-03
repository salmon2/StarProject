package com.sparta.StarProject.domain.board;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.StarProject.domain.HashTag;
import com.sparta.StarProject.domain.Like;
import com.sparta.StarProject.domain.Location;
import com.sparta.StarProject.domain.User;
import com.sparta.StarProject.dto.BoardRequestDto;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.*;

@Entity
@BatchSize(size = 100)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter
@Setter
@ToString   //문자열
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Table(name = "Board")
@EntityListeners(AuditingEntityListener.class)
public class Board extends Timestamped {

    @Id
    @GeneratedValue
    private Long id;


    private String locationName;
    private String content;
    private String img;

    public Board(String locationName, String content, String img) {
        this.locationName = locationName;
        this.content = content;
        this.img = img;
    }

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @OneToOne(mappedBy = "board", fetch = LAZY)
    private Location location;

    @OneToMany(mappedBy = "board", fetch = LAZY, cascade = ALL)
    private Set<Like> like = new HashSet<>();

    @OneToMany(mappedBy = "board", fetch = LAZY, cascade = ALL)
    private List<HashTag> hashTagList = new ArrayList<>();


}
