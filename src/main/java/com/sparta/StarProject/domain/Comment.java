package com.sparta.StarProject.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.StarProject.domain.board.Board;
import com.sparta.StarProject.domain.board.Timestamped;
import com.sparta.StarProject.dto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@NoArgsConstructor
@Setter
@Getter
@Entity
public class Comment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String comments;

    @Column
    private String nickname;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "board_id",nullable = false)
    private Board board;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    public Comment(String comments, String nickname, Board board, User user) {
        this.comments = comments;
        this.nickname = nickname;
        this.board = board;
        this.user = user;
    }

    public Comment(String comment, String nickname) {
        this.comments = comment;
        this.nickname = nickname;
    }
//    public Comment(String comments, Board board, User user){
//        this.comments = comments;
//        this.board = board;
//        this.user = user;
//    }
    public Comment update(CommentRequestDto commentRequestDto){
        this.comments = commentRequestDto.getComment();
        return this;
    }
}
