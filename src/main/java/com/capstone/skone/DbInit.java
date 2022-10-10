package com.capstone.skone;

import com.capstone.skone.auction.application.AuctionService;
import com.capstone.skone.auction.domain.Auction;
import com.capstone.skone.auction.domain.BidInfo;
import com.capstone.skone.auction.infrastructure.AuctionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DbInit {

    private final AuctionService auctionService;

    @PostConstruct
    private void AuctionInit(){


        for(int i=0;i<85;i++){

            List<BidInfo> arr= new ArrayList<>();

            Auction auction = Auction.builder()
                    .userName("유저" + i)
                    .title("제목" + i)
                    .content("내용" + i)
                    .bidInfos(arr)
                    .build();

            auctionService.createAuction(auction);

            BidInfo bidInfo = new BidInfo();
            bidInfo.setUserName("구매자"+i);
            bidInfo.setBid_Price(i*1000);
            bidInfo.setAuction(auction);
            arr.add(bidInfo);
            auctionService.createBidInfo(bidInfo);
        }
    }

}
