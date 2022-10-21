package com.capstone.skone.auction.auth.board.infrastructure;

import com.capstone.skone.auction.auth.board.domain.HotDealBoard;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotDealRepository extends JpaRepository<HotDealBoard, Long> {
  List<HotDealBoard> findByTitle(String title);
  Page<HotDealBoard> findByOption(String option, Pageable pageable);
}
