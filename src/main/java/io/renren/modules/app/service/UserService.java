/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.app.service;


import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.modules.app.entity.UserEntity;
import io.renren.modules.app.form.LoginForm;

/**
 * 用户
 *
 * @author Mark sunlightcs@gmail.com
 */
public interface UserService extends IService<UserEntity> {

	UserEntity queryByEmail(String mobile);

	/**
	 * 用户登录
	 * @param form    登录表单
	 * @return        返回用户ID
	 */
	UserEntity login(LoginForm form);

	/**
	 * 修改密码
	 * @param email       用户名
	 * @param password    新密码
	 * @return        返回用户ID
	 */
	boolean updatePassword(String email, String password);
}
