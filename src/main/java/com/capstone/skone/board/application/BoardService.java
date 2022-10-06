package com.capstone.skone.board.application;

import com.capstone.skone.board.commen.HotDealTimer;
import com.capstone.skone.board.domain.Board;
import com.capstone.skone.board.dto.CreateBoardDto;
import com.capstone.skone.board.dto.HotDealBoardDto;
import com.capstone.skone.board.dto.MainBoardDto;
import com.capstone.skone.board.dto.UpdateBoardDto;
import com.capstone.skone.board.infrastructure.BoardRepository;
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

  public Optional<Board> findByBoard(Long id) {
    return boardRepository.findById(id);
  }

  @Transactional
  public void createBoard(CreateBoardDto createBoardDto) {
    createBoardDto.setOption("HOT_DEAL");
    boardRepository.save(Board.builder()
        .nickname(createBoardDto.getNickname())
        .title(createBoardDto.getTitle())
        .content(createBoardDto.getContent())
        .price(createBoardDto.getPrice())
        .fileId(createBoardDto.getFileId())
        .option(createBoardDto.getOption())
        .build()
    );
    //7일 = 604800000
    if(createBoardDto.getOption().equals("HOT_DEAL")){
      new HotDealTimer(boardRepository);
    }
  }

  public List<Board> getBoards() {
    return boardRepository.findAll();
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

  public List<MainBoardDto> getAllBoard(List<Board> allBoards) {
    List<MainBoardDto> mainBoards = new ArrayList<>();
    for (Board allBoard : allBoards) {
      mainBoards.add(
          MainBoardDto.builder()
              .id(allBoard.getId())
              .title(allBoard.getTitle())
              .fileName(fileService.getFile(allBoard.getFileId()).getOrigFilename())
              .NickName(allBoard.getNickname())
              .price(allBoard.getPrice())
              .build()
      );
    }
    return mainBoards;
  }

  public List<HotDealBoardDto> hotDealDiscount() {
    List<HotDealBoardDto> hotDealDiscountCompletion = new ArrayList<>();
    for (Board hotDeal : boardRepository.findByOption("HOT_DEAL_COMPLETION")) {
       hotDeal.hotDealDiscount(hotDeal.getPrice());
      hotDealDiscountCompletion.add(
          HotDealBoardDto.builder()
              .title(hotDeal.getTitle())
              .price(hotDeal.getPrice())
              .content(hotDeal.getContent())
              .build()
          );
    }
    return hotDealDiscountCompletion;
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