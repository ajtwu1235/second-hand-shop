package com.capstone.skone.auth.board.domain;

import com.capstone.skone.domain.BaseTimeEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Board extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "user_id")
  private Long userId;

  //작성자 이름...?
  private String nickname;

  private String title;

  private String content;

  private Long price;

  private String filename;

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