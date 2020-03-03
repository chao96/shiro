package com.springboot.shiro.dao;

import com.springboot.shiro.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author xuchao
 * @since 2020/3/3 15:15
 */
@Mapper
public interface UserMapper {

    @Select("select * from user where username= '${username}'")
    public User findByName(@Param("username") String name);

    @Select("select * from user where id = #{value}")
    public User findById(Integer id);

}
