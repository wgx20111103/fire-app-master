package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.Student;

import java.util.Map;

public interface StudentService extends IService<Student> {
    PageUtils queryPage(Map<String, Object> params);
}