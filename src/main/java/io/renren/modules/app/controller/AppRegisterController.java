

package io.renren.modules.app.controller;


import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
import io.renren.common.utils.*;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.app.entity.UserEntity;
import io.renren.modules.app.form.RegisterForm;
import io.renren.modules.app.service.UserService;
import io.renren.modules.app.utils.EmailUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
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
        user.setPassword(new Sha256Hash(user.getPassword(), salt).toHex());
        user.setCreateTime(new Date());
        user.setStatus(1);
        user.setLanguage(form.getLanguage());
        user.setFamilyName(form.getFamilyName());
        user.setGivenName(form.getGivenName());
        user.setBirthDate(form.getBirthDate());
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
}
