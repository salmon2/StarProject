package com.sparta.StarProject.repository;

import com.sparta.StarProject.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, String> {
    Location findAllByCityName(String cityName);
    //List<Location> findAllByCityName(String cityName);
//    Optional<Location> findAllByCityName(String cityName);
}
