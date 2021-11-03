package com.sparta.StarProject.controller;

import com.sparta.StarProject.dto.CommunityDto;
import com.sparta.StarProject.dto.DetailBoardDto;
import com.sparta.StarProject.dto.ResponseDto;
import com.sparta.StarProject.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/community/list")
    public ResponseDto getBoard(){

        List<CommunityDto> commutyDtoList = boardService.getBoardList();
        return new ResponseDto(200L, "성공", commutyDtoList);
    }


    @GetMapping("/detail")
    public ResponseDto detailBoard(@RequestParam Long boardId){

        DetailBoardDto detailBoardDto = boardService.getDetailBoard(boardId);

        return new ResponseDto(200L, "성공", detailBoardDto);
    }

    @DeleteMapping("/detail/delete")
    public ResponseDto deleteDetailBoard(@RequestParam Long boardId,
                                         @AuthenticationPrincipal UserDetails userDetails){
        int result = boardService.deleteBoard(boardId, userDetails);
        if(result == 1)
            return new ResponseDto(200L, "성공", null);
        return new ResponseDto(500L, "실패", null);
    }

}
