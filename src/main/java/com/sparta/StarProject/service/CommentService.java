package com.sparta.StarProject.service;

import com.sparta.StarProject.domain.Comment;
import com.sparta.StarProject.domain.User;
import com.sparta.StarProject.dto.CommentRequestDto;
import com.sparta.StarProject.exception.ErrorCode;
import com.sparta.StarProject.exception.StarProjectException;
import com.sparta.StarProject.repository.CommentRepository.CommentRepository;
import com.sparta.StarProject.repository.boardRepository.BoardRepository;
import com.sparta.StarProject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public List<Comment> getComment(Long CommentId, UserDetailsImpl userDetails){
        return commentRepository.findAllByIdOrderByCreatedAtDesc(CommentId);
    }

    @Transactional
    public Comment saveComment(CommentRequestDto commentRequestDto, User user) throws Exception{
        if(user == null){
            throw new StarProjectException(ErrorCode.USER_NOT_FOUND);
        }
        Comment saveComment = new Comment(
                commentRequestDto.getComment(),
                user.getNickname()
        );
        Comment createComment = commentRepository.save(saveComment);
        return createComment;
    }

}
