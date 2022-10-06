package com.capstone.skone.board.presentaion;

import com.capstone.skone.board.application.BoardService;
import com.capstone.skone.board.application.FileService;
import com.capstone.skone.board.domain.Board;
import com.capstone.skone.board.dto.CreateBoardDto;
import com.capstone.skone.board.dto.CreateFileDto;
import com.capstone.skone.board.util.MD5Generator;
import java.io.File;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
public class BoarderController {

  private final BoardService boardService;
  private final FileService fileService;

  @GetMapping("/board")
  public String viewHome(){
    return"board/board";
  }

  @PostMapping("/post")
  public String write(@RequestParam("file") MultipartFile files, CreateBoardDto createBoardDto) {
    try {
      String origFilename = files.getOriginalFilename();
      String filename = new MD5Generator(origFilename).toString();
      /* 실행되는 위치의 'files' 폴더에 파일이 저장됩니다. */
      String savePath = System.getProperty("user.dir")+"/src/main/resources/static/files";
      /* 파일이 저장되는 폴더가 없으면 폴더를 생성합니다. */
      if (!new File(savePath).exists()) {
        try{
          new File(savePath).mkdir();
        }
        catch(Exception e){
          e.getStackTrace();
        }
      }

      String filePath = savePath + "\\" + origFilename;
      files.transferTo(new File(filePath));

      CreateFileDto createFileDto = new CreateFileDto();
      createFileDto.setOrigFilename(origFilename);
      createFileDto.setFilename(filename);
      createFileDto.setFilePath(filePath);

      Long fileId = fileService.saveFile(createFileDto);
      createBoardDto.setFileId(fileId);
      boardService.createBoard(createBoardDto);
    } catch(Exception e) {
      e.printStackTrace();
    }
    return "redirect:/";
  }

  @GetMapping("/board/{id}")
  public String detailBoard(@PathVariable("id") Long id, Model model){
    Optional<Board> detail = boardService.findByBoard(id);
    CreateFileDto file = fileService.getFile(id);
    model.addAttribute("detail", detail);
    model.addAttribute("file", file);
    return "board/board_details";
  }
}