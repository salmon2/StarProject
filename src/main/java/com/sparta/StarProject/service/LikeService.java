package com.sparta.StarProject.service;

import com.sparta.StarProject.domain.Like;
import com.sparta.StarProject.domain.User;
import com.sparta.StarProject.domain.board.Board;
import com.sparta.StarProject.dto.LikeResponseDto;
import com.sparta.StarProject.repository.boardRepository.BoardRepository;
import com.sparta.StarProject.repository.LikeRepository;
import com.sparta.StarProject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final LikeRepository likeRepository;


    @Transactional
    public LikeResponseDto LikeInfo(Long boardId, User user){
        Board findBoard = boardRepository.getById(boardId);

        List<Like> findDuplicateLike = likeRepository.findAllByBoardAndUser(findBoard, user);

        // 좋아요 삭제
        if(findDuplicateLike.size() != 0){
            Like findLike = findDuplicateLike.get(0);
            deleteLike(findLike);

            int count = likeRepository.countByBoard(findBoard) ;

            findBoard.setLikeCount(Long.valueOf(count));

            LikeResponseDto likeResponseDto = new LikeResponseDto(boardId, false, Long.valueOf(count));
            return likeResponseDto;
        }

        //좋아요 증가
        else{
            Like newLike = new Like(findBoard,user);
            saveLike(newLike);

            int count = likeRepository.countByBoard(findBoard);
            findBoard.setLikeCount(Long.valueOf(count));

            LikeResponseDto likeResponseDto = new LikeResponseDto(boardId, true, Long.valueOf(count));
            return likeResponseDto;
        }

    }

    @Transactional
    public void saveLike(Like newLike) {
        Like saveLike = likeRepository.save(newLike);
    }

    @Transactional
    public void deleteLike(Like findLike) {
        likeRepository.delete(findLike);
    }



}
