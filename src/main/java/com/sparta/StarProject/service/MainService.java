package com.sparta.StarProject.service;

import com.sparta.StarProject.domain.Bookmark;
import com.sparta.StarProject.domain.Location;
import com.sparta.StarProject.domain.Star;
import com.sparta.StarProject.domain.board.Board;
import com.sparta.StarProject.dto.MainDto;
import com.sparta.StarProject.repository.bookmarkRepository.BookmarkRepository;
import com.sparta.StarProject.repository.StarRepository;
import com.sparta.StarProject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MainService {

    private final StarRepository starRepository;
    private final BookmarkRepository bookmarkRepository;

    public List<MainDto> mainList(UserDetailsImpl userDetails) {
        List<MainDto> mainDto = new ArrayList<>();
        if(userDetails == null){
            userDetails = new UserDetailsImpl(null);
        }
        try {
            List<Star> mainView = starRepository.findAllByOrderByStarGazingDesc();
            int count = 0;

            for (Star star : mainView) {
                Location location = star.getLocation();
                List<Board> boardList = location.getBoard();
                for (Board board : boardList) {
                    if (count > 2) {
                        return mainDto;
                    }
                    MainDto mainDtos = new MainDto(
                            board.getId(),
                            board.getTitle(),
                            board.getAddress(),
                            board.getContent(),
                            star.getStarGazing(),
                            board.getImg(),
                            bookmarkCheck(userDetails, board)
                    );
                    mainDto.add(mainDtos);
                    count++;
                }
            }
        }
        catch(NullPointerException nullPointerException){
            throw new NullPointerException("해당하는 게시글이 존재하지 않습니다.");
        }

        return mainDto;
    }

    private Boolean bookmarkCheck(UserDetailsImpl userDetails, Board findBoard) {
        Boolean likeCheck;
        if(userDetails.getUser() == null)
            return false;
        List<Bookmark> allByBoardAndUser = bookmarkRepository.findAllByBoardAndUser(findBoard, userDetails.getUser());
        if(allByBoardAndUser.size() != 0){
            likeCheck = true;
        }
        else{
            likeCheck = false;
        }
        return likeCheck;
    }
}
