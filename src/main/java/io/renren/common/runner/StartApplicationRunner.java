package io.renren.common.runner;

import io.renren.modules.app.thread.Thread_Fire_Collect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * suntao
 */
//@Component//被spring容器管理
//@Order(1)//如果多个自定义ApplicationRunner，用来标明执行顺序
public class StartApplicationRunner implements ApplicationRunner {
    private static Logger logger = LogManager.getLogger(StartApplicationRunner.class);

/*    @Value("${thread.entrance.capacity}")
    private int se_capacity;
    @Value("${thread.entrance.poolsize}")
    private int se_poolsize;
    @Value("${thread.entrance.mixmumpoolsize}")
    private int se_mixmumpoolsize;
    @Value("${thread.entrance.keepalicetime}")
    private int se_keepalicetime;*/
    @Override
    public void run(ApplicationArguments args) throws Exception {
        //logger.info("------初始化sepabean主入口线程池----------");
       /* ComPool.sepabeanThread = new ThreadPoolExecutor(se_poolsize, se_mixmumpoolsize, se_keepalicetime, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(se_capacity));*/
        logger.info("----------启动主线程-----------");
        //Thread initProcess = new Thread(new InitProcess());
        Thread initProcess = new Thread(new Thread_Fire_Collect());
        initProcess.start();
    }

}
