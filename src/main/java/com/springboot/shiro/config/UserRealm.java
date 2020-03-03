package com.springboot.shiro.config;

import com.springboot.shiro.dao.UserMapper;
import com.springboot.shiro.entity.User;
import com.springboot.shiro.service.IUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 自定义realm类
 *
 * @author xuchao
 * @since 2020/3/3 13:32
 */
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private IUserService userService;

    /**
     * 执行授权逻辑
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行授权逻辑");

        //给资源进行授权
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //添加资源的授权字符串
        //注意：字符串要和过滤器中保持一致
        //info.addStringPermission("user:add");
        //到数据库查询当前登录用户的授权字符串
        //获取当前用户的ID
        Subject subject = SecurityUtils.getSubject();
        //注意这个getPrincipal（）；是从下们的认证方法获取的，所以我们需要改下面的方法
        User user = (User) subject.getPrincipal();
        User user1 = userService.findById(user.getId());
        //在info里面添加上我们的授权字符串
        info.addStringPermission(user1.getPerms());
        return info;
    }

    /**
     * 执行认证逻辑
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("执行认证逻辑");
        //判断用户名
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

        User user = userService.findByName(token.getUsername());

        if (!token.getUsername().equals(user.getUsername())) {
            //说明用户名不存在！
            return null;
        }
        //判断密码
        return new SimpleAuthenticationInfo(user, user.getPassword(), "");
    }
}