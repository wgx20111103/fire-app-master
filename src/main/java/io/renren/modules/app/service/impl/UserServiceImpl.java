/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.app.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.exception.RRException;
import io.renren.common.validator.Assert;
import io.renren.modules.app.dao.UserDao;
import io.renren.modules.app.entity.UserEntity;
import io.renren.modules.app.form.LoginForm;
import io.renren.modules.app.service.UserService;
import io.renren.modules.sys.entity.SysUserEntity;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;


@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {

	@Override
	public UserEntity queryByEmail(String email) {
		return baseMapper.selectOne(new QueryWrapper<UserEntity>().eq("email", email));
	}

	@Override
	public UserEntity login(LoginForm form) {
		UserEntity user = queryByEmail(form.getUsername());
		return user;
	}

	@Override
	public boolean updatePassword(String email, String password) {
		UserEntity userEntity = new UserEntity();
		userEntity.setPassword(password);
		return this.update(userEntity,
				new QueryWrapper<UserEntity>().eq("email", email));
	}
}
