package io.renren.modules.app.service.impl;

import io.renren.modules.app.dao.HouseDao;
import io.renren.modules.app.entity.HouseEntity;
import io.renren.modules.app.service.HouseService;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;




@Service("houseService")
public class HouseServiceImpl extends ServiceImpl<HouseDao, HouseEntity> implements HouseService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<HouseEntity> page = this.page(
                new Query<HouseEntity>().getPage(params),
                new QueryWrapper<HouseEntity>()
        );

        return new PageUtils(page);
    }

}