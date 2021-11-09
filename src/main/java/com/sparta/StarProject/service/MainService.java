package com.sparta.StarProject.service;

import com.sparta.StarProject.domain.Location;
import com.sparta.StarProject.domain.Star;
import com.sparta.StarProject.domain.board.Board;
import com.sparta.StarProject.dto.MainDto;
import com.sparta.StarProject.repository.StarRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MainService {

    private final StarRepository starRepository;

    public MainService(StarRepository starRepository) {
        this.starRepository = starRepository;
    }

    public List<MainDto> mainList() {
        List<MainDto> mainDto = new ArrayList<>();
        List<Star> mainView = starRepository.findTop3ByOrderByStarGazingDesc();

        for (Star star : mainView) {
            Location location = star.getLocation();
            List<Board> boardList = location.getBoard();
            for (Board board : boardList) {
                MainDto mainDtos = new MainDto(
                        board.getTitle(),
                        board.getAddress(),
                        star.getStarGazing(),
                        board.getImg()
                );
                mainDto.add(mainDtos);
            }
        }
        return mainDto;


    }
}
