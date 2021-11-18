package com.sparta.StarProject.repository.boardRepository;


import com.sparta.StarProject.domain.User;
import com.sparta.StarProject.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface BoardRepositoryCustom {
    Page<CommunityDtoCustom> findAllOrderByStarCustomExistUser(User user, PageRequest pageRequest);
    Page<CommunityDtoCustom> findAllOrderByStarCustomNoneUser(PageRequest pageRequest);
    Page<CommunityDtoCustom> findAllOrderByStarCustomContainingCityExistUser(String cityName, User user, PageRequest page);
    Page<CommunityDtoCustom> findAllOrderByStarCustomContainingCityNoneUser(String cityName, PageRequest page);

    Page<CommunityDtoCustom> findAllOrderByLikeCustomExistUser(User user, PageRequest page);
    Page<CommunityDtoCustom> findAllOrderByLikeCustomNoneUser(PageRequest page);
    Page<CommunityDtoCustom> findAllOrderByLikeCustomContainingCityNoneUser(String cityName, PageRequest page);
    Page<CommunityDtoCustom> findAllOrderByLikeCustomContainingCityExistUser(String cityName, User user, PageRequest page);

    Page<MapBoardDto> findAllMapBoardDtoListCustomExistUser(User user, PageRequest page);
    Page<MapBoardDto> findAllMapBoardDtoListCustomByLocationExistUser(User user, Double x_location, Double y_location, PageRequest page);
    Page<MapBoardDto> findAllMapBoardDtoListCustomByCityNameExistUser(String cityName, User user, PageRequest pageRequest);
    Page<MapBoardDto> findAllMapBoardDtoListCustomByLocationAndCityNameExistUser(String cityName, User userDetails, Double x_location, Double y_location, PageRequest pageRequest);

    Page<MapBoardDto> findAllMapBoardDtoListCustomNoneUser(PageRequest pageRequest);
    Page<MapBoardDto> findAllMapBoardDtoListCustomByLocationNoneUser(Double x_location, Double y_location, PageRequest pageRequest);
    Page<MapBoardDto> findAllMapBoardDtoListCustomByCityNameNoneUser(String cityName, PageRequest pageRequest);
    Page<MapBoardDto> findAllMapBoardDtoListCustomByLocationAndCityNameNoneUser(String cityName, Double x_location, Double y_location, PageRequest pageRequest);

    Page<MyBoardDto> findAllByUserCustom(User user, PageRequest pageRequest);
    Page<MyBookmarkListDto> findAllBookmarkByUserCustom(User user, PageRequest pageRequest);

}
