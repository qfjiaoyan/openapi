package com.qianfeng.openapi.web.master.shiro;

import com.qianfeng.openapi.web.master.dao.AdminUserDAO;
import com.qianfeng.openapi.web.master.pojo.AdminUser;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MyShiroRealm extends AuthorizingRealm {
    @Autowired
    private AdminUserDAO adminUserDAO;


    /**
     * 请求一个资源的时候使用，比如访问某一个url
     * 返回用的权限
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        Integer userId = (Integer) principalCollection.getPrimaryPrincipal();
        //查询出用所有的角色，交给shiro
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        List<Integer> roleIds = adminUserDAO.getUserRoleIds(userId);
        for (Integer roleId : roleIds) {
            info.addRole(roleId.toString());
        }
        List<String> perms = adminUserDAO.getUserPerms(userId);
        info.addStringPermissions(perms);
        return info;
    }

    //登陆的时候使用，验证用户名密码
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String email = token.getUsername();//登陆时的用户名
        AdminUser user = adminUserDAO.getUserByEmail(email);
        if (user == null) {//用户名错误
            return null;
        }
        return new SimpleAuthenticationInfo(user.getId(), user.getPassword(), super.getName());
    }
}
