package com.sparta.StarProject.repository.boardRepository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.StarProject.domain.*;
import com.sparta.StarProject.domain.QUser;
import com.sparta.StarProject.domain.board.Board;
import com.sparta.StarProject.domain.board.QTimestamped;
import com.sparta.StarProject.dto.*;
import com.sparta.StarProject.dto.QCommunityDtoCustom;
import com.sparta.StarProject.dto.QDetailBoardDto;
import com.sparta.StarProject.dto.QMainDto;
import com.sparta.StarProject.dto.QMapBoardDto;
import com.sparta.StarProject.dto.QMyBoardDto;
import com.sparta.StarProject.dto.QMyBookmarkListDto;
import com.sparta.StarProject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

import static com.sparta.StarProject.domain.QBookmark.bookmark;
import static com.sparta.StarProject.domain.QLike.*;
import static com.sparta.StarProject.domain.QLocation.location;
import static com.sparta.StarProject.domain.QStar.star;
import static com.sparta.StarProject.domain.QUser.user;
import static com.sparta.StarProject.domain.board.QBoard.*;

@Repository
@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<MyBoardDto> findAllByUserCustom(User user, PageRequest pageRequest) {
        List<MyBoardDto> result = queryFactory
                .select(
                        new QMyBoardDto(
                                board.id,
                                board.title,
                                board.content,
                                board.img
                        )
                )
                .from(board)
                .join(board.user, QUser.user)
                .where(QUser.user.id.eq(user.getId()))
                .orderBy(board.modifiedAt.desc())
                .offset(pageRequest.getOffset())
                .limit(pageRequest.getPageSize())
                .fetch();

        JPAQuery<Board> countQuery = countMyBoardDto(user);

        return PageableExecutionUtils.getPage(result, pageRequest, countQuery::fetchCount);
    }

    @Override
    public Page<MyBookmarkListDto> findAllBookmarkByUserCustom(User user, PageRequest pageRequest) {
        List<MyBookmarkListDto> result = queryFactory
                .select(
                        new QMyBookmarkListDto(
                                board.id,
                                board.title,
                                board.content,
                                board.img
                        )
                )
                .from(board)
                .join(board.bookmark, bookmark)
                .join(bookmark.user, QUser.user)
                .where(QUser.user.id.eq(user.getId()))
                .orderBy(board.modifiedAt.desc())
                .offset(pageRequest.getOffset())
                .limit(pageRequest.getPageSize())
                .fetch();

        JPAQuery<Board> countQuery = countMyBookmarkBoardDto(user);

        return PageableExecutionUtils.getPage(result, pageRequest, countQuery::fetchCount);
    }

    @Override
    public DetailBoardDto findDetailBoardByBoard(Long id, UserDetailsImpl userDetails) {
        DetailBoardDto result = queryFactory
                .select(
                        new QDetailBoardDto(
                                board.id,
                                board.modifiedAt,
                                user.nickname,
                                board.title,
                                board.address,
                                board.img,
                                board.content,
                                board.longitude,
                                board.latitude,
                                (userDetails == null) ? setFalse() : distinguishLikeExistUser(userDetails.getUser()),
                                boardLikeCount(),
                                (userDetails == null) ? setFalse() : distinguishBookmarkExistUser(userDetails.getUser())

                        )
                )
                .from(board)
                .join(board.user, user)
                .where(board.id.eq(id))
                .fetchOne();

        return result;
    }

    @Override
    public Page<CommunityDtoCustom> findCommunityList(UserDetailsImpl userDetails, PageRequest pageRequest, String cityName, String sort) {
        List<CommunityDtoCustom> result;
        result = queryFactory
                .select(
                        new QCommunityDtoCustom(
                                board.id,
                                QUser.user.nickname,
                                board.title,
                                location.cityName,
                                board.address,
                                board.img,
                                board.content,
                                board.modifiedAt,
                                boardLikeCount(),
                                (userDetails == null) ? setFalse() : distinguishLikeExistUser(userDetails.getUser()),
                                (userDetails == null) ? setFalse() : distinguishBookmarkExistUser(userDetails.getUser())
                        )
                )
                .from(board)
                .join(board.user, QUser.user)
                .join(board.location, location)
                .join(location.star, star)
                .orderBy(getOrderBy(sort))
                .where(likeAddressOrTitle(cityName))
                .offset(pageRequest.getOffset())
                .limit(pageRequest.getPageSize())
                .fetch();

        JPAQuery<Board> countQuery = countCommunityDtoCustomContainingCityListQuery(cityName);


        return PageableExecutionUtils.getPage(result, pageRequest, countQuery::fetchCount);

    }

    @Override
    public Page<MapBoardDto> findAllBoardDtoList(UserDetailsImpl userDetails, String cityName, Double x_location, Double y_location, PageRequest pageRequest) {
        List<MapBoardDto> result = queryFactory
                .select(
                        new QMapBoardDto(
                                board.id,
                                board.type,
                                board.title,
                                board.longitude,
                                board.latitude,
                                board.address,
                                (userDetails == null) ? setFalse() : distinguishBookmarkExistUser(userDetails.getUser()),
                                star.starGazing,
                                board.img
                        )
                )
                .from(board)
                .join(board.location.star, star)
                .offset(pageRequest.getOffset())
                .limit(pageRequest.getPageSize())
                .orderBy(star.starGazing.desc())
                .where(
                        distinguishWhereCondition(x_location, y_location, cityName)
                )
                .fetch();

        JPAQuery<Board> countQuery = countMapBoardList();

        return PageableExecutionUtils.getPage(result, pageRequest, countQuery::fetchCount);
    }

    @Override
    public MapBoardDto findboardMapSearchById(Long id, UserDetailsImpl userDetails) {
        MapBoardDto mapBoardDto = queryFactory
                .select(new QMapBoardDto(
                        board.id,
                        board.type,
                        board.title,
                        board.longitude,
                        board.latitude,
                        board.address,
                        (userDetails == null) ? setFalse() : distinguishBookmarkExistUser(userDetails.getUser()),
                        star.starGazing,
                        board.img
                ))
                .from(board)
                .join(board.location.star, star)
                .where(board.id.eq(id))
                .fetchOne();
        return mapBoardDto;
    }

    @Override
    public List<MainDto> findMainList(UserDetailsImpl userDetails) {
        List<MainDto> fetch = queryFactory
                .select(
                        new QMainDto(
                                board.id,
                                board.title,
                                board.address,
                                board.content,
                                star.starGazing,
                                board.img,
                                (userDetails == null) ? setFalse() : distinguishBookmarkExistUser(userDetails.getUser())
                        )
                )
                .from(board)
                .join(board.location.star, star)
                .orderBy(star.starGazing.desc())
                .fetch();

        return fetch;
    }


    /** 조건문 **/
    private BooleanExpression distinguishBookmarkExistUser(User user) {
        return JPAExpressions
                .select(bookmark)
                .from(bookmark)
                .where(userIdEqBookmarkUserId(user).and(boardIdEqBookmarkBoardId()))
                .exists();
    }
    private BooleanExpression likeAddressOrTitle(String cityName) {
        BooleanExpression result = cityName.equals("all") ? null : board.address.contains(cityName).or(board.title.contains(cityName));
        return result;
    }

    private BooleanExpression setFalse() {
        return Expressions.asBoolean(false);
    }

    private BooleanExpression distinguishLikeExistUser(User user) {
        return JPAExpressions
                .select(like)
                .from(like)
                .where(userIdEqLikeUserId(user).and(boardIdEqLikeBoardId()))
                .exists();
    }

    private BooleanExpression distinguishWhereCondition(Double x_location, Double y_location, String cityName) {
        if(cityName.equals("default")) {
            if(x_location.equals(0.0))
                return null;
            else
                return  board.longitude.subtract(Expressions.asNumber(x_location)).abs().loe(0.6).and(
                        board.latitude.subtract(Expressions.asNumber(y_location)).abs().loe(0.6));
        }
        else{
            if(x_location.equals(0.0))
                return board.address.contains(cityName).or(board.title.contains(cityName));
            else
                return board.address.contains(cityName).or(board.title.contains(cityName)).and(
                        board.longitude.subtract(Expressions.asNumber(x_location)).abs().loe(0.6).and(
                                board.latitude.subtract(Expressions.asNumber(y_location)).abs().loe(0.6))
                );
        }

    }

    private OrderSpecifier<? extends Serializable> getOrderBy(String sort) {
        if(sort.equals("star"))
            return star.starGazing.desc();
        else if(sort.equals("like"))
            return board.likeCount.desc();
        else if(sort.equals("latest"))
            return board.modifiedAt.desc();
        else
            return null;
    }

    private BooleanExpression userIdEqLikeUserId(User user) {
        return like.user.id.eq(user.getId());
    }

    private BooleanExpression boardIdEqLikeBoardId() {
        return like.board.id.eq(board.id);
    }

    private BooleanExpression userIdEqBookmarkUserId(User user){
        return bookmark.user.id.eq(user.getId());
    }

    private BooleanExpression boardIdEqBookmarkBoardId(){
        return bookmark.board.id.eq(board.id);
    }


    /** 페이징네이션 카운트 **/

    private JPQLQuery<Long> boardLikeCount() {
        return JPAExpressions
                .select(like.count())
                .from(like)
                .where(boardIdEqLikeBoardId());
    }


    private JPAQuery<Board> countMyBookmarkBoardDto(User user) {
        return queryFactory
                .selectFrom(board)
                .join(board.bookmark, bookmark)
                .join(bookmark.user, QUser.user)
                .where(QUser.user.id.eq(user.getId()));

    }

    private JPAQuery<Board> countMyBoardDto(User user) {
        return queryFactory
                .selectFrom(board)
                .join(board.user, QUser.user)
                .where(QUser.user.id.eq(user.getId()));
    }

    private JPAQuery<Board> countMapBoardList() {
        return queryFactory
                .selectFrom(board)
                .join(board.location.star, star)
                .orderBy(star.starGazing.desc());
    }

    private JPAQuery<Board> countCommunityDtoCustomContainingCityListQuery(String cityName) {
        return queryFactory
                .selectFrom(board)
                .join(board.user, user)
                .join(board.location, location)
                .join(location.star, star)
                .orderBy(star.starGazing.desc())
                .where(board.address.contains(cityName));
    }


}
