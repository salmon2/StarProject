package com.sparta.StarProject.repository.boardRepository;

import com.sparta.StarProject.domain.User;
import com.sparta.StarProject.domain.board.Board;
import com.sparta.StarProject.dto.BoardDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom {
    @Query("SELECT e " +
            "FROM Board e " +
            "GROUP BY e.like " +
            "ORDER BY COUNT(e.like) DESC")
    public List<Board> findBoardDto();
    List<BoardDto> findByOrderByAddressDesc();


    List<Board> findByAddressContaining(String key);
    List<Board> findAllByUser(User user);

}
