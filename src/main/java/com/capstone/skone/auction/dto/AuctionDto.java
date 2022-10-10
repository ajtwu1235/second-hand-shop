package com.capstone.skone.auction.dto;

import com.capstone.skone.auction.domain.Auction;
import com.capstone.skone.board.domain.File;
import lombok.Data;


import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class AuctionDto {

    private Long auctionId;
    private String itemName;
    //상품 설명
    private String content;
    private List<AuctionRequestDto> bidInfo;
    private String fileName;
    private String userName;
    private LocalDateTime left_time;

    // Auction -> AuctionDto 변환 메소드
    public AuctionDto(Auction auction){
        this.auctionId =auction.getAuctionNumber();
        this.itemName = auction.getTitle();
        this.content = auction.getContent();
        this.bidInfo = auction.getBidInfos().stream()
                .map(o->new AuctionRequestDto(o.getAuction().getAuctionNumber(),o.getUserName(),o.getBid_Price()))
                .collect(Collectors.toList());
        Collections.sort(bidInfo,(a,b)->b.getBid_price()-a.getBid_price());
        this.fileName = auction.getFileName();
        this.userName = auction.getUserName();
        this.left_time=auction.getCreatedDate().plusDays(7L);
    }

}
