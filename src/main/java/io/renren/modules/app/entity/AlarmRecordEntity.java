package io.renren.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 报警记录表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2025-03-10 10:31:39
 */
@Data
@TableName("alarm_record")
public class AlarmRecordEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId
	private Integer id;
	/**
	 * 报警位置
	 */
	private String alarmAddress;
	/**
	 * 经度
	 */
	private Double lon;
	/**
	 * 纬度
	 */
	private Double lat;
	/**
	 * 距离火点长度
	 */
	private Integer length;
	/**
	 * 火点时间 分钟
	 */
	private Integer time;
	/**
	 * 报警时间
	 */
	private Date alarmTime;
	/**
	 * 报警房产id
	 */
	private Long houseId;

}
