package com.example.order_project.repository;

import com.example.order_project.entity.PerformanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PerformanceRecordRepository extends JpaRepository<PerformanceRecord, Long> {
    // 根据工作人员ID查询绩效记录
    List<PerformanceRecord> findByStaff_Id(Long staffId);

    // 根据订单ID查询绩效记录
    Optional<PerformanceRecord> findByOrder_Id(Long orderId);

    // 根据工作人员ID和订单ID查询绩效记录
    Optional<PerformanceRecord> findByStaff_IdAndOrder_Id(Long staffId, Long orderId);
}