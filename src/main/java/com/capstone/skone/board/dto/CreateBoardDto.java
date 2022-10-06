package com.capstone.skone.board.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class CreateBoardDto {

  private String nickname;
  private String title;
  private String content;
  private Long price;
  private Long fileId;
  private String option;
}
