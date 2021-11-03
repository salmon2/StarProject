package com.sparta.StarProject.service;

import com.sparta.StarProject.domain.board.Board;
import com.sparta.StarProject.domain.board.Camping;
import com.sparta.StarProject.domain.board.UserMake;
import com.sparta.StarProject.dto.DetailBoardDto;
import com.sparta.StarProject.repository.BoardRepository;
import com.sparta.StarProject.repository.CampingRepository;
import com.sparta.StarProject.repository.UserMakeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final CampingRepository campingRepository;
    private final UserMakeRepository userMakeRepository;


    public DetailBoardDto getDetailBoard(Long id) {
        Board findBoard = boardRepository.findById(id).orElseThrow(
                () -> new NullPointerException("존재하는 게시글이 없습니다.")
        );

        if (findBoard instanceof Camping){
            findBoard = (Camping)findBoard;
        }
        else if (findBoard instanceof UserMake){
            findBoard = (UserMake)findBoard;
        }

        DetailBoardDto newDetailBoardDto = new DetailBoardDto(findBoard.getId(), findBoard.getUser().getNickname(),
                findBoard.getTitle(), findBoard.getLocation().getAddress(), findBoard.getImg(),
                findBoard.getContent(), findBoard.getLocation().getLongitude(), findBoard.getLocation().getLatitude());


        return newDetailBoardDto;
    }

    public int deleteBoard(Long boardId, UserDetails userDetails) {
        Board findBoard = boardRepository.findById(boardId).orElseThrow(
                () -> new NullPointerException("해당하는 게시글이 존재하지 않습니다.")
        );

        if(findBoard.getUser().getUsername().equals(userDetails.getUsername())){
            boardRepository.deleteById(boardId);
            return 1;
        }

        return 0;
    }
}
