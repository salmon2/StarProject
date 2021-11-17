package com.sparta.StarProject.controller;

import com.sparta.StarProject.domain.User;
import com.sparta.StarProject.domain.board.Board;
import com.sparta.StarProject.dto.*;
import com.sparta.StarProject.exception.StarProjectException;
import com.sparta.StarProject.repository.CampingRepository;
import com.sparta.StarProject.security.UserDetailsImpl;
import com.sparta.StarProject.service.BoardService;
import com.sparta.StarProject.service.LikeService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final CampingRepository campingRepository;

    private final LikeService likeService;

    @GetMapping("/community/list")
    public ResponseDto getBoard(@RequestParam(defaultValue = "star") String sort,
                                @RequestParam(defaultValue = "all") String cityName,
                                @AuthenticationPrincipal UserDetailsImpl userDetails){
        List<CommunityDto> communityDtoList = boardService.getBoardList(sort, cityName, userDetails);
        return new ResponseDto(200L, "성공", communityDtoList);
    }


    @GetMapping("/detail")
    public ResponseDto detailBoard(@RequestParam Long boardId,
                                       @AuthenticationPrincipal UserDetailsImpl userDetails){
        DetailBoardDto detailBoardDto = boardService.getDetailBoard(boardId, userDetails);

        return new ResponseDto(200L, "성공", detailBoardDto);
    }

    @DeleteMapping("/detail/delete")
    public ResponseDto deleteDetailBoard(@RequestParam Long boardId,
                                         @AuthenticationPrincipal UserDetails userDetails) throws StarProjectException {
        boardService.deleteBoard(boardId, userDetails);

        return new ResponseDto(200L, "성공", null);
    }

    @PostMapping("/board")
    public ResponseDto createBoard(@RequestBody BoardDto boardDto,
                                   @AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception {

        User user = userDetails.getUser();
        Board createBoard = boardService.createBoard(boardDto, user);

        return new ResponseDto(200L,"성공",boardDto);
    }

    @PutMapping("/board/update")
    public ResponseDto updateBoard(@RequestParam Long boardId,
                                   @RequestBody BoardDto boardDto,
                                   @AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception{

        Board updateBoard = boardService.updateBoard(boardId, boardDto, userDetails);
        return new ResponseDto(200L,"성공",updateBoard);
    }

    @GetMapping("/board/map/list")
    public ResponseDto getMapList(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                  @RequestParam(defaultValue = "default") String cityName,
                                  @RequestParam(defaultValue = "0")Double x_location,
                                  @RequestParam(defaultValue = "0")Double y_location
    ){
        List<MapBoardDto> mapBoardDto = boardService.getBoardMapList(cityName, userDetails, x_location, y_location);

        return new ResponseDto(200L, "성공", mapBoardDto);
    }


    @GetMapping("/board/keyword")
    public ResponseDto getKeyword(@RequestParam String cityName){
        List<KeywordDto> keywordDtoList = boardService.getKeyword(cityName);
        return new ResponseDto(200L, "성공", keywordDtoList);
    }

    //좋아요
    @PostMapping("/board/like")
    public LikeResponseDto LikeInfo(@RequestParam Long cardId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        LikeResponseDto likeResponseDto = likeService.LikeInfo(cardId, userDetails.getUser());

        return likeResponseDto;
    }

}
