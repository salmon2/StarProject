package com.sparta.StarProject.repository.CommentRepository;

import com.sparta.StarProject.domain.Comment;

import com.sparta.StarProject.domain.board.Board;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findAllByBoard(Board findBoard, Sort createdAt);
}
