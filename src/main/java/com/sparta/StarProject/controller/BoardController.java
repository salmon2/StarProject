package com.sparta.StarProject.controller;

import com.sparta.StarProject.domain.User;
import com.sparta.StarProject.domain.board.Board;
import com.sparta.StarProject.dto.BoardDto;
import com.sparta.StarProject.dto.CommunityDto;
import com.sparta.StarProject.dto.DetailBoardDto;
import com.sparta.StarProject.dto.MapBoardDto;
import com.sparta.StarProject.dto.ResponseDto;
import com.sparta.StarProject.security.UserDetailsImpl;
import com.sparta.StarProject.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/community/list")
    public ResponseDto getBoard(){

        List<CommunityDto> communityDtoList = boardService.getBoardList();
        return new ResponseDto(200L, "성공", communityDtoList);
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
    @PostMapping("/board")
    public ResponseDto createBoard(@RequestBody BoardDto boardDto, @AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception {

        User user = userDetails.getUser();
        Board createBoard = boardService.createBoard(boardDto, user);

        return new ResponseDto(200L,"성공",boardDto);
    }

    @PutMapping("/board/update")
    public ResponseDto updateBoard(@RequestParam Long id,@RequestBody BoardDto boardDto){
        Board updateBoard = boardService.updateBoard(id, boardDto);
        return new ResponseDto(200L,"성공",updateBoard);
    }

    @GetMapping("/board/map/list")
    public ResponseDto getMapList(){
        List<MapBoardDto> mapBoardDto = boardService.getBoardMapList();
        return new ResponseDto(200L, "성공", mapBoardDto);
    }

    @GetMapping("/board/keyword")
    public ResponseDto searchBoard(@RequestParam(value = "key") String key){
        List<BoardDto> boardDtoList = boardService.searchBoard(key);

        return new ResponseDto(200L,"성공",boardDtoList);
    }
}
