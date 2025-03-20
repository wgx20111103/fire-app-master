package io.renren.modules.app.thread;


import io.renren.common.utils.SpringUtil;
import io.renren.modules.app.entity.AlarmRecordEntity;
import io.renren.modules.app.entity.FirePointEntity;
import io.renren.modules.app.entity.HouseEntity;
import io.renren.modules.app.service.AlarmRecordService;
import io.renren.modules.app.service.HouseService;
import io.renren.modules.app.utils.VincentyDistanceCalculator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.util.List;

public class Thread_Fire_Send implements Runnable{
    private static Logger logger = LogManager.getLogger(Thread_Fire_Send.class);

    private final List<FirePointEntity> dataList;

    // 构造函数接收参数
    public Thread_Fire_Send(List<FirePointEntity> dataList) {
        this.dataList = dataList;
    }


    @Override
    public void run() {
        logger.info("----------------推送报警信息线程开始！--------------");
            try {
                HouseService houseService = SpringUtil.getBean(HouseService.class);
                AlarmRecordService alarmRecordService = SpringUtil.getBean(AlarmRecordService.class);
                //获取已绑定房产信息
                List<HouseEntity> list = houseService.queryBingdingHouse();
                for (HouseEntity houseEntity : list) {
                    //判断该房产是否推送报警信息
                    for (FirePointEntity firePointEntity : dataList) {
                        Double alarmNow = VincentyDistanceCalculator.calculateDistance(houseEntity.getLat(), houseEntity.getLon(), firePointEntity.getLatitude(), firePointEntity.getLongitude());
                        Double alarmDistance = houseEntity.getAlarmDistance();//报警距离
                        //推送报警信息
                        if(alarmNow<=alarmDistance){
                            // TODO: 2020/7/14  推送服务
                            // 保存报警信息
                            AlarmRecordEntity alarmRecordEntity=new AlarmRecordEntity();
                            alarmRecordEntity.setLat(houseEntity.getLat());
                            alarmRecordEntity.setLon(houseEntity.getLon());
                            alarmRecordEntity.setAlarmAddress(houseEntity.getHouseAddress());
                            alarmRecordEntity.setHouseId(houseEntity.getId());
                            alarmRecordEntity.setAlarmTime(new Date());
                            alarmRecordEntity.setLength(alarmNow.intValue());
                            alarmRecordEntity.setType(0);
                            alarmRecordEntity.setTypeClean(0);
                            alarmRecordService.save(alarmRecordEntity);
                        }
                    }
                }
        logger.info("----------------推送报警信息线程结束！--------------");
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
}
