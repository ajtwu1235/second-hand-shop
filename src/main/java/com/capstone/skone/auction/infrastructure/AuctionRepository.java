package com.capstone.skone.auction.infrastructure;

import com.capstone.skone.auction.domain.Auction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

//이건 JPA -> h2 연결하고 나서 쓰자
public interface AuctionRepository extends JpaRepository<Auction,Long> {

    @Override
    Page<Auction> findAll(Pageable pageable);
}
