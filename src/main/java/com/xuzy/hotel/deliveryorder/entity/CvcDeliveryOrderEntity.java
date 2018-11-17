package com.xuzy.hotel.deliveryorder.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import com.xuzy.hotel.order.entity.CvcDeliveryGoodsEntity;

/**
 * 描述：商品表
 * @author: www.jeecg.org
 * @since：2018年11月03日 13时07分49秒 星期六 
 * @version:1.0
 */
public class CvcDeliveryOrderEntity extends CvcDeliveryGoodsEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/**
	 * 发货时间格式化
	 */
	private String addTimeString;
	
	/**
	 *尺码名称
	 */
	private String sizeName;
	/**
	 *颜色名称
	 */
	private String colorName;
	
	
	
		return addTimeString;
	}
	public void setAddTimeString(String addTimeString) {
		this.addTimeString = addTimeString;
	}
	public String getSizeName() {
		return sizeName;
	}
	public void setSizeName(String sizeName) {
		this.sizeName = sizeName;
	}
	public String getColorName() {
		return colorName;
	}
	public void setColorName(String colorName) {
		this.colorName = colorName;
	}
	public Integer getDeliveryId() {
}
