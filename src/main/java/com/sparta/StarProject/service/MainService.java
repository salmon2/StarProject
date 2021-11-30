package com.sparta.StarProject.service;

import com.sparta.StarProject.domain.Bookmark;
import com.sparta.StarProject.domain.board.Board;
import com.sparta.StarProject.dto.MainDto;
import com.sparta.StarProject.repository.boardRepository.BoardRepository;
import com.sparta.StarProject.repository.bookmarkRepository.BookmarkRepository;
import com.sparta.StarProject.repository.starRepository.StarRepository;
import com.sparta.StarProject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MainService {

    private final StarRepository starRepository;
    private final BookmarkRepository bookmarkRepository;
    private final BoardRepository boardRepository;

    public List<MainDto> mainList(UserDetailsImpl userDetails) {
        try {
            List<MainDto> mainDto = boardRepository.findMainList(userDetails);
            mainDto = mainDto.subList(0,3);
            return mainDto;
        }
        catch(NullPointerException nullPointerException){
            throw new NullPointerException("해당하는 게시글이 존재하지 않습니다.");
        }

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
