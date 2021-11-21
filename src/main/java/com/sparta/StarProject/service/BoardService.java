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

import com.sparta.StarProject.repository.boardRepository.BoardRepository;
import com.sparta.StarProject.repository.StarRepository;

import com.sparta.StarProject.repository.bookmarkRepository.BookmarkRepository;
import com.sparta.StarProject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    private final LocationRepository locationRepository;
    private final AddressToGps addressToGps;
    private final LikeRepository likeRepository;
    private final BookmarkRepository bookmarkRepository;
    private final API api;

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


    public Page<CommunityDtoCustom> getBoardListNoneUser(String sort, String cityName, int offset) {
        Page<CommunityDtoCustom> communityDtoList = null;
        PageRequest page = PageRequest.of(offset, 12);

        if(sort.equals("star")) {
            if(cityName.equals("all")) {
                communityDtoList = boardRepository.findAllOrderByStarCustomNoneUser(page);
            }
            else
                communityDtoList = boardRepository.findAllOrderByStarCustomContainingCityNoneUser(cityName, page);
            return communityDtoList;
        }
        else if (sort.equals("like")) {
            if(cityName.equals("all"))
                communityDtoList = boardRepository.findAllOrderByLikeCustomNoneUser(page);
            else
                communityDtoList = boardRepository.findAllOrderByLikeCustomContainingCityNoneUser(cityName, page);
            return communityDtoList;
        }
        else if (sort.equals("latest")){
            if(cityName.equals("all"))
                communityDtoList = boardRepository.findAllOrderByLatestCustomNoneUser(page);
            else
                communityDtoList = boardRepository.findAllOrderByLatestCustomContainingCityNoneUser(cityName, page);
            return communityDtoList;
        }

        return null;
    }

    public Page<CommunityDtoCustom> getBoardListExistUser(String sort, String cityName, UserDetailsImpl userDetails, int offset) {
        Page<CommunityDtoCustom> communityDtoList = null;
        PageRequest page = PageRequest.of(offset, 12);

        if(sort.equals("star")) {
            if(cityName.equals("all"))
                communityDtoList = boardRepository.findAllOrderByStarCustomExistUser(userDetails.getUser(), page);
            else
                communityDtoList = boardRepository.findAllOrderByStarCustomContainingCityExistUser(cityName, userDetails.getUser(), page);
            return communityDtoList;
        }
        else if (sort.equals("like")) {
            if(cityName.equals("all"))
                communityDtoList = boardRepository.findAllOrderByLikeCustomExistUser(userDetails.getUser(), page);
            else
                communityDtoList = boardRepository.findAllOrderByLikeCustomContainingCityExistUser(cityName, userDetails.getUser(), page);
            return communityDtoList;
        }
        else if (sort.equals("latest")){
            if(cityName.equals("all"))
                communityDtoList = boardRepository.findAllOrderByLatestCustomExistUser(userDetails.getUser(),page);
            else
                communityDtoList = boardRepository.findAllOrderByLatestCustomContainingCityExistUser(cityName, userDetails.getUser(), page);
            return communityDtoList;
        }


        return null;
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

        if(address.getY_location().equals("")){
            throw new NotFoundGps(ErrorCode.NotFoundGps.getMessage());
        }

        Board saveBoard = new UserMake(
                boardDto.getTitle(),        //title
                boardDto.getAddress(),      //address
                boardDto.getContent(),
                boardDto.getImg(),
                Double.valueOf(address.getX_location()),
                Double.valueOf(address.getY_location()),
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
     * @param offset
     * @return
     */
    public Page<MapBoardDto> getBoardMapListExistUser(String cityName, UserDetailsImpl userDetails, Double x_location, Double y_location, int offset) {
        Page<MapBoardDto> mapBoardDtoPage = null;
        PageRequest page = PageRequest.of(offset, 15);

        if(cityName.equals("default")) {
            if(x_location.equals(0.0))
                mapBoardDtoPage  = boardRepository.findAllMapBoardDtoListCustomExistUser(userDetails.getUser(), page);
            else
                mapBoardDtoPage = boardRepository.findAllMapBoardDtoListCustomByLocationExistUser(userDetails.getUser(), x_location, y_location, page);
            return mapBoardDtoPage;
        }
        else{
            if(x_location.equals(0.0))
                mapBoardDtoPage  = boardRepository.findAllMapBoardDtoListCustomByCityNameExistUser(cityName, userDetails.getUser(),page);
            else
                mapBoardDtoPage = boardRepository.findAllMapBoardDtoListCustomByLocationAndCityNameExistUser(cityName, userDetails.getUser(), x_location, y_location , page);
            return mapBoardDtoPage;
        }
    }


    public Page<MapBoardDto> getBoardMapListNoneUser(String cityName, Double x_location, Double y_location, int offset) {
        Page<MapBoardDto> mapBoardDtoPage = null;
        PageRequest pageRequest = PageRequest.of(offset, 15);

        if(cityName.equals("default")) {
            if(x_location.equals(0.0))
                mapBoardDtoPage  = boardRepository.findAllMapBoardDtoListCustomNoneUser(pageRequest);
            else
                mapBoardDtoPage = boardRepository.findAllMapBoardDtoListCustomByLocationNoneUser(x_location, y_location, pageRequest);
            return mapBoardDtoPage;
        }
        else{
            if(x_location.equals(0.0))
                mapBoardDtoPage  = boardRepository.findAllMapBoardDtoListCustomByCityNameNoneUser(cityName, pageRequest);
            else
                mapBoardDtoPage =
                        boardRepository.findAllMapBoardDtoListCustomByLocationAndCityNameNoneUser(cityName, x_location, y_location, pageRequest);
            return mapBoardDtoPage;
        }
    }

    /**
     * 수정 필요
     * @param cityName
     * @return
     */
    public List<KeywordDto> getKeyword(String cityName){
        List<KeywordDto> keywordDtoList = new ArrayList<>();
        List<Location> locationList = locationRepository.findByCityNameContaining(cityName);

        for (Location location : locationList) {
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


    public GeographicDto addressCheck(String address) throws Exception {
        AddressToGps addressToGps = new AddressToGps();
        GeographicDto geographicDto = addressToGps.getAddress(address);
        if(geographicDto.getX_location().equals(""))
            throw new NotFoundGps(ErrorCode.NotFoundGps.getMessage());


        return geographicDto;
    }



}
