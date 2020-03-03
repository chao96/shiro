package com.springboot.shiro.service.impl;

import com.springboot.shiro.service.IUserService;
import com.springboot.shiro.dao.UserMapper;
import com.springboot.shiro.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xuchao
 * @since 2020/3/3 15:18
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByName(String name){
        return userMapper.findByName(name);
    }

    @Override
    public User findById(Integer id){
        return userMapper.findById(id);
    }
}
