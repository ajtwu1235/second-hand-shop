package com.capstone.skone.report.infrastructure;

import com.capstone.skone.report.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
