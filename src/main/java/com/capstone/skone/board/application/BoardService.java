package com.capstone.skone.board.application;

import com.capstone.skone.auth.application.MemberService;
import com.capstone.skone.board.commen.HotDealTimer;
import com.capstone.skone.board.domain.Board;
import com.capstone.skone.board.dto.CreateBoardDto;
import com.capstone.skone.board.dto.DetailBoardDto;
import com.capstone.skone.board.dto.UpdateBoardDto;
import com.capstone.skone.board.infrastructure.BoardRepository;
import com.capstone.skone.board.infrastructure.FileRepository;
import com.capstone.skone.board.infrastructure.HotDealRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {

  private final BoardRepository boardRepository;
  private final FileService fileService;
  private final FileRepository fileRepository;
  private final HotDealRepository hotDealRepository;

  public Optional<Board> findByBoard(Long id) {
    return boardRepository.findById(id);
  }

  @Transactional
  public void createBoard(CreateBoardDto createBoardDto) {
    boardRepository.save(Board.builder()
        .id(createBoardDto.getId())
        .nickname(createBoardDto.getNickname())
        .title(createBoardDto.getTitle())
        .content(createBoardDto.getContent())
        .price(createBoardDto.getPrice())
        .filename(createBoardDto.getFilename())
        .option(createBoardDto.getOption())
        .userEmail(createBoardDto.getUserEmail())
        .build()
    );
    //7일 = 604800000
    if (createBoardDto.getOption().equals("HOT_DEAL")) {
      new HotDealTimer(boardRepository, hotDealRepository, fileRepository);
    }
  }

  public List<String> findByTiles(String nickname){
    List<String> titles = new ArrayList<>();
    for(Board board : boardRepository.findByNickname(nickname)){
      System.out.println("================");
      System.out.println(board.getTitle());
      titles.add(board.getTitle());
    }
    return titles;
  }

  public Board selectBoard(Long id){
    Board  board = boardRepository.findById(id).orElse(null);
    System.out.println("board = " + board);
    return board;
  }

  @Transactional
  public void updateBoard(Long id, UpdateBoardDto updateBoardRequest) {
    Board board = boardRepository.findById(id).orElseThrow(() -> {
      throw new NullPointerException();
    });
    board.update(updateBoardRequest.getTitle(), updateBoardRequest.getContent(),
        updateBoardRequest.getPrice());
  }

  public void deleteBoard(Long id) {
    boardRepository.deleteById(id);
  }

  public DetailBoardDto getDetailBoard(Long id){
    Board board = boardRepository.getById(id);
    return DetailBoardDto.builder()
        .id(board.getId())
        .nickname(board.getNickname())
        .title(board.getTitle())
        .content(board.getContent())
        .filename(fileService.getFile(board.getId()).getOrigFilename())
        .price(board.getPrice())
        .option(board.getOption())
        .build();
  }

  @Transactional(readOnly = true)
  public Page<Board> pageList(Pageable pageable) {
    return boardRepository.findAll(pageable);
  }

  @Transactional
  public List<Board> keywordSearch(String title) {
    return boardRepository.findByTitleContaining(title);
  }
}