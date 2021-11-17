package com.sparta.StarProject.service;

import com.sparta.StarProject.api.API;
import com.sparta.StarProject.api.locationAPI.AddressToGps;
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

import com.sparta.StarProject.repository.BoardRepository;
import com.sparta.StarProject.repository.StarRepository;

import com.sparta.StarProject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


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
        if(address.getLatitude().equals("")){
            throw new NotFoundGps(ErrorCode.NotFoundGps.getMessage());
        }


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
    public Board updateBoard(Long id, BoardDto boardDto,UserDetailsImpl userDetails)throws Exception{
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new NullPointerException("해당하는 게시글이 존재하지 않습니다.")
        );
        if(board.getUser().getUsername().equals(userDetails.getUsername())){
            boardRepository.deleteById(id);
        }
        else{
            throw new StarProjectException(ErrorCode.User_Forbidden);
        }
        board.update(boardDto);
        return board;
    }

    //검색
    public List<MapBoardDto> getBoardMapList(String cityName) {
        try {
            List<MapBoardDto> mapBoardDtoArrayList = new ArrayList<>();
            List<Board> boardList = boardRepository.findByAddressContaining(cityName);

            for (Board board : boardList) {
                Star star = board.getLocation().getStar();
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
                mapBoardDtoArrayList.add(mapBoardDto);
            }

            return mapBoardDtoArrayList;
        }
        catch(NullPointerException nullPointerException){
            throw new NullPointerException("해당하는 게시글이 존재하지 않습니다.");
        }

    }
    //자동완성
    public List<KeywordDto> getKeyword(String cityName){
        List<KeywordDto> keywordDtoList = new ArrayList<>();
        List<Location> locationList = locationRepository.findByCityNameContaining(cityName);

        for (Location location : locationList) {
            KeywordDto keywordDto = new KeywordDto(
                    location.getCityName()
            );
            keywordDtoList.add(keywordDto);
        }
        return keywordDtoList;
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
//    public boolean LikeInfo(Long boardId, User user) throws Exception{
//        Board findboard = boardRepository.findById(boardId).orElseThrow(
//                () -> new StarProjectException(ErrorCode.BOARD_NOT_FOUND)
//        );
//        Optional<Like> like = likeRepository.findByBoardIdAndUserId(boardId,user.getId());
//
//        if(like.isPresent()){
//            likeRepository.delete(like.get());
//            return false;
//        }
//        else {
//            Like addLike = new Like(findboard, user);
//            likeRepository.save(addLike);
//            return true;
//        }
//    }
//    public boolean likeInfo(Long boardId, User user) throws Exception{
//        Board findBoard = boardRepository.findById(boardId).orElseThrow(
//                () -> new StarProjectException(ErrorCode.BOARD_NOT_FOUND)
//        );
//        List<Like> findDuplicateLike = likeRepository.findAllByBoardAndUser(findBoard, user);
//
//        if(findDuplicateLike.size() != 0){
//            Like findLike = findDuplicateLike.get(0);
//            likeRepository.delete(findLike);
//
//            return false;
//        }
//        else{
//            Like newLike = new Like(findBoard, user);
//            Like saveLike = likeRepository.save(newLike);
//
//            return true;
//        }
//    }

//    @Transactional
//    public boolean LikeInfo(User user, Long boardId) throws Exception{
//        Board board = boardRepository.findById(boardId).orElseThrow(
//                () -> new StarProjectException(ErrorCode.BOARD_NOT_FOUND)
//        );
//
//        if(LikeCheck(user,board)){
//            likeRepository.save(new Like(board,user));
//            return true;
//        }
//
//            return false;
//    }
//
//    private boolean LikeCheck(User user, Board board){
//        return likeRepository.findAllByBoardAndUser(board, user).isEmpty();
//    }


}
