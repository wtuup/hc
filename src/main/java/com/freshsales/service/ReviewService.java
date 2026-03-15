package com.freshsales.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.freshsales.entity.ProductReview;
import com.freshsales.mapper.ProductReviewMapper;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReviewService extends ServiceImpl<ProductReviewMapper, ProductReview> {

    public List<ProductReview> getByProductId(Long productId) {
        return lambdaQuery().eq(ProductReview::getProductId, productId)
                .orderByDesc(ProductReview::getCreateTime)
                .list();
    }

    public void addReview(ProductReview review, Long userId) {
        review.setUserId(userId);
        save(review);
    }
}