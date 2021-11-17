package com.sparta.StarProject.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BookmarkMapList {
    private Long id;
    private Boolean bookmark;

    @QueryProjection
    public BookmarkMapList(Long id, Boolean bookmark) {
        this.id = id;
        this.bookmark = bookmark;
    }
}
