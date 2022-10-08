package com.capstone.skone.board.commen;

import com.capstone.skone.board.domain.Board;
import com.capstone.skone.board.domain.HotDealBoard;
import com.capstone.skone.board.infrastructure.BoardRepository;
import com.capstone.skone.board.infrastructure.FileRepository;
import com.capstone.skone.board.infrastructure.HotDealRepository;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HotDealTimer {

  Timer timer = new Timer();

  public HotDealTimer(BoardRepository boardRepository, HotDealRepository hotDealRepository, FileRepository fileRepository) {
    List<Board> boards = boardRepository.findByOption("HOT_DEAL");
    TimerTask task = new TimerTask() {
      @Override
      public void run() {
        for (Board board : boards) {
          board.hotDealDiscount(board.getPrice());
          hotDealRepository.save(
              HotDealBoard.builder()
                  .id(board.getId())
                  .nickname(board.getNickname())
                  .title(board.getTitle())
                  .content(board.getContent())
                  .filename(board.getFilename())
                  .price(board.getPrice())
                  .option(board.getOption())
                  .build()
          );
          boardRepository.deleteById(board.getId());
          fileRepository.deleteById(board.getId());
        }
      }
    };

    long delay = 3000;
    timer.schedule(task, delay);
  }
}