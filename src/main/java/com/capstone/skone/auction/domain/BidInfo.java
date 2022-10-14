package com.capstone.skone.auction.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class BidInfo {

    @Id
    @GeneratedValue
    @Column(name = "bid_id")
    private Long bidNumber;

    private String userName;

    private int bid_Price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="auction_id")
    private Auction auction;

    //양방향 편의메소드 BidInfo -> Auction
    public void setAuction(Auction auction){
        if(this.auction !=null){
            this.auction.getBidInfos().remove(this);
        }

        this.auction =auction;
        auction.getBidInfos().add(this);
    }

}
