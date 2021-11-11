package com.sparta.StarProject;

import com.sparta.StarProject.api.accuweatherAPI.StarGazingCity;
import com.sparta.StarProject.domain.*;
import com.sparta.StarProject.domain.board.Camping;
import com.sparta.StarProject.dto.LocationStarMoonDustDto;
import com.sparta.StarProject.repository.CampingRepository;
import com.sparta.StarProject.repository.LocationRepository;
import com.sparta.StarProject.repository.UserRepository;
import com.sparta.StarProject.api.API;
import com.sparta.StarProject.api.CampingList;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

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
    LocationRepository locationRepository;

    @Autowired
    API api;

    @SneakyThrows
    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        User user = new User("기남", "asdf", "salmon2");
        userRepository.save(user);

        int count = 0;
        for (StarGazingCity city : StarGazingCity.values()) {
            LocationStarMoonDustDto infoByAddress = api.findInfoByAddress(city.getKorName(), count);
            api.saveStarLocationWeather(infoByAddress);
            count++;
        }


        int count2= 0;
        for (CampingList value : CampingList.values()) {
            List<String> strings = api.processAddress(value.getAddress());

            Location findLocation = locationRepository.findByCityName(strings.get(0));

            Camping campingData = new Camping(value.getName(), value.getAddress(),
                    value.getAddress(), value.getImgSrc(), value.getLocationX(), value.getLocationY(), user, findLocation, "campingData");
            campingRepository.save(campingData);
            count2++;
        }


    }

}