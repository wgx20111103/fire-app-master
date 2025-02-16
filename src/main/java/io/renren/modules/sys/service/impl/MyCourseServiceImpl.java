package io.renren.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.sys.dao.MyCourseDao;
import io.renren.modules.sys.entity.MyCourse;
import io.renren.modules.sys.service.MyCourseService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("myCourseService")
public class MyCourseServiceImpl extends ServiceImpl<MyCourseDao, MyCourse> implements MyCourseService {
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String name = (String)params.get("name");
        String userName = (String)params.get("userName");
        IPage<MyCourse> page = this.page(
                new Query<MyCourse>().getPage(params),
                new QueryWrapper<MyCourse>()
                        .like(StringUtils.isNotBlank(name),"name", name).
                        eq(StringUtils.isNotBlank(userName),"user_name", userName)
        );
        return new PageUtils(page);
    }

}