package io.renren.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 支持信息表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2025-03-10 10:03:42
 */
@Data
@TableName("remark")
public class RemarkEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private Integer id;
	/**
	 * 奖励机制
	 */
	private String reward;
	/**
	 * 支持邮箱
	 */
	private String emailSp;
	/**
	 * 支持手机号
	 */
	private String phoneSp;
	/**
	 * 支持网站地址
	 */
	private String officeWeb;
	/**
	 * 语言 0 中文 1英文 2西班牙
	 */
	private Integer language;

}
