package io.renren.modules.app.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.renren.common.utils.CheckUtil;
import io.renren.common.utils.RedisUtils;
import io.renren.modules.app.entity.AlarmRecordEntity;
import io.renren.modules.app.entity.HouseEntity;
import io.renren.modules.app.entity.UserEntity;
import io.renren.modules.app.service.AlarmRecordService;
import io.renren.modules.app.service.HouseService;
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
 * 报警记录表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2025-03-10 10:31:39
 */
@RestController
@RequestMapping("app/alarmrecord")
public class AlarmRecordController {
    @Autowired
    private AlarmRecordService alarmRecordService;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private HouseService houseService;

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

    /**
     * 根据houseId查询当前最新报警记录
     */
    @RequestMapping("/queryByHouseId")
    public R queryByHouseId(@RequestBody Map<String, Object> params){
        Long houseId = CheckUtil.objToLong(params.get("houseId"));
        AlarmRecordEntity alarmRecordEntity = alarmRecordService.queryByHouseId(houseId);
        return R.ok().put("alarmRecord", alarmRecordEntity);
    }

    /**
     * 查询用户下所有报警记录
     */
    @RequestMapping("/queryList")
    public R queryList(HttpServletRequest request){
        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)){
            return R.error(503, "token已失效,请重新登录!");
        }
        UserEntity userInfoVo = redisUtils.get(token, UserEntity.class);
        LambdaQueryWrapper<HouseEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(HouseEntity::getUserO, userInfoVo.getEmail())
                .or()
                .eq(HouseEntity::getUserT, userInfoVo.getEmail())
                .or()
                .eq(HouseEntity::getUser, userInfoVo.getEmail());
        List<HouseEntity> list = houseService.list(wrapper);
        List<AlarmRecordEntity> alist =new ArrayList<>();
        List<Long> ids =new ArrayList<>();
        if (list!=null && list.size()>0){
            for (HouseEntity houseEntity:list){
                ids.add(houseEntity.getId());
            }
            LambdaQueryWrapper<AlarmRecordEntity> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(AlarmRecordEntity::getHouseId, ids)
                    .orderByDesc(AlarmRecordEntity::getAlarmTime); // 按 createTime 降序排序
           alist = alarmRecordService.list(queryWrapper);
        }
        return R.ok().put("alarmRecordList", alist);
    }

}
