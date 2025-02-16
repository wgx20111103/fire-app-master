package io.renren.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.sys.dao.TeacherDao;
import io.renren.modules.sys.entity.Teacher;
import io.renren.modules.sys.service.TeacherService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("teacherService")
public class TeacherServiceImpl extends ServiceImpl<TeacherDao, Teacher> implements TeacherService {
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String name = (String)params.get("name");
        IPage<Teacher> page = this.page(
                new Query<Teacher>().getPage(params),
                new QueryWrapper<Teacher>().like(StringUtils.isNotBlank(name),"name", name)
        );
        return new PageUtils(page);
    }
}