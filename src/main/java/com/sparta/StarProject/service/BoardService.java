package com.sparta.StarProject.service;

import com.sparta.StarProject.api.API;
import com.sparta.StarProject.api.locationAPI.AddressToGps;
import com.sparta.StarProject.domain.Location;
import com.sparta.StarProject.domain.Star;
import com.sparta.StarProject.domain.User;
import com.sparta.StarProject.domain.Weather;
import com.sparta.StarProject.domain.board.Board;
import com.sparta.StarProject.domain.board.Camping;
import com.sparta.StarProject.domain.board.Timestamped;
import com.sparta.StarProject.domain.board.UserMake;
import com.sparta.StarProject.dto.*;

import com.sparta.StarProject.exception.ErrorCode;
import com.sparta.StarProject.exception.StarProjectException;
import com.sparta.StarProject.repository.*;

import com.sparta.StarProject.repository.BoardRepository;
import com.sparta.StarProject.repository.StarRepository;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {
    private final BoardRepository boardRepository;
    private final StarRepository starRepository;
    private final API api;
    private final LocationRepository locationRepository;
    private final AddressToGps addressToGps;


    public DetailBoardDto getDetailBoard(Long id) {
        Board findBoard = boardRepository.findById(id).orElseThrow(
                () -> new NullPointerException("해당하는 게시글이 존재하지 않습니다.")
        );

        findBoard = getCampingOrUserMake(findBoard);
        List<Weather> weatherList = findBoard.getLocation().getWeatherList();
        Location findBoardLocation = findBoard.getLocation();
        Star findStar = findBoardLocation.getStar();


        List<DetailWeatherWeatherList> detailWeatherWeatherLists = new ArrayList<>();
        for (Weather weather : weatherList) {
            DetailWeatherWeatherList detailWeatherWeatherList = new DetailWeatherWeatherList(
                    weather.getPredictTime(),
                    Long.valueOf(weather.getRainPercent()),
                    weather.getWeather(),
                    Long.valueOf(weather.getHumidity()),
                    Long.valueOf(weather.getTemperature()),
                    Long.valueOf(weather.getDust())
            );
            detailWeatherWeatherLists.add(detailWeatherWeatherList);
        }

        DetailWeatherDto newDetailBoardDto = new DetailWeatherDto(
                findBoardLocation.getCityName(),
                Timestamped.getCurrentTime().get(3),
                findStar.getStarGazing(),
                findStar.getMoonrise(),
                findStar.getMoonSet(),
                detailWeatherWeatherLists
        );
        DetailBoardDto detailBoardDto = new DetailBoardDto(
                findBoard.getId(),
                findBoard.getUser().getNickname(),
                findBoard.getTitle(),
                findBoard.getAddress(),
                findBoard.getImg(),
                findBoard.getContent(),
                findBoard.getLongitude(),   //경도
                findBoard.getLatitude(),     //위도,
                newDetailBoardDto
        );

        return detailBoardDto;
    }

    public void deleteBoard(Long boardId, UserDetails userDetails) {
        Board findBoard = boardRepository.findById(boardId).orElseThrow(
                () -> new NullPointerException("해당하는 게시글이 존재하지 않습니다.")
        );

        if(findBoard.getUser().getUsername().equals(userDetails.getUsername())){
            boardRepository.deleteById(boardId);
        }

    }


    public List<CommunityDto> getBoardList(String sort, String cityName) {
        List<CommunityDto> communityDtoList =
                getBoardListOrderBySortAndCityName(sort, cityName);

        return communityDtoList;
    }

    private List<CommunityDto> getBoardListOrderBySortAndCityName(String sort, String city) {
        List<CommunityDto>  communityDtoList = new ArrayList<>();
        try{
            if(sort.equals("star")){
                List<Star> starList = starRepository.findAllByOrderByStarGazingDesc();
                for (Star star : starList) {
                    Location location = star.getLocation();
                    if(city.equals("all")){
                        addCommunityDto(communityDtoList, location);
                    }
                    else if(location.getCityName().equals(city)){
                        addCommunityDto(communityDtoList, location);
                    }
                }
            }

            else if(sort.equals("like")){
                List<Board> boardDto = boardRepository.findBoardDto();
                log.info("boardDto = {}", boardDto);
            }
        }
        catch (NullPointerException nullPointerException){
            throw new NullPointerException("find All StarList Fail");
        }


        return communityDtoList;
    }

    private void addCommunityDto(List<CommunityDto> communityDtoList, Location location) {
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


    //게시글 생성
    @Transactional
    public Board createBoard(BoardDto boardDto, User user) throws Exception {
        List<String> strings = api.processAddress(boardDto.getAddress()); //0번이 도시이름, 1번이 행정구역명(예: 경상북도)
        Location findLocation = locationRepository.findByCityName(strings.get(0));
        GeographicDto address = addressToGps.getAddress(boardDto.getAddress());


        Board saveBoard = new UserMake(
                boardDto.getTitle(),        //title
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
                () -> new NullPointerException("해당하는 게시글이 존재하지 않습니다.")
        );
        board.update(boardDto);
        return board;
    }


    public List<MapBoardDto> getBoardMapList() {
        List<MapBoardDto> mapBoardDtoList = new ArrayList<>();
        try {
            List<Star> starList = starRepository.findAllByOrderByStarGazingDesc();

            for (Star star : starList) {
                Location location = star.getLocation();
                List<Board> boardList = location.getBoard();
                for (Board board : boardList) {
                   board = getCampingOrUserMake(board);

                    MapBoardDto mapBoardDto = new MapBoardDto(
                            board.getId(),
                            getTypeToString(board),
                            board.getTitle(),
                            board.getLongitude(),
                            board.getLatitude(),
                            board.getAddress(),
                            star.getStarGazing(),
                            board.getImg()
                    );
                    mapBoardDtoList.add(mapBoardDto);
                }
            }
        }
        catch(NullPointerException nullPointerException){
            throw new NullPointerException("해당하는 게시글이 존재하지 않습니다.");
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
