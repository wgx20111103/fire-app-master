package io.renren.modules.sys.dao;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.sys.entity.Exam;
import io.renren.modules.sys.entity.File;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ExamDao extends BaseMapper<Exam> {
}