package com.xuzy.hotel.shippingbatchorder.entity;

import java.io.Serializable;
import java.util.Date;

import org.jeecgframework.poi.excel.annotation.Excel;

import java.math.BigDecimal;

/**
 * 描述：批量发货订单表
 * @author: www.jeecg.org
 * @since：2018年11月21日 20时52分03秒 星期三 
 * @version:1.0
 */
public class CvcShippingBatchOrderEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Excel(name="订单号")
	@Excel(name="快递名称")
	@Excel(name="快递单号")
	@Excel(name="预计达到时间")
	
	private String  isOrderBatchNoOkStr;
	/**
	 * 展示状态
	 */
	private String showString;
	
	
		return isOrderBatchNoOkStr;
	}
	public void setIsOrderBatchNoOkStr(String isOrderBatchNoOkStr) {
		this.isOrderBatchNoOkStr = isOrderBatchNoOkStr;
	}
	public String getShowString() {
		return showString;
	}
	public void setShowString(String showString) {
		this.showString = showString;
	}
	public Integer getId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getShippingName() {
}
