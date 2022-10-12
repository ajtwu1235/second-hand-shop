package com.capstone.skone.auction.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 옥션 상품 등록용 DTO
 */
@Data
public class AuctionRegDto {
    private String itemName;
    private String itemDetail;
    //날짜
    private int month;
    private int day;
    //초기 입찰금액
    private int price;

    private List<MultipartFile> images;
}
