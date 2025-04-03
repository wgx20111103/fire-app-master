package io.renren.modules.app.thread;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.annotation.*;
import io.renren.common.utils.CheckUtil;
import io.renren.common.utils.DateUtil;
import io.renren.common.utils.RedisUtils;
import io.renren.modules.app.entity.FirePointEntity;
import io.renren.modules.app.entity.FirePointREntity;
import io.renren.modules.app.service.FirePointRService;
import io.renren.modules.app.service.FirePointService;
import io.renren.modules.sys.entity.SysConfigEntity;
import io.renren.modules.sys.service.SysConfigService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author wgx
 * @description
 * @Date 2025年03月27日 16:07
 * 定时删除火点数据
 */
@Service("Scheduled_Dele_FirePoint")
public class Scheduled_Dele_FirePoint extends BaseFunc implements GateService {
    private static Logger logger = LogManager.getLogger(Scheduled_Dele_FirePoint.class);


    @Resource
    private FirePointService firePointService;

    @Resource
    private FirePointRService firePointRService;

    @Autowired
    private RedisUtils redisUtils;
    


    @Override
    public ComResponse invoke(ComRequest comRequest, ComFinger comFinger) throws Exception {
        logger.info("---开始手动删除火点数据---");
        deletefirePoint();
        return new ComResponse(true);
    }

    //删除firePoint 当前两天前数据
    @Scheduled(cron = "0 20 0 * * ?")
    private void deletefirePoint() throws Exception{

        //缓存6天数据
        Date dateS1 = DateUtil.getDateBefore(new Date(), 1);
        Date dateE5 = DateUtil.getDateBefore(new Date(), 5);
        List<FirePointREntity> firePointREntities6 = firePointRService.queryListByDate(dateE5, dateS1);
        for (FirePointREntity firePointREntity : firePointREntities6) {
            long timeDay = DateUtil.getTimeDifferenceInDay(firePointREntity.getAcqDate(), new Date());
            firePointREntity.setShowFire(timeDay+1);
        }
        redisUtils.setList("sixD", firePointREntities6,RedisUtils.NOT_EXPIRE);

        //缓存30天数据
        int days=30;
        Date dateE29 = DateUtil.getDateBefore(new Date(), 29);
        List<FirePointREntity> firePointREntities29 = firePointRService.queryListByDate(dateE29,dateS1);
        for (FirePointREntity firePointREntity : firePointREntities29) {
            long timeDay = DateUtil.getTimeDifferenceInDay(firePointREntity.getAcqDate(), new Date());

            if (timeDay<=days/6){
                firePointREntity.setShowFire(1l);
            }else if (timeDay<=2*(days/6)){
                firePointREntity.setShowFire(2l);
            }else if (timeDay<=3*(days/6)){
                firePointREntity.setShowFire(3l);
            }else if (timeDay<=4*(days/6)){
                firePointREntity.setShowFire(4l);
            }else if (timeDay<=5*(days/6)){
                firePointREntity.setShowFire(5l);
            }else if (timeDay<=6*(days/6)){
                firePointREntity.setShowFire(6l);
            }

        }
        redisUtils.setList("thirtyD", firePointREntities29,RedisUtils.NOT_EXPIRE);
        //缓存360天数据
        days=360;
        Date dateE359 = DateUtil.getDateBefore(new Date(), 359);
        List<FirePointREntity> firePointREntities359 = firePointRService.queryListByDate(dateE359,dateS1);
        for (FirePointREntity firePointREntity : firePointREntities359) {
            long timeDay = DateUtil.getTimeDifferenceInDay(firePointREntity.getAcqDate(), new Date());
            if (timeDay<=days/6){
                firePointREntity.setShowFire(1l);
            }else if (timeDay<=2*(days/6)){
                firePointREntity.setShowFire(2l);
            }else if (timeDay<=3*(days/6)){
                firePointREntity.setShowFire(3l);
            }else if (timeDay<=4*(days/6)){
                firePointREntity.setShowFire(4l);
            }else if (timeDay<=5*(days/6)){
                firePointREntity.setShowFire(5l);
            }else if (timeDay<=6*(days/6)){
                firePointREntity.setShowFire(6l);
            }

        }
        redisUtils.setList("yearD", firePointREntities359,RedisUtils.NOT_EXPIRE);
        logger.info("---定时删除火点数据---");
        QueryWrapper<FirePointEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lt("acq_date", DateUtil.getDateBefore(new Date(), 2));
        firePointService.remove(queryWrapper);
    }

}
