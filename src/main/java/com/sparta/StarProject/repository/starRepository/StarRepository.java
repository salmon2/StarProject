package com.sparta.StarProject.repository.starRepository;

import com.sparta.StarProject.domain.Location;
import com.sparta.StarProject.domain.Star;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StarRepository  extends JpaRepository<Star, Long>, StarRepositoryCustom {
    List<Star> findTop3ByOrderByStarGazingDesc();
    List<Star> findAllByOrderByStarGazingDesc();
}
