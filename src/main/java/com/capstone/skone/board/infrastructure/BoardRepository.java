package com.capstone.skone.board.infrastructure;

import com.capstone.skone.board.domain.Board;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
  List<Board> findByTitleContaining(String title);
}
