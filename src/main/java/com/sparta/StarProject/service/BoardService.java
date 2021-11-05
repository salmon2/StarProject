package com.sparta.StarProject.service;

import com.sparta.StarProject.api.API;
import com.sparta.StarProject.api.locationAPI.AddressToGps;
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

import com.sparta.StarProject.dto.GeographicDto;
import com.sparta.StarProject.exception.ErrorCode;
import com.sparta.StarProject.exception.StarProjectException;
import com.sparta.StarProject.repository.*;

import com.sparta.StarProject.dto.MapBoardDto;
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
    private final API api;
    private final LocationRepository locationRepository;
    private final AddressToGps addressToGps;


    public DetailBoardDto getDetailBoard(Long id) {
        Board findBoard = boardRepository.findById(id).orElseThrow(
                () -> new NullPointerException("존재하는 게시글이 없습니다.")
        );

        findBoard = getCampingOrUserMake(findBoard);

        DetailBoardDto detailBoardDto = new DetailBoardDto(
                findBoard.getId(),
                findBoard.getUser().getNickname(),
                findBoard.getTitle(),
                findBoard.getAddress(),
                findBoard.getImg(),
                findBoard.getContent(),
                findBoard.getLongitude(),   //경도
                findBoard.getLatitude()     //위도
        );


        return detailBoardDto;
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
            List<Board> boardList = location.getBoard();
            for (Board board : boardList) {
                CommunityDto communityDto = new CommunityDto(
                        board.getId(),
                        board.getUser().getNickname(),
                        board.getTitle(),
                        location.getCityName(),
                        board.getImg(),
                        3L,
                        board.getContent(),
                        Timestamped.TimeToString(board.getModifiedAt())
                );
                communityDtoList.add(communityDto);
            }
        }
        return communityDtoList;
    }

    //게시글 생성
    @Transactional
    public Board createBoard(BoardDto boardDto, User user) throws Exception {
        List<String> strings = api.processAddress(boardDto.getAddress()); //0번이 도시이름, 1번이 행정구역명(예: 경상북도)
        Location findLocation = locationRepository.findByCityName(strings.get(0));
        GeographicDto address = addressToGps.getAddress(boardDto.getAddress());


        Board saveBoard = new UserMake(
                boardDto.getLocationName(), //title
                boardDto.getAddress(),      //address
                boardDto.getImg(),
                boardDto.getContent(),
                Double.valueOf(address.getLongitude()),
                Double.valueOf(address.getLatitude()),
                user,
                findLocation,
                "유저가 만듬"
        );

        Board createBoard = boardRepository.save(saveBoard);
        return createBoard;
    }


    //게시글 수정
    @Transactional
    public Board updateBoard(Long id, BoardDto boardDto){
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new StarProjectException(ErrorCode.BOARD_NOT_FOUND)
        );
        board.update(boardDto);
        return board;
    }


    public List<MapBoardDto> getBoardMapList() {
        List<MapBoardDto> mapBoardDtoList = new ArrayList<>();
        List<Star> starList = starRepository.findAllByOrderByStarGazingDesc();

        for (Star star : starList) {
            Location location = star.getLocation();
            List<Board> boardList = location.getBoard();
            for (Board board : boardList) {
               board = getCampingOrUserMake(board);

                MapBoardDto mapBoardDto = new MapBoardDto(
                        board.getId(),
                        getTypeToString(board),
                        board.getLongitude(),
                        board.getLatitude(),
                        board.getAddress(),
                        star.getStarGazing(),
                        board.getImg()
                );

                mapBoardDtoList.add(mapBoardDto);
            }
        }

        return mapBoardDtoList;
    }


    private Board getCampingOrUserMake(Board board) {
        if (board instanceof Camping){
            board = (Camping) board;
        }
        else if (board instanceof UserMake){
            board = (UserMake) board;
        }
        return board;
    }

    private String getTypeToString(Board board){
        if (board instanceof Camping){
            return "Camping";
        }
        else if (board instanceof UserMake){
            return "UserMaking";
        }
        return "None Type";
    }

}
