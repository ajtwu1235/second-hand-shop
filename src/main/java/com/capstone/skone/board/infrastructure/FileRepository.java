package com.capstone.skone.board.infrastructure;

import com.capstone.skone.board.domain.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {
}
