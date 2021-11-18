package com.sparta.StarProject.repository;

import com.sparta.StarProject.domain.Like;
import com.sparta.StarProject.domain.User;
import com.sparta.StarProject.domain.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface LikeRepository extends JpaRepository<Like,Long> {

    List<Like> findAllByBoardAndUser(Board board, User user);
    List<Like> findAllByBoard(Board board);
    List<Like> findAllByBoardIdAndUser(Long boardId, User user);


}
