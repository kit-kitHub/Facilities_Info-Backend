package com.kitHub.Facilities_info.controller;

import com.kitHub.Facilities_info.domain.Report.Report;
import com.kitHub.Facilities_info.domain.Report.Block;

import com.kitHub.Facilities_info.dto.CreateBlockRequest;
import com.kitHub.Facilities_info.dto.CreateReportRequest;
import com.kitHub.Facilities_info.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @PostMapping("/{contentType}/{contentId}")
    public Report createReport(@PathVariable String contentType,
                               @PathVariable Long contentId,
                               @RequestBody CreateReportRequest request) {
        return reportService.createReport(contentType, contentId, request);
    }


    @GetMapping("/user/{userId}")
    public List<Report> getReportsByUser(@PathVariable Long userId) {
        return reportService.getReportsByUser(userId);
    }

    @GetMapping
    public List<Report> getAllReports() {
        return reportService.getAllReports();
    }
}
