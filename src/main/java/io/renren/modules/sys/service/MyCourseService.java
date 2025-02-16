package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.MyCourse;

import java.util.Map;

public interface MyCourseService extends IService<MyCourse> {
    PageUtils queryPage(Map<String, Object> params);
}