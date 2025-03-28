package io.renren.modules.app.service.impl;


import io.renren.common.annotation.ComResponse;
import io.renren.common.annotation.GateService;
import io.renren.common.utils.CheckUtil;
import io.renren.common.utils.SpringUtil;
import io.renren.modules.app.service.InitService;
import io.renren.modules.app.thread.Thread_Fire_Collect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * @author wgx
 * @description 初始化流程（实现）
 * @date 2019/3/13
 */
@Service("InitServiceImpl")
public class InitServiceImpl implements InitService {

    private static Logger logger = LogManager.getLogger(InitServiceImpl.class);



    @Override
    public boolean initAllSelf(){
        logger.info("---------启动参数初始化---------");
        //仪器软件初始化状态（false 未完成初始化，true已完成初始化） 完成数据初始化后才能调用接口
        boolean b = setKeepConstant();
        if (b){
            Thread fireCollect = new Thread(new Thread_Fire_Collect());
            fireCollect.start();
        }
        logger.info("---------启动参数初始化结束---------");
        return true;
    }

    //初始化软件
    private boolean setKeepConstant(){
        try {
            //调用版本方法
            GateService gateService = SpringUtil.getBean("P_Com_KeepConstant", GateService.class);
            ComResponse comResponse = gateService.invoke(null, null);
            return CheckUtil.objToBoolean(comResponse.getRespParams());
        }catch (Exception e){
            e.printStackTrace();
            logger.error("持久化查询所有对应setKeepConstant以创建的table失败！");
            return false;
        }
    }

}
