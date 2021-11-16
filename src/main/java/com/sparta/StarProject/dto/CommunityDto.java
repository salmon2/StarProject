package com.sparta.StarProject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommunityDto  implements Comparable<CommunityDto>{
    private Long id;
    private String writer;
    private String title;
    private String cityName;
    private String img;
    private Long like;
    private String contents;
    private String modifiedAt;

    @Override
    public int compareTo(CommunityDto communityDto) {
        if (communityDto.like > this.like) {
            return 1;
        } else if (communityDto.like < this.like) {
            return -1;
        }
        return 0;
    }

}
