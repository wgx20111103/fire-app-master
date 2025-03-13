package io.renren.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 设备表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2025-03-13 09:14:06
 */
@Data
@TableName("device")
public class DeviceEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId
	private Integer id;
	/**
	 * 设备名称
	 */
	private String deviceName;
	/**
	 * 设备序列号
	 */
	private String deviceNo;
	/**
	 * 设备类型 0喷淋
	 */
	private Integer deviceType;
	/**
	 * 设备通讯地址 如mqtt
	 */
	private String ipAddress;
	/**
	 * 报警距离
	 */
	private Double alarmDistance;
	/**
	 * 倒计时时间  单位min
	 */
	private Integer timer;
	/**
	 * 报警过后重启推送报警时间，单位min
	 */
	private Integer collectTime;

}
