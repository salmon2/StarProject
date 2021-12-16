package com.sparta.StarProject.controller;

import com.sparta.StarProject.domain.Comment;
import com.sparta.StarProject.domain.User;
import com.sparta.StarProject.domain.board.Board;
import com.sparta.StarProject.dto.CommentRequestDto;
import com.sparta.StarProject.dto.CommentResponseDto;
import com.sparta.StarProject.dto.ResponseDto;
import com.sparta.StarProject.security.UserDetailsImpl;
import com.sparta.StarProject.service.BoardService;
import com.sparta.StarProject.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/comment")
    public ResponseDto createComment(@RequestBody CommentRequestDto commentRequestDto
            , @AuthenticationPrincipal UserDetailsImpl userDetails )throws Exception{

        User user = userDetails.getUser();
        Comment createComment = commentService.saveComment(commentRequestDto,user);

        return new ResponseDto(200L,"성공",commentRequestDto);
    }

    @GetMapping("/comment")
    public ResponseDto readComment(@RequestParam Long CommentId,
                                   @AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<Comment> commentResponseDto = commentService.getComment(CommentId, userDetails);

        return new ResponseDto(200L,"성공",commentResponseDto);
    }
}
