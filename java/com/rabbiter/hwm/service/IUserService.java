package com.rabbiter.hwm.service;

import com.rabbiter.hwm.controller.dto.UserDTO;
import com.rabbiter.hwm.controller.dto.UserPasswordDTO;
import com.rabbiter.hwm.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author
 * @since 2024-01-26
 */
public interface IUserService extends IService<User> {

    UserDTO login(UserDTO userDTO);

    User register(UserDTO userDTO);

    void updatePassword(UserPasswordDTO userPasswordDTO);
}
