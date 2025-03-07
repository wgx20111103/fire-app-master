/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.app.controller;


import io.renren.common.annotation.ComLogin;
import io.renren.common.exception.RRException;
import io.renren.common.utils.ErrorCode;
import io.renren.common.utils.R;
import io.renren.common.utils.RedisUtils;
import io.renren.common.utils.UUIDUtil;
import io.renren.common.validator.Assert;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.app.entity.UserEntity;
import io.renren.modules.app.form.LoginForm;
import io.renren.modules.app.service.UserService;
import io.renren.modules.app.utils.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * APP登录授权
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@RequestMapping("/app")
@Api("APP登录接口")
public class AppLoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private MessageSource messageSource;

    /**
     * 登录
     */
    @PostMapping("login")
    @ApiOperation("登录")
    public R login(@RequestBody LoginForm form, HttpServletRequest request){
        //表单校验
        ValidatorUtils.validateEntity(form);
        //用户登录
        UserEntity user = userService.login(form);
        Locale locale = (Locale)request.getAttribute("language");


        if (user==null){
            throw new RRException(messageSource.getMessage("账号不存在",null,locale), ErrorCode.ERR_NO_USER);
        }
        //账号不存在、密码错误
        if(!user.getPassword().equals(new Sha256Hash(form.getPassword(), user.getSalt()).toHex())) {
            return R.error(ErrorCode.ERR_PASSWORD,"账号或密码不正确");
        }
        //缓存中校验是否有该用户查询校验是否有该用户，有就覆盖登录
        //校验用户是否登录
        for (Map.Entry<String,UserEntity > entry : ComLogin.loginUserInfoTokenMap.entrySet()) {
            if(entry.getValue().getUserId() == user.getUserId()){
                //如果存在就删除
                ComLogin.loginUserInfoTokenMap.remove(entry.getKey());
                //删除redis中的token
                redisUtils.delete(entry.getKey());
            }
        }
        //生成token
        String token = UUIDUtil.uuid();
        ComLogin.loginUserInfoTokenMap.put(token,user);
        redisUtils.set(token, user);
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("user", user);
        return R.ok(map);
    }
    /**
     * 退出
     */
    @PostMapping("logout")
    @ApiOperation("app退出登录")
    public R logout(HttpServletRequest request) {
        String token = request.getHeader("token");
        ComLogin.loginUserInfoTokenMap.remove(token);
        redisUtils.delete(token);
        return R.ok();
    }


}
