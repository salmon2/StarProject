package com.sparta.StarProject.controller;

import com.sparta.StarProject.dto.DetailBoardDto;
import com.sparta.StarProject.dto.ResponseDto;
import com.sparta.StarProject.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;


    @GetMapping("/detail")
    public ResponseDto detailBoard(@RequestParam Long boardId){

        DetailBoardDto detailBoardDto = boardService.getDetailBoard(boardId);

        return new ResponseDto(200L, "성공", detailBoardDto);
    }

}
