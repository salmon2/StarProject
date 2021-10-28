package com.sparta.StarProject.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Star {
    @Id @GeneratedValue
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    @JsonIgnore
    private Location location;

    private String moonrise;
    private String moonSet;
    private String visibility;
    private String humidity;
    private String weather;
    private String temperature;

    private Long StarGazing;

    public Star(String moonrise, String moonSet, String visibility, String humidity, String weather,
                String temperature, Long starGazing, Location location) {
        this.moonrise = moonrise;
        this.moonSet = moonSet;
        this.visibility = visibility;
        this.humidity = humidity;
        this.weather = weather;
        this.temperature = temperature;
        this.location = location;
        this.StarGazing = starGazing;
    }

}