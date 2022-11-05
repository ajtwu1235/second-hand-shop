package com.capstone.skone.report.application;

import com.capstone.skone.report.domain.Report;
import com.capstone.skone.report.dto.ReportDto;
import com.capstone.skone.report.infrastructure.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;

    public void setReport(ReportDto report){
        reportRepository.save(
            Report.builder()
                .board_id(report.getBoard_id())
                .title(report.getTitle())
                .reason(report.getReason())
                .content(report.getContent())
                .build()
        );
    }
}