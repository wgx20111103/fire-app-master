package io.renren.modules.app.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.renren.common.utils.RedisUtils;
import io.renren.modules.app.entity.AlarmRecordEntity;
import io.renren.modules.app.entity.HouseEntity;
import io.renren.modules.app.entity.OperaRecordEntity;
import io.renren.modules.app.entity.UserEntity;
import io.renren.modules.app.service.OperaRecordService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;

import javax.servlet.http.HttpServletRequest;


/**
 * 操作记录表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2025-03-10 10:31:39
 */
@RestController
@RequestMapping("app/operarecord")
public class OperaRecordController {
    @Autowired
    private OperaRecordService operaRecordService;

    @Autowired
    private RedisUtils redisUtils;

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

    /**
     * 查询用户下所有操作记录
     */
    @RequestMapping("/queryList")
    public R queryList(HttpServletRequest request){
        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)){
            return R.error(503, "token已失效,请重新登录!");
        }
        UserEntity userInfoVo = redisUtils.get(token, UserEntity.class);
        LambdaQueryWrapper<OperaRecordEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OperaRecordEntity::getCreateUser, userInfoVo.getEmail());
        List<OperaRecordEntity> list = operaRecordService.list(wrapper);
        return R.ok().put("operaRecordList", list);
    }

}
