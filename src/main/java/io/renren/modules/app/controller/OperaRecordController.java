package io.renren.modules.app.controller;

import java.util.Arrays;
import java.util.Map;

import io.renren.modules.app.entity.OperaRecordEntity;
import io.renren.modules.app.service.OperaRecordService;
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
 * 操作记录表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2025-03-10 10:31:39
 */
@RestController
@RequestMapping("generator/operarecord")
public class OperaRecordController {
    @Autowired
    private OperaRecordService operaRecordService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("generator:operarecord:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = operaRecordService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("generator:operarecord:info")
    public R info(@PathVariable("id") Integer id){
		OperaRecordEntity operaRecord = operaRecordService.getById(id);

        return R.ok().put("operaRecord", operaRecord);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("generator:operarecord:save")
    public R save(@RequestBody OperaRecordEntity operaRecord){
		operaRecordService.save(operaRecord);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("generator:operarecord:update")
    public R update(@RequestBody OperaRecordEntity operaRecord){
		operaRecordService.updateById(operaRecord);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("generator:operarecord:delete")
    public R delete(@RequestBody Integer[] ids){
		operaRecordService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
