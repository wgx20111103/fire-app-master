package io.renren.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.app.entity.FirePointEntity;


import java.util.Map;

/**
 * 火点记录表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2025-03-18 17:23:39
 */
public interface FirePointService extends IService<FirePointEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

