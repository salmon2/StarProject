package com.sparta.StarProject.service;

import com.sparta.StarProject.domain.Bookmark;
import com.sparta.StarProject.domain.User;
import com.sparta.StarProject.domain.board.Board;
import com.sparta.StarProject.dto.BookmarkDto;
import com.sparta.StarProject.dto.MyBookmarkListDto;
import com.sparta.StarProject.repository.BoardRepository;
import com.sparta.StarProject.repository.BookmarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final BoardRepository boardRepository;
    private final BookmarkRepository bookmarkRepository;


    public BookmarkDto addBookmark(Long boardId, User user) {
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("해당하는 게시글이 존재하지 않습니다.")
        );

        Boolean isExist = bookmarkRepository.existsByBoardAndUser(boardId, user);

        BookmarkDto bookmarkDto = new BookmarkDto();

        if (isExist){
            bookmarkRepository.deleteByBoardAndUser(boardId, user);
        } else {
            Bookmark bookmark = new Bookmark(user, board);
            bookmarkRepository.save(bookmark);
        }
        return bookmarkDto;
    }

    public List<MyBookmarkListDto> getMyBookMark(User user) {
        List<MyBookmarkListDto> myBookmarkListDtos = new ArrayList<>();
        List<Board> myBookmark = boardRepository.findAllByUser(user);

        for(Board board : myBookmark) {
            MyBookmarkListDto myBookmarkListDto = new MyBookmarkListDto(
                    board.getTitle(),
                    board.getContent(),
                    board.getImg()
            );
            myBookmarkListDtos.add(myBookmarkListDto);
        }
        return myBookmarkListDtos;
    }
}