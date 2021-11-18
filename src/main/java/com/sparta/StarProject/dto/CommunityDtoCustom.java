package com.sparta.StarProject.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CommunityDtoCustom {
    private Long id;
    private String writer;
    private String title;
    private String cityName;
    private String address;
    private String img;
    private String contents;
    private LocalDateTime modifiedAt;
    private Long likeCount;
    private Boolean likeCheck;

    @QueryProjection
    public CommunityDtoCustom(Long id, String writer, String title, String cityName, String address, String img, String contents, LocalDateTime modifiedAt, Long likeCount, Boolean likeCheck) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.cityName = cityName;
        this.address = address;
        this.img = img;
        this.contents = contents;
        this.modifiedAt = modifiedAt;
        this.likeCount = likeCount;
        this.likeCheck = likeCheck;
    }
}
