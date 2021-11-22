package com.sparta.StarProject.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class MainDto {
    private Long id;
    private String title;
    private String address;
    private String contents;
    private Long StarGazing;
    private String img;
    private Boolean bookmark;

    public MainDto(Long id, String title, String address, String contents, Long starGazing, String img, Boolean bookmark) {
        String[] sliceAddress = address.split(" ");
        String result = "";

        for (int i = 0; i < sliceAddress.length; i++) {
            if(i>1)
                break;
            result += sliceAddress[i] + " ";
        }
        String contentResult = contents.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");

        this.id = id;
        this.title = title;
        this.address = result;
        this.contents = contentResult;
        StarGazing = starGazing;
        this.img = img;
        this.bookmark = bookmark;
    }
}
