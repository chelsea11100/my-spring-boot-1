package com.example.order_project.controller;

import com.example.order_project.entity.PerformanceRecord;
import com.example.order_project.service.PerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/performance")
public class PerformanceController {
    @Autowired
    private PerformanceService performanceService;

    //获取工作人员绩效记录
    @GetMapping("/staff/{staffId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<List<PerformanceRecord>> getPerformanceRecordsByStaffId(@PathVariable Long staffId) {
        List<PerformanceRecord> records = performanceService.getPerformanceRecordsByStaffId(staffId);
        return ResponseEntity.ok(records);
    }

    //修改绩效记录
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{performanceId}")
    public ResponseEntity<PerformanceRecord> updatePerformance(@PathVariable Long performanceId, @RequestBody PerformanceRecord record) {
        PerformanceRecord updatedRecord = performanceService.updatePerformance(performanceId, record.getWorkload());
        return ResponseEntity.ok(updatedRecord);
    }

    //处理绩效申诉
    @PostMapping("/{performanceId}/appeal")
    public ResponseEntity<Void> handlePerformanceAppeal(@PathVariable Long performanceId, @RequestBody String appealReason) {
        performanceService.handlePerformanceAppeal(performanceId, appealReason);
        return ResponseEntity.noContent().build();
    }
}