package io.renren.modules.app.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.app.entity.AlarmRecordEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 报警记录表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2025-03-10 10:31:39
 */
@Mapper
public interface AlarmRecordDao extends BaseMapper<AlarmRecordEntity> {

    AlarmRecordEntity queryByHouseId(@Param("houseId")Long houseId);
}
