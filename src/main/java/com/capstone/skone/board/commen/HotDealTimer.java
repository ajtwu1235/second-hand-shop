package com.capstone.skone.board.commen;

import com.capstone.skone.board.domain.Board;
import com.capstone.skone.board.infrastructure.BoardRepository;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HotDealTimer {

  Timer timer = new Timer();


  public HotDealTimer(BoardRepository boardRepository){
    List<Board> board = boardRepository.findByOption("HOT_DEAL");
    TimerTask task = new TimerTask() {
      @Override
      public void run() {
        board.get(0).hotDealDiscount(board.get(0).getPrice());
      boardRepository.save(
          Board.builder()
              .id(board.get(0).getId())
              .price(board.get(0).getPrice())
              .option(board.get(0).getOption())
              .build()
      );
      }
    };
    long delay = 3000;
    timer.schedule(task, delay);
  }
}