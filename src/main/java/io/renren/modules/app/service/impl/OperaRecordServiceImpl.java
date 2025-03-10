package io.renren.modules.app.service.impl;

import io.renren.modules.app.dao.OperaRecordDao;
import io.renren.modules.app.entity.OperaRecordEntity;
import io.renren.modules.app.service.OperaRecordService;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;




@Service("operaRecordService")
public class OperaRecordServiceImpl extends ServiceImpl<OperaRecordDao, OperaRecordEntity> implements OperaRecordService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OperaRecordEntity> page = this.page(
                new Query<OperaRecordEntity>().getPage(params),
                new QueryWrapper<OperaRecordEntity>()
        );

        return new PageUtils(page);
    }

}