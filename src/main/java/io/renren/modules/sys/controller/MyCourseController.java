

package io.renren.modules.sys.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.sys.entity.MyCourse;
import io.renren.modules.sys.service.MyCourseService;
import io.renren.modules.sys.service.SysUserRoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sys")
public class MyCourseController extends AbstractController {
    @Autowired
    private MyCourseService myCourseService;
    @Autowired
    private SysUserRoleService sysUserRoleService;


    @GetMapping("score/list")
    @RequiresPermissions("sys:score:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = myCourseService.queryPage(params);
        List<Long> roleIdList = sysUserRoleService.queryRoleIdList(getUserId());
        return R.ok().put("page", page).put("userRoles",roleIdList);


    }

    @GetMapping("score/myList")
    @RequiresPermissions("sys:myScore:list")
    public R myList(@RequestParam Map<String, Object> params){
        params.put("userName",getUser().getUsername());
        PageUtils page = myCourseService.queryPage(params);
        return R.ok().put("page", page);
    }


    @GetMapping("score/info/{id}")
    public R info(@PathVariable("id") Long id){
        MyCourse myCourse = myCourseService.getById(id);
        return R.ok().put("myCourse", myCourse);
    }

    @PostMapping("score/save")
    @RequiresPermissions("sys:score:save")
    public R save(@RequestBody MyCourse myCourse){
        myCourseService.save(myCourse);
        return R.ok();
    }
//
//    //学生选择课程
//    @PostMapping("score/update")
//    @RequiresPermissions("sys:score:update")
//    public R curriculaVariable(@RequestBody MyCourse myCourse){
//        myCourseService.saveOrUpdate(myCourse);
//        return R.ok();
//    }



    @PostMapping("score/update")
    @RequiresPermissions("sys:score:update")
    public R update(@RequestBody MyCourse myCourse){
        myCourseService.updateById(myCourse);
        return R.ok();
    }

    @PostMapping("score/delete")
    @RequiresPermissions("sys:score:delete")
    public R update(@RequestBody  Long[] ids){
        for (int i = 0; i < ids.length; i++) {
            Long id=ids[i];
            myCourseService.removeById(id);
        }
        return R.ok();
    }


}
