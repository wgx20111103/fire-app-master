package io.renren.common.annotation;


import io.renren.modules.app.entity.UserEntity;

import java.util.concurrent.ConcurrentHashMap;


/**
 *
 * @description 登录用户的存储
 *
 */
public class ComLogin {
    //登录人员tokenMap
    public static ConcurrentHashMap<String, UserEntity> loginUserInfoTokenMap = new ConcurrentHashMap<>();
}
