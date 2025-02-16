package io.renren.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.sys.entity.MyCourse;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MyCourseDao extends BaseMapper<MyCourse> {
}