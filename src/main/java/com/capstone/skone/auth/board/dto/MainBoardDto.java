package com.capstone.skone.auth.board.dto;

import com.capstone.skone.auth.board.domain.File;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MainBoardDto {

  private Long id;
  private String title;
  private String fileName;
  private String NickName;
  private File file;
  private Long price;
}
