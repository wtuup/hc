package com.freshsales.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.freshsales.entity.AfterSale;
import com.freshsales.mapper.AfterSaleMapper;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AfterSaleService extends ServiceImpl<AfterSaleMapper, AfterSale> {

    public void apply(AfterSale a, Long userId) {
        a.setUserId(userId);
        a.setStatus("PENDING");
        save(a);
    }

    public List<AfterSale> getByUserId(Long userId) {
        return lambdaQuery().eq(AfterSale::getUserId, userId)
                .orderByDesc(AfterSale::getApplyTime)
                .list();
    }

    public void approve(Long id) {
        lambdaUpdate().eq(AfterSale::getId, id)
                .set(AfterSale::getStatus, "APPROVED")
                .update();
    }

    public void reject(Long id) {
        lambdaUpdate().eq(AfterSale::getId, id)
                .set(AfterSale::getStatus, "REJECTED")
                .update();
    }

    public List<AfterSale> getAll() {
        return lambdaQuery().orderByDesc(AfterSale::getApplyTime).list();
    }
}