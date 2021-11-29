package com.sparta.StarProject.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfigurerimpl implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                        "http://startprojecttest.s3-website.ap-northeast-2.amazonaws.com",
                        "http://localhost:3000",
                        "http://localhost:8080",
                        "https://stellakorea.co.kr",
                        "https://www.stellakorea.co.kr",
                        "http://localhost:8000")

                .allowedMethods("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH")
                .allowCredentials(true);
    }


}