package com.sparta.StarProject.service;

import com.sparta.StarProject.domain.Bookmark;
import com.sparta.StarProject.domain.User;
import com.sparta.StarProject.domain.board.Board;
import com.sparta.StarProject.dto.BookmarkDto;
import com.sparta.StarProject.dto.ResponseDto;
import com.sparta.StarProject.repository.BoardRepository;
import com.sparta.StarProject.repository.BookmarkRepository;
import com.sparta.StarProject.repository.UserRepository;
import com.sparta.StarProject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final BookmarkRepository bookmarkRepository;

    public ResponseDto addBookmark(Long boardId, UserDetailsImpl userDetails) {
        Long id = userDetails.getUser().getId();
        User user = userRepository.getById(id);

        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new NullPointerException("게시글이 존재하지 않습니다.")
        );

        bookmarkRepository.findByUserAndBoard(user, board).ifPresent(
                m -> {
                    throw new IllegalArgumentException("중복된 북마크입니다.");
                }
        );

        Bookmark bookmark = new Bookmark(user, board);

        board.getBookmark().add(bookmark);
        user.getMyBookmark().add(bookmark);
        bookmarkRepository.save(bookmark);

        return new ResponseDto(200L, "성공", null);
    }

}