package com.sparta.StarProject.service;

import com.sparta.StarProject.api.API;
import com.sparta.StarProject.api.accuweatherAPI.StarGazingCity;
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

import com.sparta.StarProject.repository.boardRepository.BoardRepository;
import com.sparta.StarProject.repository.StarRepository;

import com.sparta.StarProject.repository.bookmarkRepository.BookmarkRepository;
import com.sparta.StarProject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
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
    private final LikeRepository likeRepository;
    private final BookmarkRepository bookmarkRepository;


    public DetailBoardDto getDetailBoard(Long id, UserDetailsImpl userDetails) {
        Board findBoard = boardRepository.findById(id).orElseThrow(
                () -> new NullPointerException("해당하는 게시글이 존재하지 않습니다.")
        );

        List<Like> allByBoard = likeRepository.findAllByBoard(findBoard);
        int likeSize = allByBoard.size();

        Boolean likeCheck = null;
        likeCheck = likeCHeck(userDetails, findBoard);

        Boolean bookmarkCheck = null;
        bookmarkCheck = bookmarkCheck(userDetails, findBoard);

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
                likeCheck,
                (long) likeSize,
                bookmarkCheck,
                newDetailBoardDto
        );

        return detailBoardDto;
    }

    private Boolean likeCHeck(UserDetailsImpl userDetails, Board findBoard) {
        Boolean likeCheck;
        if(userDetails == null) {
            return false;
        }

        List<Like> allByBoardAndUser = likeRepository.findAllByBoardAndUser(findBoard, userDetails.getUser());
        if(allByBoardAndUser.size() != 0){
            likeCheck = true;
        }
        else{
            likeCheck = false;
        }
        return likeCheck;
    }

    private Boolean bookmarkCheck(UserDetailsImpl userDetails, Board findBoard) {
        Boolean bookmarkCheck;
        if(userDetails == null){
            return false;
        }
        List<Bookmark> bookmarkList = bookmarkRepository.findAllByBoardAndUser(findBoard, userDetails.getUser());

        if(bookmarkList.size() != 0){
            bookmarkCheck = true;
        }
        else{
            bookmarkCheck = false;
        }

        return bookmarkCheck;
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


    public List<CommunityDto> getBoardList(String sort, String cityName, UserDetailsImpl userDetails) {
        List<CommunityDto> communityDtoList = getBoardListOrderBySortAndCityName(sort, cityName, userDetails);

        return communityDtoList;
    }

    private List<CommunityDto> getBoardListOrderBySortAndCityName(String sort, String city, UserDetailsImpl userDetails) {
        List<CommunityDto>  communityDtoList = new ArrayList<>();

        if(userDetails == null){
            userDetails = new UserDetailsImpl(null);
        }

        try{
            if(sort.equals("star")){
                List<Star> starList = starRepository.findAllByOrderByStarGazingDesc();
                for (Star star : starList) {
                    Location location = star.getLocation();
                    if(city.equals("all")){
                        addCommunityDto(communityDtoList, location, userDetails);
                    }
                    else {
                        addCommunityDtoToSearch(city, userDetails, communityDtoList, location);
                    }
                }
            }
            else if(sort.equals("like")){
                List<Location> locationList = locationRepository.findAll();
                for (Location location : locationList) {
                    if(city.equals("all")){
                        addCommunityDto(communityDtoList, location, userDetails);
                    }
                    else if(location.getCityName().equals(city)){
                        addCommunityDtoToSearch(city, userDetails, communityDtoList, location);
                    }
                }
                Collections.sort(communityDtoList);
            }
        }
        catch (NullPointerException nullPointerException){
            throw new NullPointerException("find All StarList Fail");
        }


        return communityDtoList;
    }

    private void addCommunityDtoToSearch(String city, UserDetailsImpl userDetails, List<CommunityDto> communityDtoList, Location location) {
        List<Board> boardList = location.getBoard();
        for (Board board : boardList) {
            if(board.getAddress().contains(city)){
                List<Like> allByBoard = likeRepository.findAllByBoard(board);
                int size = allByBoard.size();
                Boolean likeCheck = likeCheck(userDetails, board);

                CommunityDto communityDto = new CommunityDto(
                        board.getId(),
                        board.getUser().getNickname(),
                        board.getTitle(),
                        location.getCityName(),
                        board.getImg(),
                        board.getContent(),
                        Timestamped.TimeToString(board.getModifiedAt()),
                        (long) size,
                        likeCheck
                );
                communityDtoList.add(communityDto);}
        }
    }

    private void addCommunityDto(List<CommunityDto> communityDtoList, Location location, UserDetailsImpl userDetails) {
        List<Board> boardList = location.getBoard();
        for (Board board : boardList) {
            List<Like> allByBoard = likeRepository.findAllByBoard(board);
            int size = allByBoard.size();
            Boolean likeCheck = likeCheck(userDetails, board);

            CommunityDto communityDto = new CommunityDto(
                                        board.getId(),
                                        board.getUser().getNickname(),
                                        board.getTitle(),
                                        location.getCityName(),
                                        board.getImg(),
                                        board.getContent(),
                                        Timestamped.TimeToString(board.getModifiedAt()),
                                        (long) size,
                                        likeCheck
                );
            communityDtoList.add(communityDto);
        }
    }

    private Boolean likeCheck(UserDetailsImpl userDetails, Board board) {
        Boolean likeCheck = false;
        if(userDetails.getUser() == null){
            likeCheck = false;
        }
        else{
            List<Like> allByBoardAndUser = likeRepository.findAllByBoardAndUser(board, userDetails.getUser());
            if(allByBoardAndUser.size() != 0){
                likeCheck = true;
            }
            else{
                likeCheck = false;
            }
        }
        return likeCheck;
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
    /**
     * 수정 필요
     * @param cityName
     * @param userDetails
     * @param x_location
     * @param y_location
     * @return
     */
    public List<MapBoardDto> getBoardMapList(String cityName, UserDetailsImpl userDetails, Double x_location, Double y_location) {
            if(cityName.equals("default")){
                List<MapBoardDto> mapBoardDtoList = new ArrayList<>();
                List<Star> starList = starRepository.findAllByOrderByStarGazingDesc();

                for (Star star : starList) {
                    Location location = star.getLocation();
                    List<Board> boardList = location.getBoard();
                    for (Board board : boardList) {
                        if(x_location == 0){
                            addMapBoardDtoList(userDetails, mapBoardDtoList, star, board);
                        }
                        else{
                            Double base_y = board.getLatitude();
                            Double base_x = board.getLongitude();
                            //위경도 1 당 111km
                            if( Math.abs(base_y - y_location) < 0.4 && Math.abs(base_x - x_location) < 0.4){
                                addMapBoardDtoList(userDetails, mapBoardDtoList, star, board);
                            }
                        }
                    }
                }

                return mapBoardDtoList;
            }
            else{
                List<MapBoardDto> mapBoardDtoArrayList = new ArrayList<>();
                List<Board> boardList = boardRepository.findByAddressContaining(cityName);

                for (Board board : boardList) {
                    if(x_location == 0){
                        mapBoardDtoList2(userDetails, mapBoardDtoArrayList, board);
                    }
                    else{
                        Double base_y = board.getLatitude();
                        Double base_x = board.getLongitude();
                        //위경도 1 당 111km
                        if( Math.abs(base_y - y_location) < 0.4 && Math.abs(base_x - x_location) < 0.4){
                            mapBoardDtoList2(userDetails, mapBoardDtoArrayList, board);
                        }
                    }
                }
                return mapBoardDtoArrayList;
            }

    }

    private void mapBoardDtoList2(UserDetailsImpl userDetails, List<MapBoardDto> mapBoardDtoArrayList, Board board) {
        Boolean bookmark = null;

        if(userDetails.equals(null)){
            bookmark = false;
        }
        else {
            bookmark = bookmarkCheck(userDetails, board);
        }
        Star star = board.getLocation().getStar();
        MapBoardDto mapBoardDto = new MapBoardDto(
                board.getId(),
                getTypeToString(board),
                board.getTitle(),
                board.getLongitude(),
                board.getLatitude(),
                board.getAddress(),
                bookmark,
                star.getStarGazing(),
                board.getImg()
        );
        mapBoardDtoArrayList.add(mapBoardDto);
    }

    private void addMapBoardDtoList(UserDetailsImpl userDetails, List<MapBoardDto> mapBoardDtoList, Star star, Board board) {
        Boolean bookmark = null;

        if(userDetails == null){
            bookmark = false;
        }
        else {
            bookmark = bookmarkCheck(userDetails, board);
        }

        board = getCampingOrUserMake(board);
        MapBoardDto mapBoardDto = new MapBoardDto(
                board.getId(),
                getTypeToString(board),
                board.getTitle(),
                board.getLongitude(),
                board.getLatitude(),
                board.getAddress(),
                bookmark,
                star.getStarGazing(),
                board.getImg()
        );

        mapBoardDtoList.add(mapBoardDto);
    }


    //자동완성

    /**
     * 수정 필요
     * @param cityName
     * @return
     */
    public List<KeywordDto> getKeyword(String cityName){
        List<KeywordDto> keywordDtoList = new ArrayList<>();
        List<Location> locationList = locationRepository.findByCityNameContaining(cityName);

        for (Location location : locationList) {
            StarGazingCity starGazingCityByString = StarGazingCity.getStarGazingCityByString(location.getCityName());


            KeywordDto keywordDto = new KeywordDto(location.getCityName());
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
