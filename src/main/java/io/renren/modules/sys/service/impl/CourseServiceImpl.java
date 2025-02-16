package io.renren.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.sys.dao.CourseDao;
import io.renren.modules.sys.entity.Course;
import io.renren.modules.sys.service.CourseService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("courseService")
public class CourseServiceImpl extends ServiceImpl<CourseDao, Course> implements CourseService {
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String name = (String)params.get("name");
        IPage<Course> page = this.page(
                new Query<Course>().getPage(params),
                new QueryWrapper<Course>().like(StringUtils.isNotBlank(name),"name", name)
        );
        return new PageUtils(page);
    }


}