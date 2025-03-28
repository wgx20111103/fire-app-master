package io.renren.modules.app.thread;


import io.renren.common.utils.SpringUtil;
import io.renren.common.utils.UseUtil;
import io.renren.modules.app.service.InitService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author wgx
 * @description 总线程初始化
 * @date 2021/11/5
 */
public class InitProcess implements Runnable{

    private static Logger logger = LogManager.getLogger(InitProcess.class);

    private Long witetime = 10000L;
    @Override
    public void run() {
        try {
            if (SpringUtil.getApplicationContext() != null) {
                InitService initService = SpringUtil.getApplicationContext().getBean(InitService.class);
                if (!initService.initAllSelf()) {
                        //初始化失败,等待N秒
                        witetime = witetime * 2;
                        UseUtil.waitTimeForMill(witetime);
                        run();
                }
            }  else {
                //等待
                UseUtil.waitTime(1);
                run();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
    }
}
