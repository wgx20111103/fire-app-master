package io.renren.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.app.entity.DeviceEntity;

import java.util.Map;

/**
 * 设备表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2025-03-13 09:14:06
 */
public interface DeviceService extends IService<DeviceEntity> {

    PageUtils queryPage(Map<String, Object> params);


    DeviceEntity getDeviceByHouseId(Long houseId);
}

