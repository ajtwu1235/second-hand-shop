package com.capstone.skone.auction.auth.board.infrastructure;

import com.capstone.skone.auction.auth.board.domain.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {
}
