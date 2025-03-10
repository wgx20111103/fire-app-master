package io.renren.modules.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.app.entity.OperaRecordEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 操作记录表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2025-03-10 10:31:39
 */
@Mapper
public interface OperaRecordDao extends BaseMapper<OperaRecordEntity> {
	
}
