package com.sparta.StarProject.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.sparta.StarProject.domain.board.Timestamped;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    private String modifiedAt;
    private Long likeCount;
    private Boolean likeCheck;

    @QueryProjection
    public CommunityDtoCustom(Long id, String writer, String title, String cityName, String address, String img, String contents, LocalDateTime modifiedAt, Long likeCount, Boolean likeCheck) {
        String[] sliceAddress = address.split(" ");
        String imgResult = "";

        for (int i = 0; i < sliceAddress.length; i++) {
            if(i>1)
                break;
            imgResult += sliceAddress[i] + " ";
        }

        String contentResult = contents.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");

        this.id = id;
        this.writer = writer;
        this.title = title;
        this.cityName = cityName;
        this.address = imgResult;
        this.img = img;
        this.contents = contentResult;
        this.modifiedAt = Timestamped.TimeToString(modifiedAt);
        this.likeCount = likeCount;
        this.likeCheck = likeCheck;
    }
}
