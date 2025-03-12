package io.renren.modules.app.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.renren.common.utils.RedisUtils;
import io.renren.modules.app.entity.HouseEntity;
import io.renren.modules.app.entity.UserEntity;
import io.renren.modules.app.service.HouseService;
import io.swagger.annotations.ApiOperation;
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
 * 房产资源表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2025-03-10 10:31:39
 */
@RestController
@RequestMapping("app/house")
public class HouseController {
    @Autowired
    private HouseService houseService;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("generator:house:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = houseService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("generator:house:info")
    public R info(@PathVariable("id") Long id){
		HouseEntity house = houseService.getById(id);

        return R.ok().put("house", house);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("generator:house:save")
    public R save(@RequestBody HouseEntity house){
		houseService.save(house);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("generator:house:update")
    public R update(@RequestBody HouseEntity house){
		houseService.updateById(house);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("generator:house:delete")
    public R delete(@RequestBody Long[] ids){
		houseService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * 查询所管理的房产
     */
    @RequestMapping("/queryList")
    @ApiOperation("获取房产")
    public R queryList(HttpServletRequest request){
        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)){
            return R.error(503, "token已失效,请重新登录!");
        }
        UserEntity userInfoVo = redisUtils.get(token, UserEntity.class);
        LambdaQueryWrapper<HouseEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(HouseEntity::getUserO, userInfoVo.getUserId()).or().eq(HouseEntity::getUserT, userInfoVo.getUserId());
        List<HouseEntity> list = houseService.list(wrapper);
        return R.ok().put("houseList", list);
    }

}
