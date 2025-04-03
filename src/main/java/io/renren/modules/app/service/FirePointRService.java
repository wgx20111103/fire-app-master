package io.renren.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.app.entity.FirePointREntity;


import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 火点记录表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2025-04-01 14:39:33
 */
public interface FirePointRService extends IService<FirePointREntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<FirePointREntity> queryListByDate(Date startDate, Date endDate);
}

