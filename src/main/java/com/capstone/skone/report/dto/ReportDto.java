package com.capstone.skone.report.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReportDto {
    private Long board_id;
    private String title;
    private String reason;
    private String content;
}
