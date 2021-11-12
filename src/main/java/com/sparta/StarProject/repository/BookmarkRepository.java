package com.sparta.StarProject.repository;

import com.sparta.StarProject.domain.Bookmark;
import com.sparta.StarProject.domain.User;
import com.sparta.StarProject.domain.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    Optional<Bookmark> findByUserAndBoard(User user, Board board);

    List<Bookmark> findAllByUser(User user);
}
