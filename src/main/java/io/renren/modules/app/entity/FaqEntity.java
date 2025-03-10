package io.renren.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * Faq信息表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2025-03-10 10:11:28
 */
@Data
@TableName("faq")
public class FaqEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private Integer id;
	/**
	 * 问
	 */
	private String question;
	/**
	 * 答
	 */
	private String answer;
	/**
	 * 排序
	 */
	private Integer sort;
	/**
	 * 语言 0 中文 1英文 2西班牙
	 */
	private Integer language;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 创建人id
	 */
	private Long createUserId;

}
