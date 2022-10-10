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
    private LocalDateTime left_time;
    private String fileName;
    private String userName;

    public AuctionDto(Auction auction){
        this.auctionId =auction.getAuctionNumber();
        this.itemName = auction.getPostBordNumber().getTitle();
        this.content = auction.getPostBordNumber().getContent();
        this.bidInfo = auction.getBidInfos().stream()
                .map(o->new AuctionRequestDto(o.getAuction().getAuctionNumber(),o.getUserName(),o.getBid_Price()))
                .collect(Collectors.toList());
        Collections.sort(bidInfo,(a,b)->b.getBid_price()-a.getBid_price());
        this.left_time=auction.getLeft_time();
        this.fileName = auction.getPostBordNumber().getFilename();
        this.userName = auction.getPostBordNumber().getNickname();
    }

}
