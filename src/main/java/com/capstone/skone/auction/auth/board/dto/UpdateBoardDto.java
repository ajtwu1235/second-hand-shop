package com.capstone.skone.auction.auth.board.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateBoardDto {
  private String title;
  private String content;
  private Long price;
}