package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.Course;

import java.util.Map;

public interface CourseService extends IService<Course> {
    PageUtils queryPage(Map<String, Object> params);
}