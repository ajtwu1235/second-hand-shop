package com.capstone.skone.board.application;

import com.capstone.skone.board.domain.HotDealBoard;
import com.capstone.skone.board.infrastructure.HotDealRepository;
import com.capstone.skone.board.dto.DetailHotDealBoardDto;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HotDealService {
  private final HotDealRepository hotDealRepository;

  public Optional<HotDealBoard> getHotDealBoard(Long id){
    return hotDealRepository.findById(id);
  }

  @Transactional(readOnly = true)
  public Page<HotDealBoard> pageList(Pageable pageable) {
    return hotDealRepository.findByOption("HOT_DEAL_COMPLETION", pageable);
  }

  @Transactional
  public List<HotDealBoard> keywordSearch(String title) {
    return hotDealRepository.findByTitle(title);
  }

  public DetailHotDealBoardDto getDetailHotDealBoard(Long id){
    HotDealBoard board = hotDealRepository.getById(id);
    return DetailHotDealBoardDto.builder()
        .id(board.getId())
        .nickname(board.getNickname())
        .title(board.getTitle())
        .content(board.getContent())
        .filename(board.getFilename())
        .price(board.getPrice())
        .option(board.getOption())
        .build();
  }
}