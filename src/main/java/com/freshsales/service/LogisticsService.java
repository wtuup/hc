package com.freshsales.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.freshsales.entity.Logistics;
import com.freshsales.mapper.LogisticsMapper;
import org.springframework.stereotype.Service;

@Service
public class LogisticsService extends ServiceImpl<LogisticsMapper, Logistics> {

    public void shipOrder(Long orderId, String company, String trackingNumber) {
        Logistics l = new Logistics();
        l.setOrderId(orderId);
        l.setCompany(company);
        l.setTrackingNumber(trackingNumber);
        l.setStatus("SHIPPED");
        save(l);
    }
}