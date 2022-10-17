package com.capstone.skone.auth.board.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateBoardDto {

  private Long id;
  private String nickname;
  private String title;
  private String content;
  private Long price;
  private String filename;
  private String option;
  private String userEmail;
}