package com.capstone.skone.board.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DetailBoardDto {

  private Long id;
  private String nickname;
  private String title;
  private String content;
  private Long price;
  private String filename;
  private String option;
}