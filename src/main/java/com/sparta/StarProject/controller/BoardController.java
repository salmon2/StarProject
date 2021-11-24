package com.sparta.StarProject.controller;

import com.sparta.StarProject.domain.User;
import com.sparta.StarProject.domain.board.Board;
import com.sparta.StarProject.domain.board.Camping;
import com.sparta.StarProject.dto.*;
import com.sparta.StarProject.exception.StarProjectException;
import com.sparta.StarProject.repository.CampingRepository;
import com.sparta.StarProject.repository.StarInfoRepository;
import com.sparta.StarProject.repository.UserMakeRepository;
import com.sparta.StarProject.security.UserDetailsImpl;
import com.sparta.StarProject.service.BoardService;
import com.sparta.StarProject.service.LikeService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;


@RestController
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final LikeService likeService;
    private final UserMakeRepository userMakeRepository;
    private final StarInfoRepository starInfoRepository;
    private final CampingRepository campingRepository;

    @GetMapping("/test12")
    @Transactional
    public void addUser(){
        List<Camping> all = campingRepository.findAll();

        for (Camping camping : all) {
            String url = "https://stella-image-storage.s3.ap-northeast-2.amazonaws.com/camping" +
                    camping.getId() +
                    ".png";
            camping.setImg(url);
            camping.setContent("<p><img src=" +
                    url +
                    "></p>" +
                    "<p></p>"+
                    "<p>*운영진이 제공하는 정보입니다.</p>");
        }
    }



    @GetMapping("/community/list")
    public ResponseDto getBoard(@RequestParam(defaultValue = "star") String sort,
                                @RequestParam(defaultValue = "all") String cityName,
                                @AuthenticationPrincipal UserDetailsImpl userDetails,
                                @RequestParam(defaultValue = "1",required = false)int offset){
        Page<CommunityDtoCustom> communityDtoList;

        if(userDetails == null){
            communityDtoList = boardService.getBoardListNoneUser(sort, cityName, offset-1);
        }
        else{
            communityDtoList = boardService.getBoardListExistUser(sort,cityName, userDetails, offset-1);
        }

        PageResponseDto pageResponseDto = new PageResponseDto(communityDtoList.getNumber()+1,
                communityDtoList.getTotalPages(), communityDtoList.getContent().size(), communityDtoList.getContent());

        return new ResponseDto(200L, "성공", pageResponseDto);
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
    public ResponseDto getMapList(
                                  @RequestParam(defaultValue = "default", required = false) String cityName,
                                  @RequestParam(defaultValue = "0", required = false)Double x_location,
                                  @RequestParam(defaultValue = "0", required = false)Double y_location,
                                  @AuthenticationPrincipal UserDetailsImpl userDetails,
                                  @RequestParam(defaultValue = "1", required = false) int offset){
        Page<MapBoardDto> mapBoardDto;

        if(userDetails == null){
           mapBoardDto = boardService.getBoardMapListNoneUser(cityName, x_location, y_location, offset-1);
        }
        else{
            mapBoardDto = boardService.getBoardMapListExistUser(cityName, userDetails, x_location, y_location, offset-1);
        }

        long boardCount = boardService.getBoardCount();

        PageResponseDto pageResponseDto = new PageResponseDto(mapBoardDto.getNumber()+1,
                mapBoardDto.getTotalPages(),  Long.valueOf(boardCount).intValue(), mapBoardDto.getContent());

        return new ResponseDto(200L, "성공", pageResponseDto);
    }

    @GetMapping("/board/map/search")
    public ResponseDto boardMapSearch(@RequestParam Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        MapBoardDto mapBoardDto = null;
        if(userDetails == null)
            mapBoardDto = boardService.boardMapSearchByIdNoneUser(id);
        else
            mapBoardDto = boardService.boardMapSearchByIdExistUser(id, userDetails);
        return new ResponseDto(200L, "성공", mapBoardDto);
    }


    @GetMapping("/board/keyword")
    public ResponseDto getKeyword(@RequestParam String cityName){
        List<KeywordDto> keywordDtoList = boardService.getKeyword(cityName);
        return new ResponseDto(200L, "성공", keywordDtoList);
    }

    //좋아요
    @PostMapping("/board/like")
    public ResponseDto LikeInfo(@RequestParam Long cardId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        LikeResponseDto likeResponseDto = likeService.LikeInfo(cardId, userDetails.getUser());

        return new ResponseDto(200L, "성공", likeResponseDto);
    }

    //주소
    @GetMapping("/address/check")
    public ResponseDto addressCheck(@RequestParam String address) throws Exception {
        GeographicDto geographicDto = boardService.addressCheck(address);

        return new ResponseDto(200L, "성공", geographicDto);
    }

}
