package io.renren.modules.app.controller;

import java.util.Arrays;
import java.util.Map;

import io.renren.modules.app.entity.AlarmRecordEntity;
import io.renren.modules.app.entity.DeviceEntity;
import io.renren.modules.app.service.DeviceService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 设备表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2025-03-13 09:14:06
 */
@RestController
@RequestMapping("app/device")
public class DeviceController {
    @Autowired
    private DeviceService deviceService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("generator:device:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = deviceService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("generator:device:info")
    public R info(@PathVariable("id") Integer id){
		DeviceEntity device = deviceService.getById(id);

        return R.ok().put("device", device);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("generator:device:save")
    public R save(@RequestBody DeviceEntity device){
		deviceService.save(device);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("generator:device:update")
    public R update(@RequestBody DeviceEntity device){
		deviceService.updateById(device);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("generator:device:delete")
    public R delete(@RequestBody Integer[] ids){
		deviceService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * 根据houseId打开设备
     * @param houseId
     * @return
     */
    @RequestMapping("/queryByHouseId")
    public R queryByHouseId(@RequestBody Long houseId){
        DeviceEntity device = deviceService.getDeviceByHouseId(houseId);
        return R.ok().put("device", device);
    }

}
