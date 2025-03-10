package io.renren.modules.app.controller;

import java.util.Arrays;
import java.util.Map;

import io.renren.modules.app.entity.RemarkEntity;
import io.renren.modules.app.service.RemarkService;
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
 * 支持信息表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2025-03-10 10:03:42
 */
@RestController
@RequestMapping("generator/remark")
public class RemarkController {
    @Autowired
    private RemarkService remarkService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("generator:remark:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = remarkService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("generator:remark:info")
    public R info(@PathVariable("id") Integer id){
		RemarkEntity remark = remarkService.getById(id);

        return R.ok().put("remark", remark);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("generator:remark:save")
    public R save(@RequestBody RemarkEntity remark){
		remarkService.save(remark);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("generator:remark:update")
    public R update(@RequestBody RemarkEntity remark){
		remarkService.updateById(remark);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("generator:remark:delete")
    public R delete(@RequestBody Integer[] ids){
		remarkService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
