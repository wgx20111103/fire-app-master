package io.renren.modules.app.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.app.entity.FirePointEntity;
import io.renren.modules.app.entity.FirePointREntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 火点记录表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2025-03-18 17:23:39
 */
@Mapper
public interface FirePointDao extends BaseMapper<FirePointEntity> {

    List<FirePointEntity> queryListByTime(@Param("nowDate")String nowDate, @Param("beforeDate")String beforeDate, @Param("nowDateHHmm")String nowDateHHmm);

}
