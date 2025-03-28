package io.renren.modules.app.thread;



import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import io.renren.common.annotation.ComLogin;
import io.renren.common.utils.CheckUtil;
import io.renren.common.utils.Constant;
import io.renren.common.utils.DateUtil;
import io.renren.common.utils.SpringUtil;
import io.renren.modules.app.entity.AlarmRecordEntity;
import io.renren.modules.app.entity.FirePointEntity;
import io.renren.modules.app.entity.HouseEntity;
import io.renren.modules.app.entity.OperaRecordEntity;
import io.renren.modules.app.service.AlarmRecordService;
import io.renren.modules.app.service.HouseService;
import io.renren.modules.app.service.OperaRecordService;
import io.renren.modules.app.utils.VincentyDistanceCalculator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.util.List;

public class Thread_Fire_Send implements Runnable{
    private static Logger logger = LogManager.getLogger(Thread_Fire_Send.class);

    private final List<FirePointEntity> dataList;

    HouseService houseService = SpringUtil.getBean(HouseService.class);
    AlarmRecordService alarmRecordService = SpringUtil.getBean(AlarmRecordService.class);
    OperaRecordService operaRecordService = SpringUtil.getBean(OperaRecordService.class);
    // 构造函数接收参数
    public Thread_Fire_Send(List<FirePointEntity> dataList) {
        this.dataList = dataList;
    }


    @Override
    public void run() {
        logger.info("----------------推送报警信息线程开始！--------------");
            try {
                //获取已绑定房产信息
                List<HouseEntity> list = houseService.queryBingdingHouse();
                long device_open = CheckUtil.objToLong(ComLogin.infoSysDictionaryMap.get("device_open"));//自动开启喷淋时间
                long device_close = CheckUtil.objToLong(ComLogin.infoSysDictionaryMap.get("device_close"));//自动关闭喷淋时间
                long no_message = CheckUtil.objToLong(ComLogin.infoSysDictionaryMap.get("no_message"));//忽略报警不接受信息时间
                Boolean cleaned = true;
                for (HouseEntity houseEntity : list) {
                    //查询该房子报警记录最后一次操作
                    AlarmRecordEntity alarmRecordEntity = alarmRecordService.queryByHouseId(houseEntity.getId());

                    Integer type = 0;//报警类型：0触犯开始 1启动设备中 2报警结束
                    Integer typeOpera = 0;//忽略操做：0无操作 1忽略报警 2忽略喷淋
                    Date alarmTime = new Date();
                    Double alarmDistance = houseEntity.getAlarmDistance();//报警距离
                    if (alarmRecordEntity!=null){
                         type = alarmRecordEntity.getType();//报警类型：0触犯开始 1启动设备中 2报警结束
                         typeOpera = alarmRecordEntity.getTypeOpera();//忽略操做：0无操作 1忽略报警 2忽略喷淋
                         alarmTime = alarmRecordEntity.getAlarmTime();
                    }
                    Long tMinutes = DateUtil.getTimeDifferenceInMinutes(new Date(), alarmTime);//当前时间与报警时间差
                    //判断该房产是否推送报警信息
                    for (FirePointEntity firePointEntity : dataList) {
                        Double alarmNow = VincentyDistanceCalculator.calculateDistance(houseEntity.getLat(), houseEntity.getLon(), firePointEntity.getLatitude(), firePointEntity.getLongitude());
                        //推送报警信息
                        if(alarmNow<=alarmDistance){
                            cleaned=false;

                            // 1.保存报警信息(初始数据空,报警结束再次报警)
                            if (alarmRecordEntity==null || type == Constant.AlarmService.ASTOP.getValue()){
                                saveAlarm(houseEntity,alarmNow);
                                // TODO: 2020/7/14  推送服务
                            }else
                            // 2.已经开始了 无忽略报警信息 自动开启喷林
                            if (type == Constant.AlarmService.AST.getValue()&& typeOpera != 1){
                                //自动开启喷淋
                                if(tMinutes>=device_open){
                                    // TODO: 2020/7/14  开启喷淋
                                    OperaRecordEntity operaRecordEntity = new OperaRecordEntity();
                                    operaRecordEntity.setCreateTime(new Date());
                                    operaRecordEntity.setHouseId(houseEntity.getId());
                                    operaRecordEntity.setCreateUser(houseEntity.getUser());
                                    operaRecordEntity.setAddress(houseEntity.getHouseAddress());
                                    operaRecordEntity.setLat(houseEntity.getLat());
                                    operaRecordEntity.setLon(houseEntity.getLon());
                                    operaRecordEntity.setAlarmId(alarmRecordEntity.getId());
                                    operaRecordService.save(operaRecordEntity);
                                    //修改当前报警类型为启动设备中
                                    alarmRecordEntity.setType(1);
                                    alarmRecordService.updateById(alarmRecordEntity);
                                }
                            }else
                            // 3.已经开始了 忽略报警信息 不再接收⽕情威胁的提醒
                            if (type == Constant.AlarmService.AST.getValue()&& typeOpera == 1){
                                //对比时间 是否发生推送
                                if(tMinutes>=no_message){
                                    saveAlarm(houseEntity,alarmNow);
                                    // TODO: 2020/7/14  推送服务
                                }
                            }
                        }
                    }
                    //清除报警信息
                    if (cleaned){
                        // 1是否忽略喷淋
                        if (typeOpera != 2){
                            //查询当前在喷淋中 并且 当前时间超过自动关闭时间
                            if(type==Constant.AlarmService.AST.getValue()&& tMinutes>=device_close){
                             //TODO: 2020/7/14  关闭喷淋
                                //记录自动超做记录
                                UpdateWrapper<OperaRecordEntity> updateWrapper = new UpdateWrapper<>();
                                updateWrapper.eq("alarm_id", alarmRecordEntity.getId()).set("end_time", new Date());
                                operaRecordService.update(updateWrapper);
                                //修改该报警信息为结束
                                alarmRecordEntity.setType(2);
                            }
                            if(alarmRecordEntity!=null){
                                alarmRecordEntity.setTypeClean(1);
                                alarmRecordEntity.setTime(tMinutes.intValue());
                                alarmRecordService.updateById(alarmRecordEntity);
                            }
                        }
                    }
                    cleaned = true;//重新判断默认清除
                }
        logger.info("----------------推送报警信息线程结束！--------------");
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }

    /**
     * 插入报警信息
     */
    public void saveAlarm(HouseEntity houseEntity,Double alarmNow ){
        AlarmRecordEntity alarmRecordEntity=new AlarmRecordEntity();
        alarmRecordEntity.setLat(houseEntity.getLat());
        alarmRecordEntity.setLon(houseEntity.getLon());
        alarmRecordEntity.setAlarmAddress(houseEntity.getHouseAddress());
        alarmRecordEntity.setHouseId(houseEntity.getId());
        alarmRecordEntity.setAlarmTime(new Date());
        alarmRecordEntity.setLength(alarmNow.intValue());
        alarmRecordEntity.setType(0);
        alarmRecordEntity.setTypeClean(0);
        alarmRecordEntity.setTypeOpera(0);
        alarmRecordService.save(alarmRecordEntity);
    }
}
