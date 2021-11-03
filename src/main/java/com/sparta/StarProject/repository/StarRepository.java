package com.sparta.StarProject.repository;

import com.sparta.StarProject.domain.Location;
import com.sparta.StarProject.domain.Star;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StarRepository  extends JpaRepository<Star, Long> {
    List<Star> findAllByLocation(Location location);
    List<Star> findTop3ByOrderByStarGazingDesc();
    List<Star> findAllByOrderByStarGazingDesc();
}
