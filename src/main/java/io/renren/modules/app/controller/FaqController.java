package io.renren.modules.app.controller;

import java.util.Arrays;
import java.util.Map;

import io.renren.modules.app.entity.FaqEntity;
import io.renren.modules.app.service.FaqService;
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
 * Faq信息表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2025-03-10 10:11:28
 */
@RestController
@RequestMapping("generator/faq")
public class FaqController {
    @Autowired
    private FaqService faqService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("generator:faq:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = faqService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("generator:faq:info")
    public R info(@PathVariable("id") Integer id){
		FaqEntity faq = faqService.getById(id);

        return R.ok().put("faq", faq);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("generator:faq:save")
    public R save(@RequestBody FaqEntity faq){
		faqService.save(faq);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("generator:faq:update")
    public R update(@RequestBody FaqEntity faq){
		faqService.updateById(faq);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("generator:faq:delete")
    public R delete(@RequestBody Integer[] ids){
		faqService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
