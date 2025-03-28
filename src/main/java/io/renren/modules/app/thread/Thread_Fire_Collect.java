package io.renren.modules.app.thread;


import io.renren.common.utils.CheckUtil;
import io.renren.common.utils.DateUtil;
import io.renren.common.utils.SpringUtil;
import io.renren.config.RestTemplateConfig;
import io.renren.modules.app.entity.FirePointEntity;
import io.renren.modules.app.service.FirePointService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.client.RestTemplate;

import java.util.*;

public class Thread_Fire_Collect implements Runnable{
    private static Logger logger = LogManager.getLogger(Thread_Fire_Collect.class);


    @Override
    public void run() {
        logger.info("----------------启动火点收集线程程！--------------");
        while (true) {
            try {
                RestTemplate restTemplate = RestTemplateConfig.getRestTemplate();
                //String url="http://firms.modaps.eosdis.nasa.gov/api/area/csv/aa4b8fe40253876c2e724d87d44cfaff/VIIRS_NOAA20_NRT/-140,24.5,-60,60/1/"+DateUtil.getStrFromDate(new Date(), "yyyy-MM-dd");
                String url="http://firms.modaps.eosdis.nasa.gov/api/area/csv/aa4b8fe40253876c2e724d87d44cfaff/VIIRS_NOAA20_NRT/-140,24.5,-60,60/1/"+"2025-03-28";

                //调用接口
                String result = restTemplate.getForObject(url,String.class);
                List<FirePointEntity> dataList = new ArrayList<>();
                String[] lines = result.split("\n");
                boolean isFirstLine = true; // 跳过标题行

                for (String line : lines) {
                    if (isFirstLine) {
                        isFirstLine = false;
                        continue;
                    }
                    String[] values = line.split(",");
                    //查询下是否有该数据直到有了该数据直接跳出循环
                    Map<String, Object> columnMap=new HashMap<>();
                    columnMap.put("latitude",CheckUtil.objToDouble(values[0]));
                    columnMap.put("longitude",CheckUtil.objToDouble(values[1]));
                    columnMap.put("acq_time",CheckUtil.objToInteger(values[6]));
                    FirePointService firePointService = SpringUtil.getBean(FirePointService.class);
                    List<FirePointEntity> firePointEntities = firePointService.listByMap(columnMap);
                    if(firePointEntities!=null && firePointEntities.size()>0){
                        break;
                    }
                    FirePointEntity dataObj = new FirePointEntity();
                    dataObj.setLatitude(CheckUtil.objToDouble(values[0]));
                    dataObj.setLongitude(CheckUtil.objToDouble(values[1]));
                    dataObj.setBrightTi4(CheckUtil.objToDouble(values[2]));
                    dataObj.setScan(CheckUtil.objToDouble(values[3]));
                    dataObj.setTrack(CheckUtil.objToDouble(values[4]));
                    dataObj.setAcqDate(values[5]);
                    dataObj.setAcqTime(CheckUtil.objToInteger(values[6]));
                    dataObj.setFrp(CheckUtil.objToDouble(values[12]));
                    dataObj.setDaynight(values[13]);
                    dataList.add(dataObj);
                    firePointService.save(dataObj);
                }
                //调用推送服务
                Thread thread = new Thread(new Thread_Fire_Send(dataList));
                thread.run();


                Thread.sleep(120000);  //2分钟轮询一次
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }
}
