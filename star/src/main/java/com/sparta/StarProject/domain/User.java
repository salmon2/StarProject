package com.sparta.StarProject.domain;

import com.sparta.StarProject.domain.board.Board;
import com.sparta.StarProject.dto.SignUpRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class User {
    @Id @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String nickname;

    private Long kakaoId;
    public User(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
    }

    public User(String username, String password, String nickname, Long kakaoId) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.kakaoId = kakaoId;
    }

    public User(SignUpRequestDto signUpRequestDto){
        this.username = signUpRequestDto.getUsername();
        this.password = signUpRequestDto.getPassword();
        this.nickname = signUpRequestDto.getNickname();
        this.kakaoId = null;
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<Board> boardList = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    private Set<Like> like;
}