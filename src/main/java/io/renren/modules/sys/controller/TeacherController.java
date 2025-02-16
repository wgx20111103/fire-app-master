

package io.renren.modules.sys.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.sys.entity.Teacher;
import io.renren.modules.sys.service.SysUserRoleService;
import io.renren.modules.sys.service.TeacherService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("/sys/teacher")
public class TeacherController extends AbstractController {
    @Autowired
    private TeacherService teacherService;

    @Autowired
    private SysUserRoleService sysUserRoleService;


    @GetMapping("/list")
    @RequiresPermissions("sys:teacher:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = teacherService.queryPage(params);
        List<Long> roleIdList = sysUserRoleService.queryRoleIdList(getUserId());
        return R.ok().put("page", page).put("userRoles",roleIdList);
    }


    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        Teacher teacher = teacherService.getById(id);
        return R.ok().put("teacher", teacher);
    }

    @PostMapping("/save")
    @RequiresPermissions("sys:teacher:save")
    public R save(@RequestBody Teacher teacher){
        teacherService.saveOrUpdate(teacher);
        return R.ok();
    }


    public static String dealDateFormat(String oldDate) {
        Date date1 = null;
        DateFormat df2 = null;
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date date = df.parse(oldDate);
            SimpleDateFormat df1 = new SimpleDateFormat ("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
            date1 = df1.parse(date.toString());
            df2 = new SimpleDateFormat("yyyy-MM-dd");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return df2.format(date1);
    }

    @PostMapping("/update")
    @RequiresPermissions("sys:teacher:update")
    public R update(@RequestBody Teacher teacher){
        teacherService.updateById(teacher);
        return R.ok();
    }

    @PostMapping("/delete")
    @RequiresPermissions("sys:teacher:delete")
    public R update(@RequestBody  Long[] ids){
        for (int i = 0; i < ids.length; i++) {
            Long id=ids[i];
            teacherService.removeById(id);
        }
        return R.ok();
    }


}
