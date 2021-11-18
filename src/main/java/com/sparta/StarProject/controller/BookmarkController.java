package com.sparta.StarProject.controller;

import com.sparta.StarProject.dto.*;
import com.sparta.StarProject.security.UserDetailsImpl;
import com.sparta.StarProject.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkService bookmarkService;


    //북마크하기
    @PostMapping("/bookmark")
    public ResponseDto addBookmark(@RequestParam("cardId") Long boardId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        BookmarkDto bookmarkDto = bookmarkService.addBookmark(boardId, userDetails.getUser());

        if(bookmarkDto.getBookmarkCheck().equals(false)){
            return new ResponseDto(201L, "북마크가 취소되었습니다..", bookmarkDto);
        }
        else{
            return new ResponseDto(200L, "북마크 되었습니다.", bookmarkDto);
        }

    }

    @GetMapping("/my/bookmark")
    public ResponseDto getMyBookMark(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                     @RequestParam(defaultValue = "1", required = false) int offset
    ){
        Page<MyBookmarkListDto> myBookmarkListDto = bookmarkService.getMyBookMark(userDetails.getUser(), offset-1);

        PageResponseDto pageResponseDto = new PageResponseDto(myBookmarkListDto.getNumber()+1,
                myBookmarkListDto.getTotalPages(), myBookmarkListDto.getContent().size(), myBookmarkListDto.getContent());

        return new ResponseDto(200L, "성공", pageResponseDto);
    }



}

