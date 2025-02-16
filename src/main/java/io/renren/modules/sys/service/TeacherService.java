package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.Teacher;

import java.util.Map;

public interface TeacherService extends IService<Teacher> {
    PageUtils queryPage(Map<String, Object> params);
}