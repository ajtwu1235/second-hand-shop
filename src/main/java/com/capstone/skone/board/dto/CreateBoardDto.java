package com.capstone.skone.board.dto;

import com.capstone.skone.board.domain.File;
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
}