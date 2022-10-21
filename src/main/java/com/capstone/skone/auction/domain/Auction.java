package com.capstone.skone.auction.domain;

import com.capstone.skone.auth.domain.member.Member;
import com.capstone.skone.chat.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
//잠깐 Setter 열어둠  JPA로할땐 닫고 Create메소드작성
@Setter
public class Auction extends BaseTimeEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name ="auction_id")
  private Long auctionNumber;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id")
  private Member member;

  private String title;

  private String content;

  private String fileName;

  private String initPrice;

  @OneToMany(mappedBy = "auction", cascade = CascadeType.ALL)
  private List<BidInfo> bidInfos = new ArrayList<>();

  //테스트용
  @Builder
  public Auction(Member member, String title, String content,List<BidInfo> bidInfos, String fileName, String initPrice) {
    this.member = member;
    this.title = title;
    this.content = content;
    this.fileName =  fileName;
    this.bidInfos =bidInfos;
    this.initPrice = initPrice;
  }

  public Auction() {

  }

  //양방향 편의메소드  Auction-> BidInfo
  public void addBidInfos(BidInfo bidInfo){

    System.out.println("양방향 편의메소드  Auction-> BidInfo");
    this.bidInfos.add(bidInfo);
    if(bidInfo.getAuction()!=this){
      bidInfo.setAuction(this);
    }

  }

}
