package io.renren.modules.app.service.impl;

import io.renren.modules.app.dao.FirePointDao;
import io.renren.modules.app.entity.FirePointEntity;
import io.renren.modules.app.service.FirePointService;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;




@Service("firePointService")
public class FirePointServiceImpl extends ServiceImpl<FirePointDao, FirePointEntity> implements FirePointService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<FirePointEntity> page = this.page(
                new Query<FirePointEntity>().getPage(params),
                new QueryWrapper<FirePointEntity>()
        );

        return new PageUtils(page);
    }

}