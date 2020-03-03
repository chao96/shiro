package com.springboot.shiro.service;

import com.springboot.shiro.entity.User;

/**
 * @author xuchao
 * @since 2020/3/3 15:18
 */
public interface IUserService {

    public User findByName(String name);

    public User findById(Integer id);
}
