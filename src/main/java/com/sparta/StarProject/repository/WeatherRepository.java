package com.sparta.StarProject.repository;

import com.sparta.StarProject.domain.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeatherRepository extends JpaRepository<Weather, Long> {

    List<Weather> findAllByPredictTime(String predictTime);
}
