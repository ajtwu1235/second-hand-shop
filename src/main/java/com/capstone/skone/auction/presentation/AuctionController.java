package com.capstone.skone.auction.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import skane.skaneshop.Auction.application.AuctionService;
import skane.skaneshop.Auction.dto.AuctionDto;
import skane.skaneshop.Auction.dto.AuctionIdListDto;
import skane.skaneshop.Auction.dto.AuctionRegDto;
import skane.skaneshop.Auction.dto.BidForm;
import skane.skaneshop.Auction.infra.AuctionTestRepository;
import skane.skaneshop.Auction.infra.BidInfoTestRepository;
import skane.skaneshop.board.application.FileStore;
import skane.skaneshop.domain.*;
import skane.skaneshop.login.application.ArgumentResolver.Login;
import skane.skaneshop.login.dto.request.Member;
import skane.skaneshop.login.infrastructure.MemberRepository;

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


    private final AuctionTestRepository auctionTestRepository;
    private final MemberRepository memberRepository;
    private final BidInfoTestRepository bidInfoTestRepository;
    private final AuctionService auctionService;
    private final FileStore fileStore;
    /**
     * 경매 전체리스트
     * @return
     */
    @GetMapping("/auctions")
    public String auction_list(Model model){

        List<Auction> auctions = auctionTestRepository.findAll();

        // 옥션 ->  옥션DTO변환
        List<AuctionDto> all = auctions.stream().map(o -> new AuctionDto(o))
                .collect(Collectors.toList());

        model.addAttribute("auctions",all);

        return "auction/scone_auction_list";
    }


    /**
     * 경매 물품 상세페이지   저 @Login어노테이션도  Test용 Member임. 추후에 domain User로 바꿔야댐
     * @return
     */
    @GetMapping("/auction/{id}")
    public String auction_detail(@PathVariable Long id , @Login Member loginMember,
                                 @ModelAttribute BidForm bidForm, Model model){
        Auction auction = auctionTestRepository.findById(id);

        AuctionDto auctionDto = new AuctionDto(auction);

        model.addAttribute("auction",auctionDto);
        model.addAttribute("member",loginMember);
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

        Auction auction =auctionTestRepository.findById(auctionId);
        Member member = memberRepository.findById(userId);

        List<BidInfo> bidInfos = auction.getBidInfos();
        Collections.sort(bidInfos,(a, b)->b.getBid_Price()-a.getBid_Price());

        int first = bidInfos.get(0).getBid_Price();

        //최소금액 = 1등가격의 5%로 설정함
        //실패시 알림창띄우기
        if(bidForm.getPrice()<first+first/20){

            model.addAttribute("msg", "1등 입찰하기엔 금액이 부족합니다.");
            model.addAttribute("url", "/auction/"+auctionId);

            return "alert/alert";
        }

        System.out.println(bidForm.getPrice());
        System.out.println(first+first/20);

        BidInfo bidInfo = new BidInfo();

        bidInfo.setAuction(auction);
        bidInfo.setBid_Price(bidForm.getPrice());
        bidInfo.setUserName(member.getName());
        bidInfoTestRepository.save(bidInfo);

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
