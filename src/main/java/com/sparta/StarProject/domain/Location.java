package com.sparta.StarProject.domain;

import com.sparta.StarProject.domain.board.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Location {
    @Id @GeneratedValue
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @OneToOne(mappedBy = "location", fetch = FetchType.LAZY)
    private Star star;

    private Double xLocation;
    private Double yLocation;

    private String address;
    private String cityName;

    public Location(Double xLocation, Double yLocation, String address, String cityName, Board board) {
        this.xLocation = xLocation;
        this.yLocation = yLocation;
        this.address = address;
        this.cityName = cityName;
        this.board = board;
    }
}
