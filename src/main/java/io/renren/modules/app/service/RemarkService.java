package io.renren.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.app.entity.RemarkEntity;


import java.util.Map;

/**
 * 支持信息表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2025-03-10 10:03:42
 */
public interface RemarkService extends IService<RemarkEntity> {

    PageUtils queryPage(Map<String, Object> params);

}

