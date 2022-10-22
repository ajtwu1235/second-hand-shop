package com.capstone.skone.auction.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
/**
 * 옥션 상품 등록용 DTO
 */
@Data
@Builder
public class AuctionRegDto {
    private String itemName;
    private String itemDetail;
    //날짜
    private String month;
    private String day;
    //초기 입찰금액
    private String price;
    private String fileName;
}
