package com.sparta.StarProject.controller;

import com.sparta.StarProject.domain.Comment;
import com.sparta.StarProject.dto.CommentRequestDto;
import com.sparta.StarProject.dto.CommentResponseDto;
import com.sparta.StarProject.dto.ResponseDto;
import com.sparta.StarProject.exception.StarProjectException;
import com.sparta.StarProject.security.UserDetailsImpl;
import com.sparta.StarProject.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/comment")
    public ResponseDto getComment(@RequestParam Long boardId) {

        List<CommentResponseDto> commentResponseDtoList = commentService.getComment(boardId);

        return new ResponseDto(200L,"성공",commentResponseDtoList);
    }


    @PostMapping("/comment")
    public ResponseDto createComment(@RequestBody CommentRequestDto commentRequestDto
            , @AuthenticationPrincipal UserDetailsImpl userDetails )throws Exception{

        Comment createComment = commentService.saveComment(commentRequestDto,userDetails);

        return new ResponseDto(200L,"성공",createComment);
    }

    @PutMapping("/comment/update")
    public ResponseDto commentUpdate(@RequestParam Long id, @RequestBody CommentRequestDto commentRequestDto) throws StarProjectException {
        Comment putComment = commentService.updateComment(id,commentRequestDto);

        return new ResponseDto(200L,"성공",putComment);
    }

    @DeleteMapping("/comment/delete")
    public ResponseDto deleteComment(@RequestParam(value = "id")Long id){
       commentService.deleteComment(id);

       return new ResponseDto(200L,"성공",null);
    }
}
