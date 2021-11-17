package com.sparta.StarProject.repository;

import com.sparta.StarProject.domain.Bookmark;
import com.sparta.StarProject.domain.Like;
import com.sparta.StarProject.domain.User;
import com.sparta.StarProject.domain.board.Board;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    Boolean existsByBoardAndUser(Long boardId, User user);
    void deleteByBoardAndUser(Board board, User user);

    List<Bookmark> findAllByBoardAndUser(Board board, User user);
    List<Bookmark> findAllByUser(User user);


}
