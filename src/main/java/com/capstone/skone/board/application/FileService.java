package com.capstone.skone.board.application;

import com.capstone.skone.board.domain.File;
import com.capstone.skone.board.dto.request.CreateFileRequest;
import com.capstone.skone.board.infrastructure.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FileService {
  private final FileRepository fileRepository;

  @Transactional
  public Long saveFile(CreateFileRequest fileDto) {
    return fileRepository.save(fileDto.toEntity()).getId();
  }

  @Transactional
  public CreateFileRequest getFile(Long id) {
    File file = fileRepository.findById(id).get();

    return CreateFileRequest.builder()
        .id(id)
        .origFilename(file.getOrigFilename())
        .filename(file.getFilename())
        .filePath(file.getFilePath())
        .build();
  }

  public String getFilePath(CreateFileRequest createFileRequest){
    return createFileRequest.getFilePath();
  }
}