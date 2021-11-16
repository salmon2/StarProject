package com.sparta.StarProject.service;

import com.sparta.StarProject.domain.Like;
import com.sparta.StarProject.domain.User;
import com.sparta.StarProject.domain.board.Board;
import com.sparta.StarProject.exception.ErrorCode;
import com.sparta.StarProject.exception.StarProjectException;
import com.sparta.StarProject.repository.BoardRepository;
import com.sparta.StarProject.repository.LikeRepository;
import com.sparta.StarProject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final LikeRepository likeRepository;

//    public List<LikeDto> LikeInfo(Long boardId, UserDetailsImpl userDetails) throws Exception{
//        Long id = userDetails.getUser().getId();
//        User user = userRepository.getById(id);
//
//        Board board = boardRepository.findById(boardId).orElseThrow(
//                () -> new StarProjectException(ErrorCode.BOARD_NOT_FOUND)
//        );
//
//        Like like = new Like(board,user);
//
//        board.getLike().add(like);
//        user.getLike().add(like);
//        likeRepository.save(like);
//
//        return ;
//    }

//    public boolean addLike(User user, Long boardId) throws Exception{
//        Board board = boardRepository.findById(boardId).orElseThrow(
//                () -> new StarProjectException(ErrorCode.BOARD_NOT_FOUND)
//        );
//
//        //중복 좋아요 방지
//        if (notLike(user, board)){
//            likeRepository.save(new Like(board,user));
//            return true;
//        }
//        return false;
//    }
//
//    public void deleteLike(User user, Long boardId) throws Exception{
//        Board board = boardRepository.findById(boardId).orElseThrow(
//                () -> new StarProjectException(ErrorCode.BOARD_NOT_FOUND)
//        );
//
//        //임의 예외처리
//        Like like = likeRepository.findAllByUserAndBoard(board,user).orElseThrow(
//                () -> new StarProjectException(ErrorCode.BOARD_NOT_FOUND)
//        );
//        likeRepository.delete(like);
//    }
//    public List<String> countLike(User userDetails, Long boardId)throws Exception{
//        Board board = boardRepository.findById(boardId).orElseThrow(
//                () -> new StarProjectException(ErrorCode.BOARD_NOT_FOUND)
//        );
//
//        Integer likeCount = likeRepository.countByBoard(board).orElse(0);
//
//        List<String> likeDtoList = new ArrayList<>();
//
//        if (userDetails != null){
//           likeDtoList.add(String.valueOf(notLike(userDetails, board)));
//           return likeDtoList;
//        }
//        return likeDtoList;
//    }
//    //사용자가 이미 좋아요 한 게시물인지 확인
//    private boolean notLike(User user, Board board){
//        return likeRepository.findAllByBoardAndUser(board,user).isEmpty();
//    }

    public String LikeInfo(Long boardId,User user){
        Board findBoard = boardRepository.getById(boardId);

        List<Like> findDuplicateLike = likeRepository.findAllByBoardAndUser(findBoard, user);

        if(findDuplicateLike.size() != 0){
            Like findLike = findDuplicateLike.get(0);
            likeRepository.delete(findLike);

            return "좋아요 취소";
        }
        else{
            Like newLike = new Like(findBoard,user);
            Like saveLike = likeRepository.save(newLike);

            return "좋아요";
        }
    }
}
