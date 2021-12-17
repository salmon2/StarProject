package com.sparta.StarProject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class CommentResponseDto {
     private Long userId;
     private String nickname;
     private String date;

     public CommentResponseDto(Long userId, String nickname, String date, String comment) {
          this.userId = userId;
          this.nickname = nickname;
          this.date = date;
          this.comment = comment;
     }

     private String comment;

     public CommentResponseDto(String nickname, String date, String comment) {
          this.nickname = nickname;
          this.date = date;
          this.comment = comment;
     }
}
