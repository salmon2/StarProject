package com.sparta.StarProject.service;

import com.sparta.StarProject.domain.Comment;
import com.sparta.StarProject.domain.User;
import com.sparta.StarProject.domain.board.Board;
import com.sparta.StarProject.dto.CommentRequestDto;
import com.sparta.StarProject.dto.CommentResponseDto;
import com.sparta.StarProject.exception.ErrorCode;
import com.sparta.StarProject.exception.StarProjectException;
import com.sparta.StarProject.repository.CommentRepository.CommentRepository;
import com.sparta.StarProject.repository.boardRepository.BoardRepository;
import com.sparta.StarProject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    public List<CommentResponseDto> getComment(Long boardId){
        return commentRepository.findAllByIdOrderByCreatedAtDesc(boardId);
    }

    @Transactional
    public Comment saveComment(CommentRequestDto commentRequestDto, UserDetailsImpl userDetails) throws Exception{
        Board findBoard = boardRepository.findById(commentRequestDto.getBoardId()).orElseThrow(
                () -> new StarProjectException(ErrorCode.BOARD_NOT_FOUND)
        );
        if(userDetails == null){
            throw new StarProjectException(ErrorCode.USER_NOT_FOUND);
        }
        Comment newComment = new Comment(
                commentRequestDto.getComment(),
                findBoard,
                userDetails.getUser());

        Comment createComment = commentRepository.save(newComment);
        return createComment;
    }

    @Transactional
    public Comment updateComment(Long id, CommentRequestDto commentRequestDto) throws StarProjectException{
        Comment findComment = commentRepository.findById(id).orElseThrow(
                () -> new StarProjectException(ErrorCode.COMMENT_NOT_FOUND)
        );
        findComment.update(commentRequestDto);
        return findComment;
    }

    public void deleteComment(Long id) {
       commentRepository.findById(id);
    }
}
