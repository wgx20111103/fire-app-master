

package io.renren.modules.sys.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.sys.entity.Cla;
import io.renren.modules.sys.service.ClaService;
import io.renren.modules.sys.service.SysUserRoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sys/cla")
public class ClaController extends AbstractController {
    @Autowired
    private ClaService claService;

    @Autowired
    private SysUserRoleService sysUserRoleService;


    @GetMapping("/list")
    @RequiresPermissions("sys:cla:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = claService.queryPage(params);
        List<Long> roleIdList = sysUserRoleService.queryRoleIdList(getUserId());
        return R.ok().put("page", page).put("userRoles",roleIdList);
    }


    @PostMapping("/getCla")
    public R getCla(@RequestParam Map<String, Object> params){
        List<Cla> list = claService.list();
        return R.ok().put("list", list);
    }



    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        Cla cla = claService.getById(id);
        return R.ok().put("cla", cla);
    }

    @PostMapping("/save")
    @RequiresPermissions("sys:cla:save")
    public R save(@RequestBody Cla cla){
        claService.saveOrUpdate(cla);
        return R.ok();
    }

    @PostMapping("/update")
    @RequiresPermissions("sys:cla:update")
    public R update(@RequestBody Cla cla){
        claService.updateById(cla);
        return R.ok();
    }

    @PostMapping("/delete")
    public R update(@RequestBody  Long[] ids){
        for (int i = 0; i < ids.length; i++) {
            Long id=ids[i];
            claService.removeById(id);
        }
        return R.ok();
    }


}
