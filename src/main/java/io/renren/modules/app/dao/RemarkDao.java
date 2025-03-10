package io.renren.modules.app.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.app.entity.RemarkEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 支持信息表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2025-03-10 10:03:42
 */
@Mapper
public interface RemarkDao extends BaseMapper<RemarkEntity> {
	
}
