package com.sparta.StarProject.repository.boardRepository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.StarProject.domain.board.QBoard;
import com.sparta.StarProject.dto.BoardDto;
import com.sparta.StarProject.dto.QBoardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepositoryCustom{
    private final JPAQueryFactory queryFactory;
    @Override
    public List<BoardDto> myfindBoardList() {
        List<BoardDto> result =
                queryFactory
                        .select(
                                new QBoardDto(
                                        QBoard.board.title,
                                        QBoard.board.address,
                                        QBoard.board.content,
                                        QBoard.board.img
                                )
                        )
                        .from(QBoard.board)
                        .fetch();

        return result;
    }
}
