package com.capstone.skone.auction.auth.board.dto;

import com.capstone.skone.auction.auth.board.domain.File;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateFileDto {
  private Long id;
  private String origFilename;
  private String filename;
  private String filePath;

  public File toEntity() {
    return File.builder()
        .id(id)
        .origFilename(origFilename)
        .filename(filename)
        .filePath(filePath)
        .build();
  }
}