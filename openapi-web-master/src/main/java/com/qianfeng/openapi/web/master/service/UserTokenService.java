package com.qianfeng.openapi.web.master.service;

import com.github.pagehelper.PageInfo;
import com.qianfeng.openapi.web.master.pojo.UserToken;

public interface UserTokenService {
    PageInfo<UserToken> getTokenList(UserToken criteria, int offset, int pageSize);

    UserToken getTokenById(int id);
}
