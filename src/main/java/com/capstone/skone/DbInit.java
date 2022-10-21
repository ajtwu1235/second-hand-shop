package com.capstone.skone;

import com.capstone.skone.auction.application.AuctionService;
import com.capstone.skone.auction.domain.Auction;
import com.capstone.skone.auction.domain.BidInfo;
import com.capstone.skone.auction.auth.application.MemberService;
import com.capstone.skone.auction.auth.domain.member.Member;
import com.capstone.skone.auction.auth.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DbInit {

    private final AuctionService auctionService;
    private final MemberService memberService;

    @PostConstruct
    private void AuctionInit(){

        MemberDto memberDto = new MemberDto();
        memberDto.setEmail("1234");
        memberDto.setPassword("1234");
        memberDto.setAuth("ROLE_MEMBER");

        Long memberId = memberService.joinUser(memberDto);

        Member member = memberService.loadUserByUserId(memberId);

        for(int i=0;i<85;i++){

            List<BidInfo> arr= new ArrayList<>();

            Auction auction = Auction.builder()
                    .member(member)
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
