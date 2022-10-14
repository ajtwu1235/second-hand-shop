package com.capstone.skone.auction.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
public class AuctionIdListDto {

    private List<Long> auc_list = new ArrayList<>();
}
