package com.sparta.StarProject.service;

import com.sparta.StarProject.domain.Location;
import com.sparta.StarProject.domain.Star;
import com.sparta.StarProject.domain.User;
import com.sparta.StarProject.domain.board.Board;
import com.sparta.StarProject.domain.board.Camping;
import com.sparta.StarProject.domain.board.Timestamped;
import com.sparta.StarProject.domain.board.UserMake;
import com.sparta.StarProject.dto.BoardDto;
import com.sparta.StarProject.dto.CommunityDto;
import com.sparta.StarProject.dto.DetailBoardDto;
import com.sparta.StarProject.repository.BoardRepository;
import com.sparta.StarProject.repository.CampingRepository;
import com.sparta.StarProject.repository.StarRepository;
import com.sparta.StarProject.repository.UserMakeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final CampingRepository campingRepository;
    private final UserMakeRepository userMakeRepository;
    private final StarRepository starRepository;


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



        return null;
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

    public List<CommunityDto> getBoardList() {
        List<CommunityDto>  communityDtoList = new ArrayList<>();
        List<Star> starList = starRepository.findAllByOrderByStarGazingDesc();


        for (Star star : starList) {
            Location location = star.getLocation();
            Board board = null;

            CommunityDto communityDto = new CommunityDto(
                    board.getId(),
                    board.getUser().getNickname(),
                    board.getLocationName(),
                    location.getCityName(),
                    board.getImg(),
                    3L,
                    board.getContent(),
                    Timestamped.TimeToString(board.getModifiedAt())
            );

            communityDtoList.add(communityDto);
        }
        return communityDtoList;
    }
    @Transactional
    public Board createBoard(BoardDto boardDto, User user){
        Board saveBoard = new Board(
                boardDto.getAddress(),
                boardDto.getLocationName(),
                boardDto.getImg(),
                boardDto.getContent(),
                user
        );

        Board createBoard = boardRepository.save(saveBoard);
        return createBoard;

    }
}
