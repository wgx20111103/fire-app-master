

package io.renren.modules.sys.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.sys.entity.Course;
import io.renren.modules.sys.entity.MyCourse;
import io.renren.modules.sys.service.CourseService;
import io.renren.modules.sys.service.MyCourseService;
import io.renren.modules.sys.service.SysUserRoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sys/course")
public class CourseController extends AbstractController {
    @Autowired
    private CourseService courseService;

    @Autowired
    private MyCourseService myCourseService;
    @Autowired
    private SysUserRoleService sysUserRoleService;

    @GetMapping("/list")
    @RequiresPermissions("sys:course:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = courseService.queryPage(params);
        List<Long> roleIdList = sysUserRoleService.queryRoleIdList(getUserId());
        return R.ok().put("page", page).put("userRoles",roleIdList);
    }







    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        Course course = courseService.getById(id);
        return R.ok().put("course", course);
    }

    @PostMapping("/save")
    @RequiresPermissions("sys:course:save")
    public R save(@RequestBody Course course){
        courseService.saveOrUpdate(course);
        return R.ok();
    }

    //学生选择课程
    @PostMapping("/curriculaVariable")
    public R curriculaVariable(@RequestBody  Long id){
        Course course = courseService.getById(id);
        MyCourse myCourse=new MyCourse();
        myCourse.setName(course.getName());
        myCourse.setTeacherNum(course.getTeacherNum());
        myCourse.setSkTime(course.getSkTime());
        myCourse.setUserId(getUserId().intValue());
        myCourse.setUserName(getUser().getUsername());
        myCourse.setKcType(course.getKcType());
        myCourse.setScore(course.getScore());
        myCourse.setSkPlace(course.getSkPlace());
        myCourse.setWeeksNumber(course.getWeeksNumber());
        myCourse.setUserScore("未打分");//默认未打分
        myCourseService.saveOrUpdate(myCourse);
        return R.ok();
    }





    @PostMapping("/update")
    @RequiresPermissions("sys:course:update")
    public R update(@RequestBody Course course){
        courseService.updateById(course);
        return R.ok();
    }

    @PostMapping("/delete")
    @RequiresPermissions("sys:course:delete")
    public R update(@RequestBody  Long[] ids){
        for (int i = 0; i < ids.length; i++) {
            Long id=ids[i];
            courseService.removeById(id);
        }
        return R.ok();
    }


}
