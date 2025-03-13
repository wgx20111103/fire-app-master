package io.renren.modules.app.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.app.entity.DeviceEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 设备表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2025-03-13 09:14:06
 */
@Mapper
public interface DeviceDao extends BaseMapper<DeviceEntity> {
	
}
