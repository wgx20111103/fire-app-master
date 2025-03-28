package io.renren.common.annotation;


/**
 * @author suntao
 * @description 统一入口
 * @date 2019/4/1
 */
public interface GateService {

    ComResponse invoke(ComRequest comRequest,ComFinger comFinger) throws Exception;
}
