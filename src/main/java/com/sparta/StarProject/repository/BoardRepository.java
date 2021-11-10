package com.sparta.StarProject.repository;

import com.sparta.StarProject.domain.board.Board;
import com.sparta.StarProject.dto.BoardDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    @Query("SELECT e " +
            "FROM Board e " +
            "GROUP BY e.like " +
            "ORDER BY COUNT(e.like) DESC")
    public List<Board> findBoardDto();
    List<BoardDto> findByOrderByAddressDesc();
    List<Board> findByAddressStartingWith(String key);

}
