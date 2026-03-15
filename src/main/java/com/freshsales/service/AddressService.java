package com.freshsales.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.freshsales.entity.Address;
import com.freshsales.mapper.AddressMapper;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AddressService extends ServiceImpl<AddressMapper, Address> {

    public List<Address> getUserAddresses(Long userId) {
        return lambdaQuery().eq(Address::getUserId, userId).list();
    }

    public void saveAddress(Address address, Long userId) {
        address.setUserId(userId);
        if (Boolean.TRUE.equals(address.getIsDefault())) {
            lambdaUpdate().eq(Address::getUserId, userId)
                    .set(Address::getIsDefault, false)
                    .update();
        }
        if (address.getId() == null) {
            save(address);
        } else {
            updateById(address);
        }
    }

    public void setDefault(Long id, Long userId) {
        lambdaUpdate().eq(Address::getUserId, userId)
                .set(Address::getIsDefault, false)
                .update();
        lambdaUpdate().eq(Address::getId, id)
                .set(Address::getIsDefault, true)
                .update();
    }

    public void deleteAddress(Long id, Long userId) {
        lambdaUpdate().eq(Address::getId, id)
                .eq(Address::getUserId, userId)
                .remove();
    }
}