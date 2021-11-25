package com.sparta.StarProject.repository.boardRepository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.StarProject.domain.*;
import com.sparta.StarProject.domain.QUser;
import com.sparta.StarProject.domain.board.Board;
import com.sparta.StarProject.dto.*;
import com.sparta.StarProject.dto.QBookmarkMapList;
import com.sparta.StarProject.dto.QCommunityDtoCustom;
import com.sparta.StarProject.dto.QMapBoardDto;
import com.sparta.StarProject.dto.QMyBoardDto;
import com.sparta.StarProject.dto.QMyBookmarkListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sparta.StarProject.domain.QBookmark.bookmark;
import static com.sparta.StarProject.domain.QLike.*;
import static com.sparta.StarProject.domain.QLocation.location;
import static com.sparta.StarProject.domain.QStar.star;
import static com.sparta.StarProject.domain.board.QBoard.*;

@Repository
@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<CommunityDtoCustom> findAllOrderByStarCustomExistUser(User user, PageRequest pageRequest) {
        List<CommunityDtoCustom> result = queryFactory
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
                                JPAExpressions
                                        .select(like.count())
                                        .from(like)
                                        .where(boardIdEqLikeBoardId()),
                                JPAExpressions
                                        .select(like)
                                        .from(like)
                                        .where(userIdEqLikeUserId(user).and(boardIdEqLikeBoardId()))
                                        .exists()
                        )
                )
                .from(board)
                .join(board.user, QUser.user)
                .join(board.location, location)
                .join(location.star, star)
                .orderBy(star.starGazing.desc())
                .offset(pageRequest.getOffset())
                .limit(pageRequest.getPageSize())
                .fetch();

        JPAQuery<Board> countQuery = countCommunityDtoCustomListQuery();

        return PageableExecutionUtils.getPage(result, pageRequest, countQuery::fetchCount);
    }

    @Override
    public Page<CommunityDtoCustom> findAllOrderByStarCustomNoneUser(PageRequest pageRequest) {
        List<CommunityDtoCustom> result = queryFactory
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
                                JPAExpressions
                                        .select(like.count())
                                        .from(like)
                                        .where(boardIdEqLikeBoardId()),
                                Expressions.asBoolean(false)
                        )
                )
                .from(board)
                .join(board.user, QUser.user)
                .join(board.location, location)
                .join(location.star, star)
                .orderBy(star.starGazing.desc())
                .offset(pageRequest.getOffset())
                .limit(pageRequest.getPageSize())
                .fetch();

        JPAQuery<Board> countQuery = countCommunityDtoCustomListQuery();

        return PageableExecutionUtils.getPage(result, pageRequest, countQuery::fetchCount);
    }

    @Override
    public Page<CommunityDtoCustom> findAllOrderByStarCustomContainingCityExistUser(String cityName, User user, PageRequest pageRequest) {
        List<CommunityDtoCustom> result = queryFactory
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
                                JPAExpressions
                                        .select(like.count())
                                        .from(like)
                                        .where(boardIdEqLikeBoardId()),
                                JPAExpressions
                                        .select(like)
                                        .from(like)
                                        .where(userIdEqLikeUserId(user).and(boardIdEqLikeBoardId()))
                                        .exists()
                        )
                )
                .from(board)
                .join(board.user, QUser.user)
                .join(board.location, location)
                .join(location.star, star)
                .orderBy(star.starGazing.desc())
                .where(likeAddressOrTitle(cityName))
                .offset(pageRequest.getOffset())
                .limit(pageRequest.getPageSize())
                .fetch();

        JPAQuery<Board> countQuery = countCommunityDtoCustomContainingCityListQuery(cityName);


        return PageableExecutionUtils.getPage(result, pageRequest, countQuery::fetchCount);

    }

    @Override
    public Page<CommunityDtoCustom> findAllOrderByStarCustomContainingCityNoneUser(String cityName, PageRequest pageRequest) {
        List<CommunityDtoCustom> result = queryFactory
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
                                JPAExpressions
                                        .select(like.count())
                                        .from(like)
                                        .where(boardIdEqLikeBoardId()),
                                Expressions.asBoolean(false)
                        )
                )
                .from(board)
                .join(board.user, QUser.user)
                .join(board.location, location)
                .join(location.star, star)
                .orderBy(star.starGazing.desc())
                .where(likeAddressOrTitle(cityName))
                .offset(pageRequest.getOffset())
                .limit(pageRequest.getPageSize())
                .fetch();

        JPAQuery<Board> countQuery = countCommunityDtoCustomContainingCityListQuery(cityName);


        return PageableExecutionUtils.getPage(result, pageRequest, countQuery::fetchCount);
    }

    private BooleanExpression likeAddressOrTitle(String cityName) {
        return board.address.contains(cityName).or(board.title.contains(cityName));
    }

    @Override
    public Page<CommunityDtoCustom> findAllOrderByLikeCustomExistUser(User user, PageRequest pageRequest) {
        List<CommunityDtoCustom> result = queryFactory
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
                                JPAExpressions
                                        .select(like.count())
                                        .from(like)
                                        .where(boardIdEqLikeBoardId()),
                                JPAExpressions
                                        .select(like)
                                        .from(like)
                                        .where(userIdEqLikeUserId(user).and(boardIdEqLikeBoardId()))
                                        .exists()
                        )
                )
                .from(board)
                .join(board.user, QUser.user)
                .join(board.location, location)
                .join(location.star, star)
                .orderBy(board.likeCount.desc())
                .offset(pageRequest.getOffset())
                .limit(pageRequest.getPageSize())
                .fetch();

        JPAQuery<Board> countQuery = countCommunityDtoCustomListQuery();


        return PageableExecutionUtils.getPage(result, pageRequest, countQuery::fetchCount);
    }

    @Override
    public Page<CommunityDtoCustom> findAllOrderByLikeCustomNoneUser(PageRequest pageRequest) {
        List<CommunityDtoCustom> result = queryFactory
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
                                JPAExpressions
                                        .select(like.count())
                                        .from(like)
                                        .where(boardIdEqLikeBoardId()),
                                Expressions.asBoolean(false)
                        )
                )
                .from(board)
                .join(board.user, QUser.user)
                .join(board.location, location)
                .join(location.star, star)
                .orderBy(board.likeCount.desc())
                .offset(pageRequest.getOffset())
                .limit(pageRequest.getPageSize())
                .fetch();

        JPAQuery<Board> countQuery = countCommunityDtoCustomListQuery();


        return PageableExecutionUtils.getPage(result, pageRequest, countQuery::fetchCount);
    }

    @Override
    public Page<CommunityDtoCustom> findAllOrderByLikeCustomContainingCityNoneUser(String cityName, PageRequest pageRequest) {
        List<CommunityDtoCustom> result = queryFactory
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
                                JPAExpressions
                                        .select(like.count())
                                        .from(like)
                                        .where(boardIdEqLikeBoardId()),
                                Expressions.asBoolean(false)
                        )
                )
                .from(board)
                .join(board.user, QUser.user)
                .join(board.location, location)
                .join(location.star, star)
                .orderBy(board.likeCount.desc())
                .where(likeAddressOrTitle(cityName))
                .offset(pageRequest.getOffset())
                .limit(pageRequest.getPageSize())
                .fetch();

        JPAQuery<Board> countQuery = countCommunityDtoCustomContainingCityListQuery(cityName);


        return PageableExecutionUtils.getPage(result, pageRequest, countQuery::fetchCount);
    }

    @Override
    public Page<CommunityDtoCustom> findAllOrderByLikeCustomContainingCityExistUser(String cityName, User user, PageRequest pageRequest) {
        List<CommunityDtoCustom> result = queryFactory
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
                                JPAExpressions
                                        .select(like.count())
                                        .from(like)
                                        .where(boardIdEqLikeBoardId()),
                                JPAExpressions
                                        .select(like)
                                        .from(like)
                                        .where(userIdEqLikeUserId(user).and(boardIdEqLikeBoardId()))
                                        .exists()
                        )
                )
                .from(board)
                .join(board.user, QUser.user)
                .join(board.location, location)
                .join(location.star, star)
                .orderBy(board.likeCount.desc())
                .where(likeAddressOrTitle(cityName))
                .offset(pageRequest.getOffset())
                .limit(pageRequest.getPageSize())
                .fetch();

        JPAQuery<Board> countQuery = countCommunityDtoCustomContainingCityListQuery(cityName);


        return PageableExecutionUtils.getPage(result, pageRequest, countQuery::fetchCount);
    }

    @Override
    public Page<CommunityDtoCustom> findAllOrderByLatestCustomExistUser(User user, PageRequest pageRequest) {
        List<CommunityDtoCustom> result = queryFactory
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
                                JPAExpressions
                                        .select(like.count())
                                        .from(like)
                                        .where(boardIdEqLikeBoardId()),
                                JPAExpressions
                                        .select(like)
                                        .from(like)
                                        .where(userIdEqLikeUserId(user).and(boardIdEqLikeBoardId()))
                                        .exists()
                        )
                )
                .from(board)
                .join(board.user, QUser.user)
                .join(board.location, location)
                .join(location.star, star)
                .orderBy(board.createdAt.desc())
                .offset(pageRequest.getOffset())
                .limit(pageRequest.getPageSize())
                .fetch();

        JPAQuery<Board> countQuery = countCommunityDtoCustomListQuery();


        return PageableExecutionUtils.getPage(result, pageRequest, countQuery::fetchCount);

    }

    @Override
    public Page<CommunityDtoCustom> findAllOrderByLatestCustomNoneUser(PageRequest pageRequest) {
        List<CommunityDtoCustom> result = queryFactory
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
                                JPAExpressions
                                        .select(like.count())
                                        .from(like)
                                        .where(boardIdEqLikeBoardId()),
                                Expressions.asBoolean(false)
                        )
                )
                .from(board)
                .join(board.user, QUser.user)
                .join(board.location, location)
                .join(location.star, star)
                .orderBy(board.createdAt.desc())
                .offset(pageRequest.getOffset())
                .limit(pageRequest.getPageSize())
                .fetch();

        JPAQuery<Board> countQuery = countCommunityDtoCustomListQuery();

        return PageableExecutionUtils.getPage(result, pageRequest, countQuery::fetchCount);
    }


    @Override
    public Page<CommunityDtoCustom> findAllOrderByLatestCustomContainingCityNoneUser(String cityName, PageRequest pageRequest) {
        List<CommunityDtoCustom> result = queryFactory
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
                                JPAExpressions
                                        .select(like.count())
                                        .from(like)
                                        .where(boardIdEqLikeBoardId()),
                                Expressions.asBoolean(false)
                        )
                )
                .from(board)
                .join(board.user, QUser.user)
                .join(board.location, location)
                .join(location.star, star)
                .orderBy(board.createdAt.desc())
                .where(likeAddressOrTitle(cityName))
                .offset(pageRequest.getOffset())
                .limit(pageRequest.getPageSize())
                .fetch();

        JPAQuery<Board> countQuery = countCommunityDtoCustomContainingCityListQuery(cityName);


        return PageableExecutionUtils.getPage(result, pageRequest, countQuery::fetchCount);
    }

    @Override
    public Page<CommunityDtoCustom> findAllOrderByLatestCustomContainingCityExistUser(String cityName, User user, PageRequest pageRequest) {
        List<CommunityDtoCustom> result = queryFactory
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
                                JPAExpressions
                                        .select(like.count())
                                        .from(like)
                                        .where(boardIdEqLikeBoardId()),
                                JPAExpressions
                                        .select(like)
                                        .from(like)
                                        .where(userIdEqLikeUserId(user).and(boardIdEqLikeBoardId()))
                                        .exists()
                        )
                )
                .from(board)
                .join(board.user, QUser.user)
                .join(board.location, location)
                .join(location.star, star)
                .orderBy(board.createdAt.desc())
                .where(likeAddressOrTitle(cityName))
                .offset(pageRequest.getOffset())
                .limit(pageRequest.getPageSize())
                .fetch();

        JPAQuery<Board> countQuery = countCommunityDtoCustomContainingCityListQuery(cityName);


        return PageableExecutionUtils.getPage(result, pageRequest, countQuery::fetchCount);
    }

    @Override
    public Page<MapBoardDto> findAllMapBoardDtoListCustomExistUser(User user, PageRequest pageRequest) {
        List<MapBoardDto> result = queryFactory
                .select(
                        new QMapBoardDto(
                                board.id,
                                board.type,
                                board.title,
                                board.longitude,
                                board.latitude,
                                board.address,
                                JPAExpressions
                                        .select(bookmark)
                                        .from(bookmark)
                                        .where(bookmark.user.id.eq(user.getId()).and(bookmark.board.id.eq(board.id)))
                                        .exists(),
                                star.starGazing,
                                board.img
                        )
                )
                .from(board)
                .join(board.location.star, star)
                .offset(pageRequest.getOffset())
                .limit(pageRequest.getPageSize())
                .orderBy(star.starGazing.desc())
                .fetch();

        JPAQuery<Board> countQuery = countMapBoardList();

        return PageableExecutionUtils.getPage(result, pageRequest, countQuery::fetchCount);

    }

    @Override
    public Page<MapBoardDto> findAllMapBoardDtoListCustomByLocationExistUser(User user, Double x_location, Double y_location, PageRequest pageRequest) {
        List<MapBoardDto> result = queryFactory
                .select(
                        new QMapBoardDto(
                                board.id,
                                board.type,
                                board.title,
                                board.longitude,
                                board.latitude,
                                board.address,
                                JPAExpressions
                                        .select(bookmark)
                                        .from(bookmark)
                                        .where(bookmark.user.id.eq(user.getId()).and(bookmark.board.id.eq(board.id)))
                                        .exists(),
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
                        board.longitude.subtract(Expressions.asNumber(x_location)).abs().loe(0.6).and(
                        board.latitude.subtract(Expressions.asNumber(y_location)).abs().loe(0.6))
                )
                .fetch();

        JPAQuery<Board> countQuery = countMapBoardListUsingLocation(x_location, y_location);

        return PageableExecutionUtils.getPage(result, pageRequest, countQuery::fetchCount);
    }

    @Override
    public Page<MapBoardDto> findAllMapBoardDtoListCustomByCityNameExistUser(String cityName, User user, PageRequest pageRequest) {
        List<MapBoardDto> result = queryFactory
                .select(
                        new QMapBoardDto(
                                board.id,
                                board.type,
                                board.title,
                                board.longitude,
                                board.latitude,
                                board.address,
                                JPAExpressions
                                        .select(bookmark)
                                        .from(bookmark)
                                        .where(bookmark.user.id.eq(user.getId()).and(bookmark.board.id.eq(board.id)))
                                        .exists(),
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
                        likeAddressOrTitle(cityName)
                )
                .fetch();

        JPAQuery<Board> countQuery = countMapBoardListUsingCityName(cityName);

        return PageableExecutionUtils.getPage(result, pageRequest, countQuery::fetchCount);

    }

    @Override
    public Page<MapBoardDto> findAllMapBoardDtoListCustomByLocationAndCityNameExistUser(String cityName, User user, Double x_location, Double y_location, PageRequest pageRequest) {
        List<MapBoardDto> result = queryFactory
                .select(
                        new QMapBoardDto(
                                board.id,
                                board.type,
                                board.title,
                                board.longitude,
                                board.latitude,
                                board.address,
                                JPAExpressions
                                        .select(bookmark)
                                        .from(bookmark)
                                        .where(bookmark.user.id.eq(user.getId()).and(bookmark.board.id.eq(board.id)))
                                        .exists(),
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
                        likeAddressOrTitle(cityName).and(
                                board.longitude.subtract(Expressions.asNumber(x_location)).abs().loe(0.6).and(
                                        board.latitude.subtract(Expressions.asNumber(y_location)).abs().loe(0.6))
                        )
                )
                .fetch();

        JPAQuery<Board> countQuery = countMapBoardListUsingCityNameAndLocation(cityName, x_location, y_location);

        return PageableExecutionUtils.getPage(result, pageRequest, countQuery::fetchCount);
    }

    @Override
    public Page<MapBoardDto> findAllMapBoardDtoListCustomNoneUser(PageRequest pageRequest) {
        List<MapBoardDto> result = queryFactory
                .select(
                        new QMapBoardDto(
                                board.id,
                                board.type,
                                board.title,
                                board.longitude,
                                board.latitude,
                                board.address,
                                Expressions.asBoolean(false),
                                star.starGazing,
                                board.img
                        )
                )
                .from(board)
                .join(board.location.star, star)
                .offset(pageRequest.getOffset())
                .limit(pageRequest.getPageSize())
                .orderBy(star.starGazing.desc())
                .fetch();

        JPAQuery<Board> countQuery = countMapBoardList();

        return PageableExecutionUtils.getPage(result, pageRequest, countQuery::fetchCount);
    }

    @Override
    public Page<MapBoardDto> findAllMapBoardDtoListCustomByLocationNoneUser(Double x_location, Double y_location, PageRequest pageRequest) {
        List<MapBoardDto> result = queryFactory
                .select(
                        new QMapBoardDto(
                                board.id,
                                board.type,
                                board.title,
                                board.longitude,
                                board.latitude,
                                board.address,
                                Expressions.asBoolean(false),
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
                        board.longitude.subtract(Expressions.asNumber(x_location)).abs().loe(0.6).and(
                                board.latitude.subtract(Expressions.asNumber(y_location)).abs().loe(0.6))
                )
                .fetch();

        JPAQuery<Board> countQuery = countMapBoardListUsingLocation(x_location, y_location);

        return PageableExecutionUtils.getPage(result, pageRequest, countQuery::fetchCount);

    }

    @Override
    public Page<MapBoardDto> findAllMapBoardDtoListCustomByCityNameNoneUser(String cityName, PageRequest pageRequest) {
        List<MapBoardDto> result = queryFactory
                .select(
                        new QMapBoardDto(
                                board.id,
                                board.type,
                                board.title,
                                board.longitude,
                                board.latitude,
                                board.address,
                                Expressions.asBoolean(false),
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
                        likeAddressOrTitle(cityName)
                )
                .fetch();

        JPAQuery<Board> countQuery = countMapBoardListUsingCityName(cityName);

        return PageableExecutionUtils.getPage(result, pageRequest, countQuery::fetchCount);
    }

    @Override
    public Page<MapBoardDto> findAllMapBoardDtoListCustomByLocationAndCityNameNoneUser(String cityName, Double x_location, Double y_location, PageRequest pageRequest) {
        List<MapBoardDto> result = queryFactory
                .select(
                        new QMapBoardDto(
                                board.id,
                                board.type,
                                board.title,
                                board.longitude,
                                board.latitude,
                                board.address,
                                Expressions.asBoolean(false),
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
                        likeAddressOrTitle(cityName).and(
                                board.longitude.subtract(Expressions.asNumber(x_location)).abs().loe(0.6).and(
                                        board.latitude.subtract(Expressions.asNumber(y_location)).abs().loe(0.6))
                        )
                )
                .fetch();

        JPAQuery<Board> countQuery = countMapBoardListUsingCityNameAndLocation(cityName, x_location, y_location);
        return PageableExecutionUtils.getPage(result, pageRequest, countQuery::fetchCount);
    }

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
                .orderBy(board.createdAt.desc())
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
                .orderBy(board.createdAt.desc())
                .offset(pageRequest.getOffset())
                .limit(pageRequest.getPageSize())
                .fetch();

        JPAQuery<Board> countQuery = countMyBookmarkBoardDto(user);

        return PageableExecutionUtils.getPage(result, pageRequest, countQuery::fetchCount);
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


    private JPAQuery<Board> countMapBoardListUsingCityNameAndLocation(String cityName, Double x_location, Double y_location) {
        return queryFactory
                .selectFrom(board)
                .join(board.location.star, star)
                .orderBy(star.starGazing.desc())
                .where(
                        board.address.contains(cityName).and(
                                board.longitude.subtract(Expressions.asNumber(x_location)).abs().loe(0.6).and(
                                        board.latitude.subtract(Expressions.asNumber(y_location)).abs().loe(0.6))
                        )
                );
    }

    private JPAQuery<Board> countMapBoardListUsingCityName(String cityName) {
        return queryFactory
                .selectFrom(board)
                .join(board.location.star, star)
                .orderBy(star.starGazing.desc())
                .where(
                        board.address.contains(cityName)
                );
    }

    private JPAQuery<Board> countMapBoardListUsingLocation(Double x_location, Double y_location) {
        return queryFactory
                .selectFrom(board)
                .join(board.location.star, star)
                .orderBy(star.starGazing.desc())
                .where(
                        board.longitude.subtract(Expressions.asNumber(x_location)).abs().loe(0.6).and(
                                board.latitude.subtract(Expressions.asNumber(y_location)).abs().loe(0.6))
                );
    }

    private JPAQuery<Board> countMapBoardList() {
        return queryFactory
                .selectFrom(board)
                .join(board.location.star, star)
                .orderBy(star.starGazing.desc());
    }


    private JPAQuery<Board> countCommunityDtoCustomListQuery() {
        return queryFactory
                .selectFrom(board)
                .join(board.user, QUser.user)
                .join(board.location, location)
                .join(location.star, star)
                .orderBy(like.count().desc());
    }


    private JPAQuery<Board> countCommunityDtoCustomContainingCityListQuery(String cityName) {
        return queryFactory
                .selectFrom(board)
                .join(board.user, QUser.user)
                .join(board.location, location)
                .join(location.star, star)
                .orderBy(star.starGazing.desc())
                .where(board.address.contains(cityName));
    }

    private BooleanExpression userIdEqLikeUserId(User user) {
        return like.user.id.eq(user.getId());
    }

    private BooleanExpression boardIdEqLikeBoardId() {
        return like.board.id.eq(board.id);
    }


}
