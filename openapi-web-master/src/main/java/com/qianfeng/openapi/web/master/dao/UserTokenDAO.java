package com.qianfeng.openapi.web.master.dao;

import com.qianfeng.openapi.web.master.pojo.UserToken;

import java.util.List;

public interface UserTokenDAO {
    List<UserToken> getTokenList(UserToken criteria);

    UserToken getTokenById(int id);
}
