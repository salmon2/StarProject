package com.sparta.StarProject.repository;

import com.sparta.StarProject.domain.board.Camping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampingRepository extends JpaRepository<Camping, Long> {
}
