package io.renren.modules.app.service.init;


import io.renren.common.annotation.*;
import io.renren.common.utils.CheckUtil;
import io.renren.modules.sys.entity.SysConfigEntity;
import io.renren.modules.sys.service.SysConfigService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wgx
 * @description
 * @Date 2025年03月27日 16:07
 */
@Service("P_Com_KeepConstant")
public class P_Com_KeepConstant extends BaseFunc implements GateService {
    private static Logger logger = LogManager.getLogger(P_Com_KeepConstant.class);


    @Resource
    private SysConfigService sysConfigService;


    @Override
    public ComResponse invoke(ComRequest comRequest, ComFinger comFinger) throws Exception {
        logger.info("---开始初始化并缓存版本数据---");
        List<SysConfigEntity> list = sysConfigService.list();
        if(list != null && list.size()>0) {
            for (SysConfigEntity maps:list) {
                String key = CheckUtil.objToString(maps.getParamKey());
                String val = CheckUtil.objToString(maps.getParamValue());
                ComLogin.infoSysDictionaryMap.put(key,val);
            }
        }

        return new ComResponse(true);
    }

}
