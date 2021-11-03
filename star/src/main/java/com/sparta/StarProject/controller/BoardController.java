//package com.sparta.StarProject.controller;
//
//import com.sparta.StarProject.domain.User;
//import com.sparta.StarProject.domain.board.Board;
//import com.sparta.StarProject.dto.BoardRequestDto;
//import com.sparta.StarProject.dto.ResponseDto;
//import com.sparta.StarProject.repository.BoardRepository;
//import com.sparta.StarProject.repository.StarRepository;
//import com.sparta.StarProject.security.UserDetailsImpl;
//import com.sparta.StarProject.service.BoardService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RequiredArgsConstructor
//@RestController
//public class BoardController {
//
//    private final BoardService boardService;
//    private final BoardRepository boardRepository;
//    private final StarRepository starRepository;
//
//    @GetMapping("/community/list")
//    public List<Board> getBoard(){
//        return boardRepository.findAllByOrderByIdDesc();
//    }
//
//    @PostMapping("/board")
//    public ResponseDto saveBoard(@RequestBody @ModelAttribute BoardRequestDto boardRequestDto,
//                                 @AuthenticationPrincipal UserDetailsImpl userDetails){
//
//        User user = userDetails.getUser();
//        Board save = boardRepository.save(boardRequestDto,user);
//
//        ResponseDto responseDto = new ResponseDto(200L,"성공",data);
//    }
//}
//
