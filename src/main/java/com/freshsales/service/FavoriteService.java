package com.freshsales.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.freshsales.entity.Favorite;
import com.freshsales.entity.Product;
import com.freshsales.mapper.FavoriteMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoriteService extends ServiceImpl<FavoriteMapper, Favorite> {
    private final ProductService productService;

    public FavoriteService(ProductService productService) {
        this.productService = productService;
    }

    public void addFavorite(Long userId, Long productId) {
        if (lambdaQuery().eq(Favorite::getUserId, userId)
                .eq(Favorite::getProductId, productId)
                .count() > 0) {
            return;
        }
        Favorite fav = new Favorite();
        fav.setUserId(userId);
        fav.setProductId(productId);
        save(fav);
    }

    public void removeFavorite(Long userId, Long productId) {
        lambdaUpdate().eq(Favorite::getUserId, userId)
                .eq(Favorite::getProductId, productId)
                .remove();
    }

    public List<Product> getFavoriteProducts(Long userId) {
        return lambdaQuery().eq(Favorite::getUserId, userId)
                .list()
                .stream()
                .map(f -> productService.getById(f.getProductId()))
                .collect(Collectors.toList());
    }

    public boolean isFavorited(Long userId, Long productId) {
        return lambdaQuery().eq(Favorite::getUserId, userId)
                .eq(Favorite::getProductId, productId)
                .count() > 0;
    }
}