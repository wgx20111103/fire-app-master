package io.renren.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.renren.common.utils.DateUtil;
import io.renren.modules.app.dao.FirePointRDao;
import io.renren.modules.app.entity.FirePointREntity;
import io.renren.modules.app.service.FirePointRService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;




@Service("firePointRService")
public class FirePointRServiceImpl extends ServiceImpl<FirePointRDao, FirePointREntity> implements FirePointRService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<FirePointREntity> page = this.page(
                new Query<FirePointREntity>().getPage(params),
                new QueryWrapper<FirePointREntity>()
        );

        return new PageUtils(page);
    }


    @Override
    public List<FirePointREntity> queryListByDate(Date startDate, Date endDate){
        LambdaQueryWrapper<FirePointREntity> wrapper = new LambdaQueryWrapper<>();
        String nowDate = DateUtil.getStrFromDate(startDate, null);
        String oldDate = DateUtil.getStrFromDate(endDate, null);
        wrapper.ge(FirePointREntity::getAcqDate, nowDate)
                .le(FirePointREntity::getAcqDate, oldDate)
                .orderByDesc(FirePointREntity::getAcqDate)
                .orderByDesc(FirePointREntity::getAcqTime);

        return baseMapper.selectList(wrapper);
    }


}