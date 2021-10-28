package com.sparta.StarProject.repository;

import com.sparta.StarProject.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
}
