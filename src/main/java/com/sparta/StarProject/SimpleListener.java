package com.sparta.StarProject;

import com.sparta.StarProject.domain.*;
import com.sparta.StarProject.domain.board.Camping;
import com.sparta.StarProject.domain.board.UserMake;
import com.sparta.StarProject.dto.LocationStarMoonDustDto;
import com.sparta.StarProject.repository.CampingRepository;
import com.sparta.StarProject.repository.UserMakeRepository;
import com.sparta.StarProject.repository.UserRepository;
import com.sparta.StarProject.repository.WeatherRepository;
import com.sparta.StarProject.weatherApi.API;
import com.sparta.StarProject.weatherApi.CampingList;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.transaction.Transactional;
import java.util.List;

@NoArgsConstructor
//@Component
public class SimpleListener implements ApplicationListener<ApplicationStartedEvent> {
    @Autowired
    UserRepository userRepository;
    @Autowired
    CampingRepository campingRepository;

    @Autowired
    API api;

    @SneakyThrows
    @Override
    @Transactional
    public void onApplicationEvent(ApplicationStartedEvent event) {
        User user = new User("기남", "asdf", "salmon2");

        for (CampingList value : CampingList.values()) {

            Camping campingData = new Camping(value.getName(), value.getAddress(), value.getAddress(), value.getImgSrc(), user, "campingData");
            campingRepository.save(campingData);

        }

        List<Camping> campingList = campingRepository.findAll();
        for (Camping camping : campingList) {
            LocationStarMoonDustDto infoByAddress = api.findInfoByAddress(camping.getAddress());
            api.saveStarLocationWeather(camping, infoByAddress);
        }
    }

}