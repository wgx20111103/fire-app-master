

package io.renren.modules.sys.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.sys.entity.Inform;
import io.renren.modules.sys.entity.News;
import io.renren.modules.sys.service.NewsService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/sys/news")
public class NewsController extends AbstractController {
    @Autowired
    private NewsService newsService;


    @GetMapping("/list")
    @RequiresPermissions("sys:news:list")
    public R list(@RequestParam Map<String, Object> params){
        params.put("kind","党建要闻");
        PageUtils page = newsService.queryPage(params);
        return R.ok().put("page", page);
    }

    @GetMapping("/list_zzfc")
    @RequiresPermissions("sys:news:list")
    public R list_zzfc(@RequestParam Map<String, Object> params){
        params.put("kind","组织风采");
        PageUtils page = newsService.queryPage(params);
        return R.ok().put("page", page);
    }

    @GetMapping("/list_zzfz")
    @RequiresPermissions("sys:news:list")
    public R list_zzfz(@RequestParam Map<String, Object> params){
        params.put("kind","组织发展");
        PageUtils page = newsService.queryPage(params);
        return R.ok().put("page", page);
    }



    @GetMapping("/info/{id}")
    @RequiresPermissions("sys:news:info")
    public R info(@PathVariable("id") Long id){
        News news = newsService.getById(id);
        return R.ok().put("news", news);
    }

    @PostMapping("/save")
    @RequiresPermissions("sys:news:save")
    public R save(@RequestBody News news){
        news.setCreateBy(getUser().getUsername());
        news.setCreateTime(new Date());
        newsService.saveOrUpdate(news);
        return R.ok();
    }


    @PostMapping("/update")
    @RequiresPermissions("sys:news:update")
    public R update(@RequestBody News news){
        newsService.updateById(news);
        return R.ok();
    }

    @PostMapping("/delete")
    public R delete(@RequestBody  Long[] ids){
        for (int i = 0; i < ids.length; i++) {
            Long id=ids[i];
            newsService.removeById(id);
        }
        return R.ok();
    }


}
