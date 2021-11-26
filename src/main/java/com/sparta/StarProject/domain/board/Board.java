package com.sparta.StarProject.domain.board;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.StarProject.domain.*;
import com.sparta.StarProject.dto.BoardDto;
import com.sparta.StarProject.dto.GeographicDto;
import com.sparta.StarProject.dto.QBoardDto;
import com.sparta.StarProject.dto.UpdateBoardDto;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.*;

@Entity
@BatchSize(size = 100)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
@ToString   //문자열
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Table(name = "Board")
@EntityListeners(AuditingEntityListener.class)
public class Board extends Timestamped{

    @Id @GeneratedValue
    private Long id;

    private String title;
    private String address;

    @Column( length = 10000000)
    private String content;

    @Column( length = 100000 )
    private String img;

    private Double longitude;
    private Double latitude;
    private Long likeCount;
    private String type;


    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "location_id")
    @JsonIgnore
    private Location location;


    @OneToMany(mappedBy = "board", fetch = LAZY, cascade = ALL)
    private Set<Like> like = new HashSet<>();

    @OneToMany(mappedBy = "board", fetch = LAZY, cascade = ALL)
    private List<HashTag> hashTagList = new ArrayList<>();

    @OneToMany(mappedBy = "board", orphanRemoval = true)
    @JsonIgnore
    private List<Bookmark> bookmark;

    public Board(String title, String address, String content, String img, Double longitude, Double latitude, User user, Location location, String type) {
        this.title = title;
        this.address = address;
        this.content = content;
        this.img = img;
        this.longitude = longitude;
        this.latitude = latitude;
        this.user = user;
        this.location = location;
        this.likeCount = 0L;
        this.type = type;
    }

    public Board(String address,String content,String img,String title, User user){
        this.title = title;
        this.address = address;
        this.img = img;
        this.content = content;
        this.user = user;
    }
    public Board(String address,String content,String img,String title){
        this.title = title;
        this.address = address;
        this.img = img;
        this.content = content;
    }

    public void update(UpdateBoardDto boardDto, GeographicDto address, Location location ){
        Pattern nonValidPattern = Pattern.compile("<img[^>]*src=[\"']?([^>\"']+)[\"']?[^>]*>");
        List result = new ArrayList();
        Matcher matcher = nonValidPattern.matcher(boardDto.getContent());
        while (matcher.find()) {
            result.add(matcher.group(1));
        }

        this.title = boardDto.getTitle();
        this.address = boardDto.getAddress();


        this.img = (result.size() == 0) ? "" : result.get(0).toString();

        this.content = boardDto.getContent();


        this.longitude = Double.valueOf(address.getX_location());
        this.latitude = Double.valueOf(address.getY_location());

        this.location = location;
    }



}
