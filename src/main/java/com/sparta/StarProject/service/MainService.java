package com.sparta.StarProject.service;

import com.sparta.StarProject.domain.Location;
import com.sparta.StarProject.domain.Star;
import com.sparta.StarProject.domain.board.Board;
import com.sparta.StarProject.dto.MainDto;
import com.sparta.StarProject.repository.StarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MainService {

    private final StarRepository starRepository;


    public List<MainDto> mainList() {
        List<MainDto> mainDto = new ArrayList<>();
        List<Star> mainView = starRepository.findAllByOrderByStarGazingDesc();
        int count = 0;

        for (Star star : mainView) {
            Location location = star.getLocation();
            List<Board> boardList = location.getBoard();
            for (Board board : boardList) {
                if(count >2){
                    return mainDto;
                }
                MainDto mainDtos = new MainDto(
                        board.getTitle(),
                        board.getAddress(),
                        board.getContent(),
                        star.getStarGazing(),
                        board.getImg()
                );
                mainDto.add(mainDtos);
                count++;
            }
        }


        return mainDto;
    }
}
