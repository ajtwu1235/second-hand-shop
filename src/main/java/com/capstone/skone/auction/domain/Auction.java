package com.capstone.skone.auction.domain;

import com.capstone.skone.board.domain.Board;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
//잠깐 Setter 열어둠  JPA로할땐 닫고 Create메소드작성
@Setter
public class Auction {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name ="auction_id")
  private Long auctionNumber;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name="post_bord_id")
  private Board postBordNumber;

  @OneToMany(mappedBy = "auction")
  private List<BidInfo> bidInfos = new ArrayList<>();

  private LocalDateTime left_time;

  //양방향 편의메소드  Auction-> BidInfo
  public void addBidInfos(BidInfo bidInfo){

    System.out.println("양방향 편의메소드  Auction-> BidInfo");
    this.bidInfos.add(bidInfo);
    if(bidInfo.getAuction()!=this){
      bidInfo.setAuction(this);
    }

  }

}
