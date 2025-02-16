package io.renren.modules.sys.service;
import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.Cla;

import java.util.Map;

public interface ClaService extends IService<Cla> {
    PageUtils queryPage(Map<String, Object> params);
}