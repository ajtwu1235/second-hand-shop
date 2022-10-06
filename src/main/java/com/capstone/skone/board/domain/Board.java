package com.capstone.skone.board.domain;

import com.capstone.skone.domain.BaseTimeEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Board extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String nickname;

  private String title;

  private String content;

  private Long price;

  private Long fileId;

  private String option;

  public void update(String title, String content, Long price){
    this.title = title;
    this.content = content;
    this.price = price;
  }

  public void hotDealDiscount(Long price){
    this.price = price-(price/10);
    this.option = "HOT_DEAL_COMPLETION";
  }
}