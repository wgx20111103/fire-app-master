package io.renren.modules.app.controller;

import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import io.renren.common.utils.*;
import io.renren.modules.app.entity.*;
import io.renren.modules.app.service.*;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletRequest;


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

    @Autowired
    private AlarmRecordService alarmRecordService;

    @Autowired
    private OperaRecordService operaRecordService;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private FirePointRService firePointRService;

    @Autowired
    private FirePointService firePointService;




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
     * 根据houseId查询设备
     * @param houseId
     * @return
     */
    @RequestMapping("/queryByHouseId")
    public R queryByHouseId(@RequestBody Long houseId){
        DeviceEntity device = deviceService.getDeviceByHouseId(houseId);
        return R.ok().put("device", device);
    }


    /**
     * 根据houseId打开设备
     * @param houseEntity
     * @return
     */
    @RequestMapping("/openByHouseId")
    public R openByHouseId(@RequestBody HouseEntity houseEntity,HttpServletRequest request){
        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)){
            return R.error(503, "token已失效,请重新登录!");
        }
        UserEntity userInfoVo = redisUtils.get(token, UserEntity.class);
        Long houseId = houseEntity.getId();
        DeviceEntity device = deviceService.getDeviceByHouseId(houseId);
        // TODO 打开喷淋
        //查询该房子报警记录最后一次操作
        AlarmRecordEntity alarmRecordEntity = alarmRecordService.queryByHouseId(houseId);
        //保存操作记录
        OperaRecordEntity operaRecordEntity = new OperaRecordEntity();
        operaRecordEntity.setCreateTime(new Date());
        operaRecordEntity.setHouseId(houseId);
        operaRecordEntity.setCreateUser(userInfoVo.getEmail());
        operaRecordEntity.setAddress(houseEntity.getHouseAddress());
        operaRecordEntity.setLat(houseEntity.getLat());
        operaRecordEntity.setLon(houseEntity.getLon());
        operaRecordEntity.setAlarmId(alarmRecordEntity.getId());
        operaRecordService.save(operaRecordEntity);
        //修改报警状态
        alarmRecordEntity.setType(Constant.AlarmService.ARUN.getValue());
        alarmRecordService.saveOrUpdate(alarmRecordEntity);
        return R.ok();
    }

    /**
     * 根据houseId关闭设备
     * @param params
     * @return
     */
    @RequestMapping("/closeByHouseId")
    public R closeByHouseId(@RequestBody Map<String, Object> params ,HttpServletRequest request){
        Long houseId = CheckUtil.objToLong(params.get("houseId"));
        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)){
            return R.error(503, "token已失效,请重新登录!");
        }
        UserEntity userInfoVo = redisUtils.get(token, UserEntity.class);
        DeviceEntity device = deviceService.getDeviceByHouseId(houseId);
        // TODO 关闭喷淋
        //查询该房子报警记录最后一次操作
        AlarmRecordEntity alarmRecordEntity = alarmRecordService.queryByHouseId(houseId);
        Long tMinutes = DateUtil.getTimeDifferenceInMinutes(alarmRecordEntity.getAlarmTime(),new Date());//当前时间与报警时间差
        //保存操作记录
        UpdateWrapper<OperaRecordEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("alarm_id", alarmRecordEntity.getId()).set("end_time", new Date());
        operaRecordService.update(updateWrapper);
        //修改该报警信息为结束
        alarmRecordEntity.setType(Constant.AlarmService.ASTOP.getValue());
        alarmRecordEntity.setTime(tMinutes.intValue());
        alarmRecordService.saveOrUpdate(alarmRecordEntity);
        return R.ok();
    }

    /**
     * 忽略报警信息/喷淋信息
     * @param params
     * @return
     */
    @RequestMapping("/ignoreFire")
    public R ignoreFire(@RequestBody Map<String, Object> params) {

        Long houseId = CheckUtil.objToLong(params.get("houseId"));
        int type = CheckUtil.objToInteger(params.get("type"));//1是忽略报警，2是忽略喷淋
        //查询该房子报警记录最后一次操作
        AlarmRecordEntity alarmRecordEntity = alarmRecordService.queryByHouseId(houseId);
        if (type == 1){
            alarmRecordEntity.setTypeOpera(1);
        }
        if (type == 2){
            alarmRecordEntity.setTypeOpera(2);
        }
        alarmRecordService.saveOrUpdate(alarmRecordEntity);
        return R.ok();
    }

    /**
     * 查询火点信息
     */
    @RequestMapping("/queryFirePoint")
    public R queryFirePoint(@RequestBody Map<String, Object> params) {
        int type = CheckUtil.objToInteger(params.get("type"));//1实时，2 24小时的
        List<FirePointEntity> list =new ArrayList<>();
        String nowDate = DateUtil.getStrFromDate(new Date(), null);
        String nowDateHHmm = DateUtil.getStrFromDate(new Date(), "HHmm");


        if (type==1){
            QueryWrapper<FirePointEntity> queryWrapper = new QueryWrapper<>();

            queryWrapper.eq("acq_date",nowDate)
                    .select("MAX(acq_time) as maxTime");
            Long maxTime = firePointService.getObj(queryWrapper, obj -> Long.parseLong(obj.toString()));
            //区间查询
            queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("acq_date",nowDate)
                    .ge("acq_time",maxTime-5)
                    .le("acq_time",maxTime);
             list = firePointService.list(queryWrapper);
        }
        if (type==2){
            Date dateBefore = DateUtil.getDateBefore(new Date(), 1);
            String beforeDate = DateUtil.getStrFromDate(dateBefore, null);
            list = firePointService.queryListByTime(nowDate, beforeDate, nowDateHHmm);
        }
        return R.ok().put("firePoinList", list);
    }

    /**
     * 查询过去火点信息
     */
    @RequestMapping("/queryFirePointOld")
    public R queryFirePointOld(@RequestBody Map<String, Object> params) {
        int type = CheckUtil.objToInteger(params.get("type"));//1 7天,2 30天, 3 360天
        QueryWrapper<FirePointEntity> queryWrapper = new QueryWrapper<>();
        String nowDate = DateUtil.getStrFromDate(new Date(), null);
        queryWrapper.eq("acq_date",nowDate);
        List<FirePointEntity> list = firePointService.list(queryWrapper);
        for (FirePointEntity firePointEntity:list) {
            firePointEntity.setShowFire(1l);
        }

        if (type==1){
            List<FirePointEntity> sixD = redisUtils.getList("sixD", FirePointEntity.class);
            list.addAll(sixD);
        }
        if (type==2){
            List<FirePointEntity> thirtyD = redisUtils.getList("thirtyD", FirePointEntity.class);
            list.addAll(thirtyD);
        }
        if (type==3){
            List<FirePointEntity> yearD = redisUtils.getList("yearD", FirePointEntity.class);
            list.addAll(yearD);
        }
        return R.ok().put("firePoinList", list);
    }




}
