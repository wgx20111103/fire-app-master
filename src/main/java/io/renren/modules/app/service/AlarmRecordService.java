package io.renren.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.app.entity.AlarmRecordEntity;


import java.util.Map;

/**
 * 报警记录表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2025-03-10 10:31:39
 */
public interface AlarmRecordService extends IService<AlarmRecordEntity> {

    PageUtils queryPage(Map<String, Object> params);

    AlarmRecordEntity queryByHouseId(Long houseId);
}

