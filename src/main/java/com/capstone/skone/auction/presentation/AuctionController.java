package com.capstone.skone.auction.presentation;

import com.capstone.skone.auction.application.AuctionService;
import com.capstone.skone.board.application.FileService;
import com.capstone.skone.board.dto.CreateFileDto;
import com.capstone.skone.board.util.MD5Generator;
import com.capstone.skone.auction.domain.Auction;
import com.capstone.skone.auction.domain.BidInfo;
import com.capstone.skone.auction.dto.AuctionDto;
import com.capstone.skone.auction.dto.AuctionIdListDto;
import com.capstone.skone.auction.dto.AuctionRegDto;
import com.capstone.skone.auction.dto.BidForm;
import com.capstone.skone.auth.application.MemberService;
import com.capstone.skone.auth.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;


import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
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
    private final FileService fileService;

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
    public String auction_reg(AuctionRegDto auctionRegDto,Authentication auth,@RequestParam("file") MultipartFile files){


        System.out.println("auth = " + auth.getName());
        System.out.println("auctionRegDto = " + auctionRegDto);

        try {
            String origFilename = files.getOriginalFilename();
            String filename = new MD5Generator(origFilename).toString();
            /* 실행되는 위치의 'files' 폴더에 파일이 저장됩니다. */
            String savePath = System.getProperty("user.dir") + "/src/main/resources/static/files";
            /* 파일이 저장되는 폴더가 없으면 폴더를 생성합니다. */
            if (!new File(savePath).exists()) {
                try {
                    new File(savePath).mkdir();
                } catch (Exception e) {
                    e.getStackTrace();
                }
            }

            String filePath = savePath + "\\" + files.getOriginalFilename();
            files.transferTo(new File(filePath));

            CreateFileDto createFileDto = new CreateFileDto();
            createFileDto.setOrigFilename(origFilename);
            createFileDto.setFilename(filename);
            createFileDto.setFilePath(filePath);

            String fileName = fileService.saveFile(createFileDto);
            auctionRegDto.setFileName(fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<BidInfo> arr= new ArrayList<>();
        Member member = memberService.loadUserByUsername(auth.getName());
        Auction auction = Auction.builder()
                .member(member)
                .title(auctionRegDto.getItemName())
                .content(auctionRegDto.getItemDetail())
                .fileName(auctionRegDto.getFileName())
                .initPrice(auctionRegDto.getPrice())
                .bidInfos(arr)
                .build();
        auctionService.createAuction(auction);
        //초기 입찰가 설정 부분
        BidInfo bidInfo = new BidInfo();
        //초기 입찰 판매자로 설정
        bidInfo.setUserName(auction.getMember().getUsername());
        bidInfo.setBid_Price(Integer.parseInt(auctionRegDto.getPrice()));
        bidInfo.setAuction(auction);
        arr.add(bidInfo);
        auctionService.createBidInfo(bidInfo);



        return "redirect:/";
    }

    @PostMapping("/auctions/del")
    public String auction_delete(@ModelAttribute AuctionIdListDto aucts, Authentication auth, HttpServletRequest request) throws Exception{

        List<Long> auc_list = aucts.getAuc_list();
        String loginUser = auth.getName();

        System.out.println("auc_list = " + auc_list);
        for(Long auc:auc_list){
            Auction auction = auctionService.selectAuction(auc);
            System.out.println("auc = " + auc);
            if(!ObjectUtils.isEmpty(auction)){
                String writer = auction.getMember().getEmail();
                // 로그인 정보와 경매글 작성자가 동일한 경우 게시물 삭제처리
                if(StringUtils.equals(writer,loginUser)){
                    auctionService.deleteAuction(auc);
                    System.out.println("auction : "+auc+" ----삭제 완료----");
                }
            }
            else{
                System.out.println("----경매글이 존재하지 않습니다.-----");
            }
        }
        System.out.println("-----작성자가 아닙니다.-----");

        return "redirect:/";
    }

}
