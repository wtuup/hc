package com.freshsales.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.freshsales.entity.Product;
import com.freshsales.mapper.ProductMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.time.Duration;
import java.util.List;

@Slf4j
@Service
public class ProductService extends ServiceImpl<ProductMapper, Product> {
    private final RedisTemplate<String, Object> redisTemplate;

    @Value("${inventory.warning-threshold:10}")
    private int warningThreshold;

    public ProductService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public Product getByIdWithCache(Long id) {
        String key = "product:" + id;
        Product p = (Product) redisTemplate.opsForValue().get(key);
        if (p != null) return p;
        p = getById(id);
        if (p != null) {
            redisTemplate.opsForValue().set(key, p, Duration.ofMinutes(30));
        }
        return p;
    }

    public List<Product> searchAndFilter(String keyword, Long categoryId,
                                         String sortBy, Boolean asc,
                                         Double minPrice, Double maxPrice) {
        QueryWrapper<Product> w = new QueryWrapper<>();
        if (StringUtils.hasText(keyword)) {
            w.like("name", keyword);
        }
        if (categoryId != null) {
            w.eq("category_id", categoryId);
        }
        if (minPrice != null) {
            w.ge("price", minPrice);
        }
        if (maxPrice != null) {
            w.le("price", maxPrice);
        }
        if ("price".equals(sortBy)) {
            w.orderBy(true, Boolean.TRUE.equals(asc), "price");
        } else if ("sales".equals(sortBy)) {
            w.orderBy(true, Boolean.TRUE.equals(asc), "sales");
        } else {
            w.orderByDesc("created_at");
        }
        return list(w);
    }

    public void decreaseStock(Long productId, Integer quantity) {
        Product p = getById(productId);
        if (p == null) throw new RuntimeException("商品不存在");
        if (p.getStock() < quantity) throw new RuntimeException("库存不足");
        int newStock = p.getStock() - quantity;
        lambdaUpdate().eq(Product::getId, productId)
                .set(Product::getStock, newStock)
                .update();
        redisTemplate.delete("product:" + productId);
        if (newStock < warningThreshold) {
            log.warn("⚠️ 库存预警！商品[{}]剩余：{}", productId, newStock);
        }
    }

    public void incrementSales(Long productId, Integer quantity) {
        lambdaUpdate().eq(Product::getId, productId)
                .setSql("sales = sales + " + quantity)
                .update();
    }

    public List<Product> getTopBySales(int limit) {
        return baseMapper.selectTopBySales(limit);
    }

    public void updateStock(Long id, Integer newStock) {
        lambdaUpdate().eq(Product::getId, id)
                .set(Product::getStock, newStock)
                .update();
        redisTemplate.delete("product:" + id);
        if (newStock < warningThreshold) {
            log.warn("⚠️ 库存预警！商品[{}]剩余：{}", id, newStock);
        }
    }
}