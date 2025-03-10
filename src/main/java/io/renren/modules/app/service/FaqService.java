package io.renren.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.app.entity.FaqEntity;


import java.util.Map;

/**
 * Faq信息表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2025-03-10 10:11:28
 */
public interface FaqService extends IService<FaqEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

