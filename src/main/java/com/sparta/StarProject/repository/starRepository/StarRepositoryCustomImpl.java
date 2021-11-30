package com.sparta.StarProject.repository.starRepository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.StarProject.dto.QStarWeatherResponseDto;
import com.sparta.StarProject.dto.StarWeatherResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.sparta.StarProject.domain.QLocation.location;
import static com.sparta.StarProject.domain.QStar.star;
import static com.sparta.StarProject.domain.QWeather.weather1;

@Repository
@RequiredArgsConstructor
public class StarRepositoryCustomImpl implements StarRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    public StarWeatherResponseDto findStarWeatherResponse(String cityName, String predictTime) {
        StarWeatherResponseDto result = queryFactory
                .select(
                        new QStarWeatherResponseDto(
                                location.cityName,
                                weather1.rainPercent,
                                weather1.humidity,
                                weather1.weather,
                                weather1.temperature,
                                weather1.maxTemperature,
                                weather1.minTemperature,
                                weather1.dust
                        )
                )
                .from(location)
                .join(location.star, star)
                .join(location.weatherList, weather1)
                .where(weather1.predictTime.eq(predictTime).and(location.cityName.eq(cityName)))
                .fetchOne();

        return result;
    }
}
