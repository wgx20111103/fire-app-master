package io.renren.modules.app.service.impl;

import io.renren.modules.app.dao.FaqDao;
import io.renren.modules.app.entity.FaqEntity;
import io.renren.modules.app.service.FaqService;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;




@Service("faqService")
public class FaqServiceImpl extends ServiceImpl<FaqDao, FaqEntity> implements FaqService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<FaqEntity> page = this.page(
                new Query<FaqEntity>().getPage(params),
                new QueryWrapper<FaqEntity>()
        );

        return new PageUtils(page);
    }

}