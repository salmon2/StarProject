//package com.sparta.StarProject.service;
//
//import com.sparta.StarProject.domain.User;
//import com.sparta.StarProject.domain.board.Board;
//import com.sparta.StarProject.dto.BoardListResponseDto;
//import com.sparta.StarProject.dto.BoardRequestDto;
//import com.sparta.StarProject.repository.BoardRepository;
//import com.sparta.StarProject.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import javax.transaction.Transactional;
//import java.util.ArrayList;
//import java.util.List;
//
//import static com.sparta.StarProject.domain.board.Timestamped.TimeToString;
//
//@Service
//@RequiredArgsConstructor
//public class BoardServiceImpl implements BoardService {
//    private final BoardRepository boardRepository;
//    private final UserRepository userRepository;
//
//    public BoardServiceImpl(BoardRepository boardRepository, UserRepository userRepository) {
//        this.boardRepository = boardRepository;
//        this.userRepository = userRepository;
//    }
//
//    @Transactional
//    public Board save(BoardRequestDto boardRequestDto, User user) {
//        Board board = new Board(
//                boardRequestDto.getContents(),
//                boardRequestDto.getImg(),
//                boardRequestDto.getTitle(),
//                boardRequestDto.getLocation(),
//                user.getUsername(), user
//        );
//
//        Board save = boardRepository.save(board);
//        return save;
//    }
//
//    //Read All
//    public List<BoardListResponseDto> findAll(){
//        List<Board> findBoardList = boardRepository.findAllByOrderByIdDesc();
//        List<BoardListResponseDto> boardListResponseDtoList = new ArrayList<>();
//
//        for (Board board : findBoardList){
//            BoardListResponseDto newBoardListResponseDto =
//                    new BoardListResponseDto(board.getId(), board.getUser(), board.getImg(),board.getContent(), board.getLocation(), TimeToString(board.getModifiedAt()));
//            boardListResponseDtoList.add(newBoardListResponseDto);
//        }
//        return boardListResponseDtoList;
//    }
//
////    //Read One
////    public BoardReponseDto findById(Long id){
////        Board findBoard = boardRepository.getById(id).orElseThrow(
////                () -> new IllegalAccessError("존재하는 게시글이 없습니다.")
////        );
////
////        List<>
////    }
//}
