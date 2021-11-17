package com.sparta.StarProject.service;

import com.sparta.StarProject.domain.Bookmark;
import com.sparta.StarProject.domain.Location;
import com.sparta.StarProject.domain.Star;
import com.sparta.StarProject.domain.User;
import com.sparta.StarProject.domain.board.Board;
import com.sparta.StarProject.dto.BookmarkDto;
import com.sparta.StarProject.dto.MyBookmarkListDto;
import com.sparta.StarProject.dto.BookmarkMapList;
import com.sparta.StarProject.repository.boardRepository.BoardRepository;
import com.sparta.StarProject.repository.bookmarkRepository.BookmarkRepository;
import com.sparta.StarProject.repository.StarRepository;
import com.sparta.StarProject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final BoardRepository boardRepository;
    private final BookmarkRepository bookmarkRepository;
    private final StarRepository starRepository;


    @Transactional
    public BookmarkDto addBookmark(Long boardId, User user) {
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("해당하는 게시글이 존재하지 않습니다.")
        );

        List<Bookmark> allByBoardAndUser = bookmarkRepository.findAllByBoardAndUser(board, user);

        BookmarkDto bookmarkDto = null;

        if (allByBoardAndUser.size() != 0){
            bookmarkRepository.deleteByBoardAndUser(board, user);
            bookmarkDto = new BookmarkDto(boardId, false);
        } else {
            Bookmark bookmark = new Bookmark(user, board);
            bookmarkRepository.save(bookmark);

            bookmarkDto = new BookmarkDto(boardId, true);
        }
        return bookmarkDto;
    }

    public List<MyBookmarkListDto> getMyBookMark(User user) {
        List<MyBookmarkListDto> myBookmarkListDtos = new ArrayList<>();
        List<Bookmark> bookmarkList = bookmarkRepository.findAllByUser(user);

        for(Bookmark bookmark : bookmarkList) {
            Board board = bookmark.getBoard();

            MyBookmarkListDto myBookmarkListDto = new MyBookmarkListDto(
                    board.getTitle(),
                    board.getContent(),
                    board.getImg()
            );
            myBookmarkListDtos.add(myBookmarkListDto);
        }
        return myBookmarkListDtos;
    }

    public List<BookmarkMapList> getBookmarkMapList(String cityName, UserDetailsImpl userDetails) {
        try {
            if(cityName.equals("default")){
                List<BookmarkMapList> bookmarkMapLists = new ArrayList<>();
                List<Star> starList = starRepository.findAllByOrderByStarGazingDesc();

                for (Star star : starList) {
                    Location location = star.getLocation();
                    List<Board> boardList = location.getBoard();
                    for (Board board : boardList) {

                        BookmarkMapList bookmarkMapList = new BookmarkMapList(
                                board.getId(),
                                bookmarkCheck(userDetails, board)
                        );

                        bookmarkMapLists.add(bookmarkMapList);
                    }
                }

                return bookmarkMapLists;
            }
            else{
                List<BookmarkMapList> bookmarkMapLists = new ArrayList<>();
                List<Board> boardList = boardRepository.findByAddressContaining(cityName);
                for (Board board : boardList) {
                    BookmarkMapList bookmarkMapList = new BookmarkMapList(
                            board.getId(),
                            bookmarkCheck(userDetails, board)
                    );

                    bookmarkMapLists.add(bookmarkMapList);
                }
                return bookmarkMapLists;
            }

        }
        catch(NullPointerException nullPointerException){
            throw new NullPointerException("해당하는 게시글이 존재하지 않습니다.");
        }
    }

    private Boolean bookmarkCheck(UserDetailsImpl userDetails, Board findBoard) {
        Boolean bookmarkCheck;
        List<Bookmark> bookmarkList = bookmarkRepository.findAllByBoardAndUser(findBoard, userDetails.getUser());
        if(bookmarkList.size() != 0){
            bookmarkCheck = true;
        }
        else{
            bookmarkCheck = false;
        }

        return bookmarkCheck;
    }

}