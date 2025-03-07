package com.example.order_project.service;

import com.example.order_project.entity.Order;
import com.example.order_project.entity.PerformanceRecord;
import com.example.order_project.repository.PerformanceRecordRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PerformanceServiceImpl implements PerformanceService {
    private final PerformanceRecordRepository performanceRecordRepository;

    public PerformanceServiceImpl(PerformanceRecordRepository performanceRecordRepository) {
        this.performanceRecordRepository = performanceRecordRepository;
    }

    @Override
    public PerformanceRecord evaluatePerformance(Order orderId, Double workload) {
        PerformanceRecord record = new PerformanceRecord();
        record.setOrder(orderId);
        record.setWorkload(workload);
        return performanceRecordRepository.save(record);
    }

    @Override
    public List<PerformanceRecord> getPerformanceRecordsByStaffId(Long staffId) {
        return performanceRecordRepository.findByStaff_Id(staffId);
    }

    @Override
    public PerformanceRecord updatePerformance(Long performanceId, Double newWorkload) {
        PerformanceRecord record = performanceRecordRepository.findById(performanceId).orElse(null);
        if (record != null) {
            record.setWorkload(newWorkload);
            return performanceRecordRepository.save(record);
        }
        return null;
    }

    @Override
    public void handlePerformanceAppeal(Long performanceId, String appealReason) {
        // 处理绩效申诉逻辑
    }
}