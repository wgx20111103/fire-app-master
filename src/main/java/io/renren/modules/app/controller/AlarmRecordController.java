package io.renren.modules.app.controller;

import java.util.Arrays;
import java.util.Map;

import io.renren.modules.app.entity.AlarmRecordEntity;
import io.renren.modules.app.service.AlarmRecordService;
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
 * 报警记录表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2025-03-10 10:31:39
 */
@RestController
@RequestMapping("generator/alarmrecord")
public class AlarmRecordController {
    @Autowired
    private AlarmRecordService alarmRecordService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("generator:alarmrecord:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = alarmRecordService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("generator:alarmrecord:info")
    public R info(@PathVariable("id") Integer id){
		AlarmRecordEntity alarmRecord = alarmRecordService.getById(id);

        return R.ok().put("alarmRecord", alarmRecord);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("generator:alarmrecord:save")
    public R save(@RequestBody AlarmRecordEntity alarmRecord){
		alarmRecordService.save(alarmRecord);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("generator:alarmrecord:update")
    public R update(@RequestBody AlarmRecordEntity alarmRecord){
		alarmRecordService.updateById(alarmRecord);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("generator:alarmrecord:delete")
    public R delete(@RequestBody Integer[] ids){
		alarmRecordService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
