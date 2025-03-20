package io.renren.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 火点记录表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2025-03-18 17:23:39
 */
@Data
@TableName("fire_point")
public class FirePointEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 纬度
	 */
	private Double latitude;
	/**
	 * 经度
	 */
	private Double longitude;
	/**
	 * 卫星测量的亮度值（可能与温度相关）
	 */
	private Double brightTi4;
	/**
	 * 扫描角度或参数
	 */
	private Double scan;
	/**
	 * 轨道角度或参
	 */
	private Double track;
	/**
	 * 数据采集日期
	 */
	private String acqDate;
	/**
	 * 数据采集时间（可能是UTC时间的一部分）
	 */
	private Integer acqTime;
	/**
	 * 火热辐射功率
	 */
	private Double frp;
	/**
	 * 昼夜标志（N可能表示夜间)
	 */
	private String daynight;

}
