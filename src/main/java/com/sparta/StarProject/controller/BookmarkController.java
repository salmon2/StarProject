package com.sparta.StarProject.controller;

import com.sparta.StarProject.domain.Bookmark;
import com.sparta.StarProject.dto.BookmarkDto;
import com.sparta.StarProject.dto.MyBookmarkListDto;
import com.sparta.StarProject.dto.ResponseDto;
import com.sparta.StarProject.repository.BoardRepository;
import com.sparta.StarProject.repository.BookmarkRepository;
import com.sparta.StarProject.repository.UserRepository;
import com.sparta.StarProject.security.UserDetailsImpl;
import com.sparta.StarProject.service.BookmarkService;
import lombok.RequiredArgsConstructor;
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
        Boolean bookmarkCheck = true;

        BookmarkDto bookmarkDto = bookmarkService.addBookmark(boardId, userDetails.getUser());

        if(bookmarkCheck){
            return new ResponseDto(200L, "북마크 되었습니다.", bookmarkDto);
        } else {
            return new ResponseDto(201L, "북마크가 취소 되었습니다.", bookmarkDto);
        }
    }


    @GetMapping("/my/bookmark")
    public ResponseDto getMyBookMark(@AuthenticationPrincipal UserDetailsImpl userDetails){
        List<MyBookmarkListDto> myBookmarkListDto = bookmarkService.getMyBookMark(userDetails.getUser());

        return new ResponseDto(200L, "북마크 추가 되었습니다.", myBookmarkListDto);
    }

}

