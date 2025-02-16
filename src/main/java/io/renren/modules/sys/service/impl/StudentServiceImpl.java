package io.renren.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.sys.dao.StudentDao;
import io.renren.modules.sys.entity.Student;
import io.renren.modules.sys.service.StudentService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("studentService")
public class StudentServiceImpl extends ServiceImpl<StudentDao, Student> implements StudentService {
    @Override


    public PageUtils queryPage(Map<String, Object> params) {
        String name = (String)params.get("name");

        IPage<Student> page = this.page(
                new Query<Student>().getPage(params),
                new QueryWrapper<Student>().like(StringUtils.isNotBlank(name),"name", name)
        );
        return new PageUtils(page);
    }
}