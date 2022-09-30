package com.capstone.skone.board.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateBoardRequest {

  private String nickname;
  private String title;
  private String content;
  private Long price;
  private Long fileId;
}
