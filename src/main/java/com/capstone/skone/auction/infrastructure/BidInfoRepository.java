package com.capstone.skone.auction.infrastructure;

import com.capstone.skone.auction.domain.BidInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BidInfoRepository extends JpaRepository<BidInfo, Long> {
}
