package io.renren.modules.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.app.entity.HouseEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 房产资源表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2025-03-10 10:31:39
 */
@Mapper
public interface HouseDao extends BaseMapper<HouseEntity> {
    List<HouseEntity> queryBingdingHouse();
}
