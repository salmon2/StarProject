package com.sparta.StarProject.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.StarProject.domain.board.Board;
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

    @Column(unique=true)
    private String username;

    private String password;

    @Column(unique=true)
    private String nickname;

    public User(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
    }

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Board> boardList = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Bookmark> myBookmark;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    private Set<Like> like;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Comment> commentList = new ArrayList<>();

    public void updateUser(String nickname, String password) {
        this.nickname = nickname;
        this.password = password;
    }
}
