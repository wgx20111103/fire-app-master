
package io.renren.modules.sys.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.sys.entity.Student;
import io.renren.modules.sys.service.StudentService;
import io.renren.modules.sys.service.SysUserRoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sys/student")
public class StudentController extends AbstractController {
    @Autowired
    private StudentService service;

    @Autowired
    private SysUserRoleService sysUserRoleService;


    @GetMapping("/list")
    @RequiresPermissions("sys:student:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = service.queryPage(params);
        List<Long> roleIdList = sysUserRoleService.queryRoleIdList(getUserId());
        return R.ok().put("page", page).put("userRoles",roleIdList);
    }


    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        Student student = service.getById(id);
        return R.ok().put("student", student);
    }

    @PostMapping("/save")
    @RequiresPermissions("sys:student:save")
    public R save(@RequestBody Student student){
        service.saveOrUpdate(student);
        return R.ok();
    }

    @PostMapping("/update")
    @RequiresPermissions("sys:student:update")
    public R update(@RequestBody Student student){
        service.updateById(student);
        return R.ok();
    }

    @PostMapping("/delete")
    public R update(@RequestBody  Long[] ids){
        for (int i = 0; i < ids.length; i++) {
            Long id=ids[i];
            service.removeById(id);
        }
        return R.ok();
    }


}
