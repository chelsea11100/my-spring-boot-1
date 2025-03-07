package com.example.order_project.service;

import com.example.order_project.entity.Order;
import com.example.order_project.entity.PerformanceRecord;

import java.util.List;

public interface PerformanceService {
    // 绩效评估
    PerformanceRecord evaluatePerformance(Order orderId, Double workload);

    // 查询绩效记录
    List<PerformanceRecord> getPerformanceRecordsByStaffId(Long staffId);

    // 修改绩效
    PerformanceRecord updatePerformance(Long performanceId, Double newWorkload);

    // 绩效申诉处理
    void handlePerformanceAppeal(Long performanceId, String appealReason);
}