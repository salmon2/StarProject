package com.sparta.StarProject.repository;

import com.sparta.StarProject.domain.Bookmark;
import com.sparta.StarProject.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    Boolean existsByBoardAndUser(Long boardId, User user);

    void deleteByBoardAndUser(Long boardId, User user);
}
