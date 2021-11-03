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

    private String moonrise;    //월출
    private String moonSet;     //월몰
    private Long starGazing;

    public Star(String moonrise, String moonSet, Long starGazing, Location location) {
        this.moonrise = moonrise;
        this.moonSet = moonSet;
        this.location = location;
        this.starGazing = starGazing;
    }

}
