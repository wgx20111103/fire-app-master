package io.renren.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.sys.dao.ClaDao;
import io.renren.modules.sys.entity.Cla;
import io.renren.modules.sys.service.ClaService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("claService")
public class ClaServiceImpl extends ServiceImpl<ClaDao, Cla> implements ClaService {
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String name = (String)params.get("name");
        IPage<Cla> page = this.page(
                new Query<Cla>().getPage(params),
                new QueryWrapper<Cla>().like(StringUtils.isNotBlank(name),"name", name)

        );
        return new PageUtils(page);
    }
}