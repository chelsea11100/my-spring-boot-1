package com.example.order_project.service;

import com.example.order_project.entity.Order;
import com.example.order_project.entity.PerformanceRecord;
import com.example.order_project.repository.PerformanceRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Service
public class PerformanceServiceImpl implements PerformanceService {
    private final PerformanceRecordRepository performanceRecordRepository;

    @Autowired
    private RestTemplate restTemplate;

    public PerformanceServiceImpl(PerformanceRecordRepository performanceRecordRepository) {
        this.performanceRecordRepository = performanceRecordRepository;
    }

    //创建绩效记录（工作量ai来定义）
    @Override
    public PerformanceRecord evaluateWorkload(Order order) {
        // 假设AI的URL
        String aiUrl = "http://xxxxx.com/evaluate"; // 到时候加api
        String feedback = order.getUserFeedback();
        Double workload;

        // 调用AI API评估工作量
        ResponseEntity<Double> response = restTemplate.getForEntity(
                UriComponentsBuilder.fromHttpUrl(aiUrl)
                        .queryParam("feedback", feedback)
                        .build().toUri(),
                Double.class
        );

        if (response.getStatusCode().is2xxSuccessful()) {
            workload = response.getBody();
        } else {
            return null;
        }

        PerformanceRecord record = new PerformanceRecord();
        record.setOrder(order);
        record.setWorkload(workload);
        record.setSalary(workload * 100); // 假设这一单赚的薪资是工作量的100倍
        return performanceRecordRepository.save(record);
    }
    //获取工作人员的绩效记录
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