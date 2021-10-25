package com.sparta.StarProject.domain.repository;

import com.sparta.StarProject.domain.StarInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StarInfoRepository extends JpaRepository<StarInfo, Long> {
    Optional<StarInfo> findById(Long id);
}
