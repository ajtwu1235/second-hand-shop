package com.capstone.skone.report.presentaion;

import com.capstone.skone.report.application.ReportService;
import com.capstone.skone.report.dto.ReportDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @PostMapping("/report")
    public String createReport(ReportDto reportDto){
        reportService.setReport(reportDto);
        return "redirect:/";
    }
}
