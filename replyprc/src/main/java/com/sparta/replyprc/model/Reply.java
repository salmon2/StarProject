package com.sparta.replyprc.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter // get 함수를 일관적으로 만들어줍니다.
@NoArgsConstructor // 기본생성자를 만들어줍니다.
@Entity // DB 테이블 역할을 합니다.


public class Reply {
    //ID 자동으로 생성되고 증가됩니다.
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(nullable = false)
    private Long id;
    @Column(nullable = false)
    private Long postid;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String reply;
    @Column(nullable = false)
    private Long userId;

    public Reply(Long postid, String username, String reply){
        this.postid = postid;
        this.username = username;
        this.reply = reply;
    }

    public Reply(ReplyRequstDto requestDto,Long userId){
        this.postid = requestDto.getPostid();
        this.reply = requestDto.getReply();
        this.username = requestDto.getUsername();
        this.userId = uesrId;
    }

}
