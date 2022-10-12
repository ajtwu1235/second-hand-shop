package com.capstone.skone.auction.presentation;

import com.capstone.skone.auction.application.AuctionService;
import com.capstone.skone.auction.domain.Auction;
import com.capstone.skone.auction.domain.BidInfo;
import com.capstone.skone.auction.dto.AuctionDto;
import com.capstone.skone.auction.dto.AuctionIdListDto;
import com.capstone.skone.auction.dto.AuctionRegDto;
import com.capstone.skone.auction.dto.BidForm;
import com.capstone.skone.auth.application.MemberService;
import com.capstone.skone.auth.domain.member.Member;
import com.capstone.skone.auth.infrastructure.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


/**
 * !!지금 현재 TestRepository 사용중임!!
 * JPARepository 나중에 바꿔주기
 */
@Controller
@RequiredArgsConstructor
public class AuctionController {

    private final MemberService memberService;
    private final AuctionService auctionService;

    /**
     * 경매 전체리스트
     * @return
     */
    @GetMapping("/auctions")
    public String auction_list(Model model, Pageable pageable){
        Page<Auction> auctions = auctionService.getAllAuction(pageable);

        // 옥션 ->  옥션DTO변환
        List<AuctionDto> all = auctions.stream().map(o -> new AuctionDto(o))
                .collect(Collectors.toList());

        model.addAttribute("auctions",all);

        return "auction/scone_auction_list";
    }


    /**
     * 이젠 스프링 시큐리티 사용
     * @return
     */
    @GetMapping("/auction/{id}")
    public String auction_detail(@PathVariable Long id , @ModelAttribute BidForm bidForm,
                                 Authentication authentication, Model model){
        Auction auction = auctionService.getSingleAuction(id);
        Member member = (Member) authentication.getPrincipal();


        AuctionDto auctionDto = new AuctionDto(auction);
        model.addAttribute("auction",auctionDto);
        model.addAttribute("member",member);
        //로그인시 화면페이지 이동
        return "auction/scone_auction";
    }

    /**
     *
     * 옥션 자유입찰 처리 메소드
     * @param auctionId
     * @param userId
     * @param bidForm
     * @return
     */
    @PostMapping("/auction/{auctionId}/{userId}")
    public String auction_bid(@PathVariable Long auctionId, @PathVariable Long userId,
                              @ModelAttribute BidForm bidForm,Model model){

        Auction auction = auctionService.getSingleAuction(auctionId);

        Member member = memberService.loadUserByUserId(userId);
        int price = bidForm.getPrice();

        //최소금액 = 1등가격의 10%로 설정함
        //실패시 알림창띄우기
        if(auctionService.isBiding(auction,price)){
            model.addAttribute("msg", "1등 입찰하기엔 금액이 부족합니다.");
            model.addAttribute("url", "/auction/"+auctionId);

            return "alert/alert";
        }

        // 경매 입찰 정보 저장
        auctionService.createBidInfo(auction,member,price);

        model.addAttribute("msg", "축하합니다! 입찰에 성공하셧습니다.");
        model.addAttribute("url", "/auction/"+auctionId);

        return "alert/alert";
    }

    /**
    옥션 상품 등록 페이지
     */
    @GetMapping("/auction/reg")
    public String auction_reg_form(@ModelAttribute AuctionRegDto auctionRegDto){

        return "auction/scone_auction_regist";
    }

    @PostMapping("/auction/reg")
    @ResponseBody
    public String auction_reg(@ModelAttribute AuctionRegDto auctionRegDto){

        // 여기서 옥션 정보 저장하는 로직 필요


        return "포스팅 성공";
    }


    @PostMapping("/auctions")
    @ResponseBody
    public String auction_delete(@ModelAttribute AuctionIdListDto aucts){

        List<Long> auc_list = aucts.getAuc_list();

        for(Long auc:auc_list){
            System.out.println("auc = " + auc);
        }

        return "삭제 성공";
    }



}
