package com.qianfeng.openapi.web.master.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qianfeng.openapi.web.master.dao.UserTokenDAO;
import com.qianfeng.openapi.web.master.pojo.UserToken;
import com.qianfeng.openapi.web.master.service.UserTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author menglili
 */
@Service
public class UserTokenServiceImpl implements UserTokenService {

    @Autowired
    private UserTokenDAO userTokenDAO;

    @Override
    public PageInfo<UserToken> getTokenList(UserToken criteria, int offset, int pageSize) {
        PageHelper.offsetPage(offset, pageSize);
        return new PageInfo<>(userTokenDAO.getTokenList(criteria));
    }

    @Override
    public UserToken getTokenById(int id) {
        return userTokenDAO.getTokenById(id);
    }
}
