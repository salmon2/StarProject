package com.sparta.StarProject.controller;

import com.sparta.StarProject.domain.board.Board;
import com.sparta.StarProject.dto.BookmarkDto;
import com.sparta.StarProject.dto.ResponseDto;
import com.sparta.StarProject.security.UserDetailsImpl;
import com.sparta.StarProject.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @GetMapping("/bookmark")
    public ResponseDto addBookmark(@RequestParam Long boardId, @AuthenticationPrincipal UserDetailsImpl userDetails){

        return bookmarkService.addBookmark(boardId, userDetails);
    }

}
