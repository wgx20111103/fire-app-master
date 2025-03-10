package io.renren.modules.app.service.impl;

import io.renren.modules.app.dao.RemarkDao;
import io.renren.modules.app.entity.RemarkEntity;
import io.renren.modules.app.entity.UserEntity;
import io.renren.modules.app.service.RemarkService;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;




@Service("remarkService")
public class RemarkServiceImpl extends ServiceImpl<RemarkDao, RemarkEntity> implements RemarkService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<RemarkEntity> page = this.page(
                new Query<RemarkEntity>().getPage(params),
                new QueryWrapper<RemarkEntity>()
        );

        return new PageUtils(page);
    }
}