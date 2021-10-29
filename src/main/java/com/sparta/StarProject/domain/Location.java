package com.sparta.StarProject.domain;

import com.sparta.StarProject.domain.board.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Location {
    @Id @GeneratedValue
    private Long id;

    private Double xLocation;
    private Double yLocation;

    private String address;
    private String cityName;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @OneToOne(mappedBy = "location", fetch = FetchType.LAZY)
    private Star star;

    @OneToMany(mappedBy = "location", fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    private List<Weather> weatherList = new ArrayList<>();


    public Location(Double xLocation, Double yLocation, String address, String cityName, Board board) {
        this.xLocation = xLocation;
        this.yLocation = yLocation;
        this.address = address;
        this.cityName = cityName;
        this.board = board;
    }
}
