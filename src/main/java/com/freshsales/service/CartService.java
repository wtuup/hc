package com.freshsales.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.freshsales.entity.Cart;
import com.freshsales.mapper.CartMapper;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CartService extends ServiceImpl<CartMapper, Cart> {

    public void addToCart(Long userId, Long productId, Integer quantity) {
        Cart existing = lambdaQuery()
                .eq(Cart::getUserId, userId)
                .eq(Cart::getProductId, productId)
                .one();
        if (existing != null) {
            existing.setQuantity(existing.getQuantity() + quantity);
            updateById(existing);
        } else {
            Cart c = new Cart();
            c.setUserId(userId);
            c.setProductId(productId);
            c.setQuantity(quantity);
            save(c);
        }
    }

    public void updateQuantity(Long cartId, Long userId, Integer quantity) {
        lambdaUpdate()
                .eq(Cart::getId, cartId)
                .eq(Cart::getUserId, userId)
                .set(Cart::getQuantity, quantity)
                .update();
    }

    public void removeItem(Long cartId, Long userId) {
        lambdaUpdate()
                .eq(Cart::getId, cartId)
                .eq(Cart::getUserId, userId)
                .remove();
    }

    public void clearCart(Long userId) {
        lambdaUpdate().eq(Cart::getUserId, userId).remove();
    }

    public List<Cart> getUserCart(Long userId) {
        return lambdaQuery().eq(Cart::getUserId, userId).list();
    }
}