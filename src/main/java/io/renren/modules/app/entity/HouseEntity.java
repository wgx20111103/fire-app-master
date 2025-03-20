package io.renren.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 房产资源表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2025-03-10 10:31:39
 */
@Data
@TableName("house")
public class HouseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId
	private Long id;
	/**
	 * 房产名称
	 */
	private String houseName;
	/**
	 * 设备绑定id
	 */
	private Integer deviceBindingId;
	/**
	 * 房产地址名称
	 */
	private String houseAddress;
	/**
	 * 经度
	 */
	private Double lon;
	/**
	 * 纬度
	 */
	private Double lat;
	/**
	 * 控制人1
	 */
	private String userO;
	/**
	 * 控制人2
	 */
	private String userT;

	/**
	 * 所属人
	 */
	private String user;

	/**
	 * 报警距离
	 */
	private Double alarmDistance;

}
