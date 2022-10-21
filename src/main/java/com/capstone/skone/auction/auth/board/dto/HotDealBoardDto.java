package com.capstone.skone.auction.auth.board.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HotDealBoardDto {

  private Long id;
  private String nickname;
  private String title;
  private String content;
  private Long price;
  private Long fileId;
  private String option;
}
