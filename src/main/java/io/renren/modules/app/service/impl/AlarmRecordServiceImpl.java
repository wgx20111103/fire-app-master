package io.renren.modules.app.service.impl;

import io.renren.modules.app.dao.AlarmRecordDao;
import io.renren.modules.app.entity.AlarmRecordEntity;
import io.renren.modules.app.service.AlarmRecordService;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;



@Service("alarmRecordService")
public class AlarmRecordServiceImpl extends ServiceImpl<AlarmRecordDao, AlarmRecordEntity> implements AlarmRecordService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AlarmRecordEntity> page = this.page(
                new Query<AlarmRecordEntity>().getPage(params),
                new QueryWrapper<AlarmRecordEntity>()
        );

        return new PageUtils(page);
    }

}