package io.renren.modules.app.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.app.entity.FaqEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * Faq信息表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2025-03-10 10:11:28
 */
@Mapper
public interface FaqDao extends BaseMapper<FaqEntity> {
	
}
