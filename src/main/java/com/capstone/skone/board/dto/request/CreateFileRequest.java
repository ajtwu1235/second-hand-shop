package com.capstone.skone.board.dto.request;

import com.capstone.skone.board.domain.File;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class CreateFileRequest {
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

  @Builder
  public CreateFileRequest(Long id, String origFilename, String filename, String filePath) {
    this.id = id;
    this.origFilename = origFilename;
    this.filename = filename;
    this.filePath = filePath;
  }
}
