

package io.renren.modules.app.controller;


import io.renren.common.annotation.ComLogin;
import io.renren.common.utils.*;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.app.entity.FaqEntity;
import io.renren.modules.app.entity.RemarkEntity;
import io.renren.modules.app.entity.UserEntity;
import io.renren.modules.app.form.RegisterForm;
import io.renren.modules.app.service.FaqService;
import io.renren.modules.app.service.RemarkService;
import io.renren.modules.app.service.UserService;
import io.renren.modules.app.utils.EmailUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 注册
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@RequestMapping("/app")
@Api("APP注册接口")
public class AppRegisterController {
    @Autowired
    private UserService userService;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private RemarkService remarkService;

    @Autowired
    private FaqService faqService;





    @PostMapping("register")
    @ApiOperation("注册")
    public R register(@RequestBody RegisterForm form){
        //表单校验
        ValidatorUtils.validateEntity(form);
        long id = SnowflakeIdGenerator.getId();
        UserEntity user = new UserEntity();
        user.setMobile(form.getMobile());
        user.setEmail(form.getEmail());
        user.setUserId(id);
        user.setCreateUserId(id);
        user.setCreateTime(new Date());
        String salt = RandomStringUtils.randomAlphanumeric(20);
        user.setSalt(salt);
        user.setPassword(new Sha256Hash(form.getPassword(), salt).toHex());
        user.setCreateTime(new Date());
        user.setStatus(1);
        user.setLanguage(form.getLanguage());
        user.setFamilyName(form.getFamilyName());
        user.setGivenName(form.getGivenName());
        user.setBirthDate(form.getBirthDate());
        user.setScore(500);
        userService.save(user);
        return R.ok();
    }

    @PostMapping("sendEmail")
    @ApiOperation("发送邮件")
    @ResponseBody
    public R sendEmail(@RequestBody String email){

        String code = EmailUtils.generateVerificationCode();
        EmailUtils.sendEmail(email, "验证码", code);
        redisUtils.set(email,code,60);
        return R.ok();
    }

    @PostMapping("validationEmailCode")
    @ApiOperation("校验邮件验证码")
    public R validationEmailCode(@RequestBody Map<String, Object> params) {

        String email = CheckUtil.objToString(params.get("email"));
        String code = CheckUtil.objToString(params.get("code"));
        String rcode = redisUtils.get(email);
        if (!code.equals(rcode)){
            return R.error(ErrorCode.ERR_CODE,"验证码错误!");
        }
        return R.ok();
    }

    @PostMapping("password")
    @ApiOperation("APP修改密码")
    public R password(@RequestBody Map<String, Object> params) {

        String email = CheckUtil.objToString(params.get("email"));
        String password = CheckUtil.objToString(params.get("password"));
        String code = CheckUtil.objToString(params.get("code"));
        String rcode = redisUtils.get(email);
        if (!code.equals(rcode)){
            return R.error(ErrorCode.ERR_NO_CODE,"验证码失效!");
        }
        boolean flg = userService.updatePassword(email, password);
        if (flg){
            return R.error();
        }
        return R.ok();
    }

    @PostMapping("userDetail")
    @ApiOperation("用户详情")
    public R userDetail(HttpServletRequest request) {

        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)){
            return R.error(503, "token已失效,请重新登录!");
        }
        UserEntity userInfoVo = redisUtils.get(token, UserEntity.class);

        return R.ok().put("user", userInfoVo);
    }

    @PostMapping("updateLanguage")
    @ApiOperation("修改语言")
    public R updateLanguage(@RequestBody Map<String, Object> params,HttpServletRequest request) {

        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)){
            return R.error(503, "token已失效,请重新登录!");
        }
        UserEntity userInfoVo = redisUtils.get(token, UserEntity.class);
        int language = CheckUtil.objToInteger(params.get("language"));
        userInfoVo.setLanguage(language);

        boolean flg = userService.updateByUserId(userInfoVo);
        if (flg){
            ComLogin.loginUserInfoTokenMap.put(token,userInfoVo);
            redisUtils.set(token, userInfoVo);
        }
        return R.ok();
    }

    @PostMapping("updateUser")
    @ApiOperation("修改个人信息")
    public R updateUser(@RequestBody Map<String, Object> params,HttpServletRequest request) {

        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)){
            return R.error(503, "token已失效,请重新登录!");
        }
        UserEntity userInfoVo = redisUtils.get(token, UserEntity.class);
        String familyName = CheckUtil.objToString(params.get("familyName"));
        String givenName = CheckUtil.objToString(params.get("givenName"));
        Date birthDate = CheckUtil.objToDate(params.get("birthDate"));
        userInfoVo.setFamilyName(familyName);
        userInfoVo.setGivenName(givenName);
        userInfoVo.setBirthDate(birthDate);

        boolean flg = userService.updateByUserId(userInfoVo);
        if (flg){
            ComLogin.loginUserInfoTokenMap.put(token,userInfoVo);
            redisUtils.set(token, userInfoVo);
        }
        return R.ok();
    }

    @PostMapping("updatePhone")
    @ApiOperation("修改手机号及邮箱")
    public R updatePhone(@RequestBody Map<String, Object> params,HttpServletRequest request) {

        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)){
            return R.error(503, "token已失效,请重新登录!");
        }
        UserEntity userInfoVo = redisUtils.get(token, UserEntity.class);
        String mobile = CheckUtil.objToString(params.get("mobile"));
        String email = CheckUtil.objToString(params.get("email"));

        userInfoVo.setMobile(mobile);
        userInfoVo.setEmail(email);

        boolean flg = userService.updateByUserId(userInfoVo);
        if (flg){
            ComLogin.loginUserInfoTokenMap.put(token,userInfoVo);
            redisUtils.set(token, userInfoVo);
        }
        return R.ok();
    }

    @PostMapping("queryRemark")
    @ApiOperation("获取支持信息")
    public R queryRemark(HttpServletRequest request) {

        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)){
            return R.error(503, "token已失效,请重新登录!");
        }
        UserEntity userInfoVo = redisUtils.get(token, UserEntity.class);

        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("language",userInfoVo.getLanguage() );
        List<RemarkEntity> remarkEntities = remarkService.listByMap(columnMap);
        RemarkEntity remarkEntity = remarkEntities.get(0);
        List<FaqEntity> faqEntities = faqService.listByMap(columnMap);
        return R.ok().put("remark", remarkEntity).put("faqList", faqEntities);
    }

}
