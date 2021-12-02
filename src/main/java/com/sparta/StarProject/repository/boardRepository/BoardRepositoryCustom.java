package com.sparta.StarProject.repository.boardRepository;


import com.sparta.StarProject.domain.User;
import com.sparta.StarProject.dto.*;
import com.sparta.StarProject.security.UserDetailsImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface BoardRepositoryCustom {
    Page<MapBoardDto> findAllBoardDtoList(UserDetailsImpl userDetails, String cityName, Double x_location, Double y_location, PageRequest pageRequest);
    Page<MyBoardDto> findAllByUserCustom(User user, PageRequest pageRequest);
    Page<MyBookmarkListDto> findAllBookmarkByUserCustom(User user, PageRequest pageRequest);
    DetailBoardDto findDetailBoardByBoard(Long id, UserDetailsImpl userDetails);
    Page<CommunityDtoCustom> findCommunityList(UserDetailsImpl userDetails, PageRequest page, String cityName, String sort);
    MapBoardDto findboardMapSearchById(Long id, UserDetailsImpl userDetails);
    List<MainDto> findMainList(UserDetailsImpl userDetails);
}
