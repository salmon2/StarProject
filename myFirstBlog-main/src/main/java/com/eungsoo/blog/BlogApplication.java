package com.eungsoo.blog;

import com.eungsoo.blog.models.Contents;
import com.eungsoo.blog.repository.ContentsRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.annotation.PostConstruct;
import java.util.TimeZone;


@EnableJpaAuditing
@SpringBootApplication
public class BlogApplication {
    public static void main(String[] args) {

        SpringApplication.run(BlogApplication.class, args);
    }

    //    # main 아래에 삽입
    @PostConstruct
    public void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }

    @Bean
    public CommandLineRunner demo(ContentsRepository repository) {
        return (args) -> {
            repository.save(new Contents("항해99", "선원1", "어푸어푸"));
        };
    }

}
