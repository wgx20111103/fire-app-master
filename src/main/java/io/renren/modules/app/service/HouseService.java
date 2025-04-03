package io.renren.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.app.entity.HouseEntity;

import java.util.List;
import java.util.Map;

/**
 * 房产资源表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2025-03-10 10:31:39
 */
public interface HouseService extends IService<HouseEntity> {

    PageUtils queryPage(Map<String, Object> params);

    //查询已绑定的房产
    List<HouseEntity> queryBingdingHouse();

    //查询房产是否存在
    boolean existsByHouseAddress(String houseAddress);


}

