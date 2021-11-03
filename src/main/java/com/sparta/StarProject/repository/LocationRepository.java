package com.sparta.StarProject.repository;

import com.sparta.StarProject.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, String> {
    Location findByLongitudeAndLatitude(Double longitude, Double latitude);
}
