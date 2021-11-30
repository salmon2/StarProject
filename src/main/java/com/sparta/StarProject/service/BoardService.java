package com.sparta.StarProject.service;

import com.sparta.StarProject.api.API;
import com.sparta.StarProject.api.locationAPI.AddressToGps;
import com.sparta.StarProject.api.locationAPI.GpsToAddress;
import com.sparta.StarProject.domain.*;
import com.sparta.StarProject.domain.board.Board;
import com.sparta.StarProject.domain.board.Camping;
import com.sparta.StarProject.domain.board.Timestamped;
import com.sparta.StarProject.domain.board.UserMake;
import com.sparta.StarProject.dto.*;

import com.sparta.StarProject.exception.ErrorCode;
import com.sparta.StarProject.exception.NotFoundGps;
import com.sparta.StarProject.exception.StarProjectException;
import com.sparta.StarProject.repository.*;

import com.sparta.StarProject.repository.boardRepository.BoardRepository;

import com.sparta.StarProject.repository.bookmarkRepository.BookmarkRepository;
import com.sparta.StarProject.repository.weatherRepository.WeatherRepository;
import com.sparta.StarProject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {

    private final BoardRepository boardRepository;
    private final LocationRepository locationRepository;
    private final AddressToGps addressToGps;
    private final LikeRepository likeRepository;
    private final BookmarkRepository bookmarkRepository;
    private final API api;
    private final GpsToAddress gpsToAddress;
    private final WeatherRepository weatherRepository;


    public Page<CommunityDtoCustom> getBoardList(UserDetailsImpl userDetails, String sort, String cityName, int offset) {
        PageRequest page = PageRequest.of(offset, 12);

        return boardRepository.findCommunityList(userDetails, page, cityName, sort);
    }


    public DetailBoardDto getDetailBoard(Long id, UserDetailsImpl userDetails) {
        List<DetailWeatherList> detailWeatherLists = weatherRepository.findDetailWeatherListByBoardId(id);

        DetailWeatherCityInfoDto newDetailBoardDto = weatherRepository.findDetailWeatherCityInfoByBoardId(id);
        newDetailBoardDto.setWeatherList(detailWeatherLists);

        DetailBoardDto detailBoardDto = boardRepository.findDetailBoardByBoard(id, userDetails);
        detailBoardDto.setWeather(newDetailBoardDto);

        return detailBoardDto;
    }



    public void deleteBoard(Long boardId, UserDetails userDetails) throws StarProjectException {
        Board findBoard = boardRepository.findById(boardId).orElseThrow(
                () -> new NullPointerException("해당하는 게시글이 존재하지 않습니다.")
        );

        if(findBoard.getUser().getUsername().equals(userDetails.getUsername())){
            boardRepository.deleteById(boardId);
        }
        else{
            throw new StarProjectException(ErrorCode.User_Forbidden);
        }

    }


    //게시글 생성
    @Transactional
    public Board createBoard(BoardDto boardDto, User user) throws Exception {
        GeographicDto gps = addressToGps.getAddress(boardDto.getAddress());

        String address = gpsToAddress.getAddress(Double.valueOf(gps.getY_location()), Double.valueOf(gps.getX_location()));
        List<String> strings = api.processAddress(address); //0번이 도시이름, 1번이 행정구역명(예: 경상북도)
        Location findLocation = locationRepository.findByCityName(strings.get(0));

        if(gps.getY_location().equals("")){
            throw new NotFoundGps(ErrorCode.NotFoundGps.getMessage());
        }

        Board saveBoard = new UserMake(
                boardDto.getTitle(),        //title
                boardDto.getAddress(),      //address
                boardDto.getContent(),
                boardDto.getImg(),
                Double.valueOf(gps.getX_location()),
                Double.valueOf(gps.getY_location()),
                user,
                findLocation,
                "userMake",
                "userMake"
        );


        Board createBoard = boardRepository.save(saveBoard);


        return createBoard;
    }


    //게시글 수정
    @Transactional
    public Board updateBoard(Long id, UpdateBoardDto boardDto,UserDetailsImpl userDetails)throws Exception{
        GeographicDto gps = addressToGps.getAddress(boardDto.getAddress());
        String address = gpsToAddress.getAddress(Double.valueOf(gps.getY_location()), Double.valueOf(gps.getX_location()));


        List<String> strings = api.processAddress(address); //0번이 도시이름, 1번이 행정구역명(예: 경상북도)
        Location findLocation = locationRepository.findByCityName(strings.get(0));


        if(findLocation == null){
            throw new NullPointerException(ErrorCode.NOtFoundLocation.getMessage());
        }

        if(gps.getY_location().equals("")){
            throw new NotFoundGps(ErrorCode.NotFoundGps.getMessage());
        }

        Board board = boardRepository.findById(id).orElseThrow(
                () -> new NullPointerException("해당하는 게시글이 존재하지 않습니다.")
        );


        if(board.getUser().getUsername().equals(userDetails.getUsername())){
            board.update(boardDto, gps, findLocation);
        }
        else{
            throw new StarProjectException(ErrorCode.User_Forbidden);
        }
        return board;
    }



    public Page<MapBoardDto> getBoardMapList(UserDetailsImpl userDetails, String cityName, Double x_location, Double y_location, int offset) {
        Page<MapBoardDto> mapBoardDtoPage = null;
        PageRequest pageRequest = PageRequest.of(offset, 15);

        mapBoardDtoPage = boardRepository.findAllBoardDtoList(userDetails, cityName, x_location, y_location, pageRequest);

        return mapBoardDtoPage;
    }

    public List<KeywordDto> getKeyword(String cityName){
        List<KeywordDto> keywordDtoList = new ArrayList<>();
        List<Location> locationList = locationRepository.findByCityNameContaining(cityName);

        for (Location location : locationList) {
            KeywordDto keywordDto = new KeywordDto(location.getCityName());
            keywordDtoList.add(keywordDto);
        }

        return keywordDtoList;
    }

    public GeographicDto addressCheck(String address) throws Exception {
        AddressToGps addressToGps = new AddressToGps();
        GeographicDto geographicDto = addressToGps.getAddress(address);
        if(geographicDto.getX_location().equals(""))
            throw new NotFoundGps(ErrorCode.NotFoundGps.getMessage());


        return geographicDto;
    }

    public long getBoardCount() {

        return boardRepository.count();
    }


    public MapBoardDto boardMapSearchByIdExistUser(Long id, UserDetailsImpl userDetails) {
        MapBoardDto mapBoardDto = boardRepository.findboardMapSearchById(id, userDetails);

        return mapBoardDto;
    }
}
