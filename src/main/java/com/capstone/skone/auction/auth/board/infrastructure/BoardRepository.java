package com.capstone.skone.auction.auth.board.infrastructure;

import com.capstone.skone.auction.auth.board.domain.Board;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
  List<Board> findByTitleContaining(String title);
  List<Board> findByOption(String option);
  Page<Board> findAll(Pageable pageable);
}
