/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.app.form;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.renren.common.validator.group.AddGroup;
import io.renren.common.validator.group.UpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

/**
 * 注册表单
 *
 * @author Mark sunlightcs@gmail.com
 */
@Data
@ApiModel(value = "注册表单")
public class RegisterForm {
    @ApiModelProperty(value = "手机号")
    @NotBlank(message="手机号不能为空")
    private String mobile;

    @ApiModelProperty(value = "密码")
    @NotBlank(message="密码不能为空")
    private String password;


    /**
     * 邮箱
     */
    @NotBlank(message="邮箱不能为空")
    @ApiModelProperty(value = "邮箱")
    private String email;

    /**
     *姓
     */
    @NotBlank(message="姓不能为空")
    private String familyName;

    /**
     *名
     */
    @NotBlank(message="名不能为空")
    private String givenName;

    /**
     * 生日
     */
    private Date birthDate;

    /**
     * 语言  0 中 1英 2西班牙
     */
    private Integer language;

}
