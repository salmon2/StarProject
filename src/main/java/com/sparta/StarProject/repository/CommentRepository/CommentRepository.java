package com.sparta.StarProject.repository.CommentRepository;

import com.sparta.StarProject.domain.Comment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    Optional<Comment> findById(Long id);
    List<Comment> findAllByIdOrderByCreatedAtDesc(Long CommentId);
}
