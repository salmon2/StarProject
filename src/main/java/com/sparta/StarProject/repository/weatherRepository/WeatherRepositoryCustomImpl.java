package com.sparta.StarProject.repository.weatherRepository;

import com.querydsl.core.QueryFactory;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.StarProject.domain.QLocation;
import com.sparta.StarProject.domain.QStar;
import com.sparta.StarProject.domain.QWeather;
import com.sparta.StarProject.domain.board.Board;
import com.sparta.StarProject.domain.board.QBoard;
import com.sparta.StarProject.domain.board.Timestamped;
import com.sparta.StarProject.dto.DetailWeatherCityInfoDto;
import com.sparta.StarProject.dto.DetailWeatherList;
import com.sparta.StarProject.dto.QDetailWeatherCityInfoDto;
import com.sparta.StarProject.dto.QDetailWeatherList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sparta.StarProject.domain.QLocation.location;
import static com.sparta.StarProject.domain.QStar.star;
import static com.sparta.StarProject.domain.QWeather.weather1;
import static com.sparta.StarProject.domain.board.QBoard.*;

@Repository
@RequiredArgsConstructor
public class WeatherRepositoryCustomImpl implements WeatherRepositoryCustom{
    private final JPAQueryFactory queryFactory;


    @Override
    public List<DetailWeatherList> findDetailWeatherListByBoardId(Long id) {
        List<DetailWeatherList> result = queryFactory
                .select(
                        new QDetailWeatherList(
                                weather1.predictTime,
                                weather1.rainPercent.castToNum(Long.class),
                                weather1.weather,
                                weather1.humidity.castToNum(Long.class),
                                weather1.temperature.castToNum(Long.class),
                                weather1.dust.castToNum(Long.class)
                        )
                )
                .from(location)
                .join(location.weatherList, weather1)
                .join(location.board, board)
                .where(board.id.eq(id))
                .fetch();

        return result;
    }

    @Override
    public DetailWeatherCityInfoDto findDetailWeatherCityInfoByBoardId(Long id) {
        DetailWeatherCityInfoDto result = queryFactory
                .select(
                        new QDetailWeatherCityInfoDto(
                                location.state.concat(" ").concat(location.cityName),
                                Expressions.asString(Timestamped.getCurrentTime().get(3)),
                                star.starGazing,
                                star.moonrise,
                                star.moonSet
                        )
                )
                .from(location)
                .join(location.star, star)
                .join(location.board, board)
                .where(board.id.eq(id))
                .fetchOne();

        return result;
    }
}
