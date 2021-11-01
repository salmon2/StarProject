package com.sparta.StarProject;

import com.sparta.StarProject.domain.*;
import com.sparta.StarProject.domain.board.Camping;
import com.sparta.StarProject.domain.board.UserMake;
import com.sparta.StarProject.repository.CampingRepository;
import com.sparta.StarProject.repository.UserMakeRepository;
import com.sparta.StarProject.repository.WeatherRepository;
import lombok.NoArgsConstructor;
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
@Component
public class SimpleListener implements ApplicationListener<ApplicationStartedEvent> {
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private CampingRepository campingRepository;

    @Autowired
    private UserMakeRepository userMakeRepository;

    @Autowired
    private WeatherRepository weatherRepository;

    @Override
    @Transactional
    public void onApplicationEvent(ApplicationStartedEvent event) {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        User user = new User("기남", "asdf", "salmon2");
        em.persist(user);

        for (int i = 0 ; i<10; i++){
            Camping newCamping = new Camping("테스트용 캠핑 제목" + i,
                    "테스트용 캠핑 본문" + i, "테스트용 이미지" + i, user, "캠핑 데이터" + i);
            em.persist(newCamping);

            Location newLocation = new Location(3.2, 1.5, "더미 주소", "대구", newCamping);
            em.persist(newLocation);

            Star newStar = new Star("moonrise", "moonSet", 3L, newLocation);
            em.persist(newStar);

            for (int j = 12; j < 15; j++) {
                Weather newWeather = new Weather(
                        "humidity",
                        "weather",
                        "temperature",
                        "maxTemperature",
                        "minTemperature",
                        "rainPercent",
                        j+"00", //1200 1300 1400
                        newLocation
                );
                em.persist(newWeather);
            }



//            for (int j = 0; j < 3; j++) {
//                HashTag newHashTag = new HashTag(newCamping, "테스트 hashTag" + j);
//                em.persist(newHashTag);
//            }
//
//            for (int j = 0; j < 3; j++) {
//                Like newLike = new Like(newCamping, user);
//                em.persist(newLike);
//            }
        }

        for (int i = 0 ; i<10; i++){
            UserMake newUerMake = new UserMake("테스트용 유저만든 제목" + i, "테스트용 유저만든 본문" + i, "테스트용 이미지" + i, user,
                    "유저만든 데이터" + i);
            //UserMake saveUserMake = userMakeRepository.save(newUerMake);
            em.persist(newUerMake);


            Location newLocation = new Location(3.2, 1.5, "더미 주소", "대구", newUerMake);
            em.persist(newLocation);

            Star newStar = new Star("moonrise", "moonSet", 3L, newLocation);
            em.persist(newStar);


            for (int j = 12; j < 15; j++) {
                Weather newWeather = new Weather(
                        "humidity",
                        "weather",
                        "temperature",
                        "maxTemperature",
                        "minTemperature",
                        "rainPercent",
                        j+"00", //1200 1300 1400
                        newLocation
                );
                em.persist(newWeather);
            }



//            for (int j = 0; j < 3; j++) {
//                HashTag newHashTag = new HashTag(newUerMake, "테스트 hashTag" + j);
//                em.persist(newHashTag);
//            }
//
//            for (int j = 0; j < 3; j++) {
//                Like newLike = new Like(newUerMake, user);
//                em.persist(newLike);
//            }
        }


        for (int i = 0; i < 12; i++) {
            StarInfo newStarInfo = new StarInfo("/testImg" + i + ".jpg", "별자리" + i, "테스트 코멘트" + i, Integer.toString(i));
            em.persist(newStarInfo);
        }

        em.getTransaction().commit();

        //===========================================================================================================//

        List<Camping> campingList = campingRepository.findAll();
        for (Camping camping : campingList) {
            System.out.println("camping = " + camping.getLocationName());
        }

        List<UserMake> userMakeList = userMakeRepository.findAll();
        for (UserMake userMake : userMakeList) {
            System.out.println("userMake.getContent() = " + userMake.getContent());
        }
    }

}