package com.sparta.StarProject.repository;

import com.sparta.StarProject.domain.board.Board;
import com.sparta.StarProject.dto.BoardDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<BoardDto> findByOrderByAddressDesc();
}
