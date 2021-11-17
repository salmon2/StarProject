package com.sparta.StarProject.service;

import com.sparta.StarProject.domain.Like;
import com.sparta.StarProject.domain.User;
import com.sparta.StarProject.domain.board.Board;
import com.sparta.StarProject.dto.LikeResponseDto;
import com.sparta.StarProject.repository.BoardRepository;
import com.sparta.StarProject.repository.LikeRepository;
import com.sparta.StarProject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

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
        List<Like> allByBoard = likeRepository.findAllByBoard(findBoard);
        int size = allByBoard.size();

        // 좋아요 삭제
        if(findDuplicateLike.size() != 0){
            Like findLike = findDuplicateLike.get(0);
            likeRepository.delete(findLike);
            LikeResponseDto likeResponseDto = new LikeResponseDto(boardId, false, new Long(size - 1));
            return likeResponseDto;
        }

        //좋아요 증가
        else{
            Like newLike = new Like(findBoard,user);
            Like saveLike = likeRepository.save(newLike);
            LikeResponseDto likeResponseDto = new LikeResponseDto(boardId, true, new Long(size + 1));
            return likeResponseDto;
        }

    }
}
