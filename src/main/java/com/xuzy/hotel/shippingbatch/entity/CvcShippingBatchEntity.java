package com.xuzy.hotel.shippingbatch.entity;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

/**
 * 描述：批量发货表
 * @author: www.jeecg.org
 * @since：2018年11月20日 20时41分17秒 星期二 
 * @version:1.0
 */
public class CvcShippingBatchEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/**
	 * 上传时间格式化
	 */
	private String addTimeFormat;
	
	/**
	 * 展示字符
	 */
	private String msgStatusStr;
	/**
	 * 上传失败数量
	 */
	private int shippingCountNot;
	
	
	
	public String getMsgStatusStr() {
		return msgStatusStr;
	}
	public void setMsgStatusStr(String msgStatusStr) {
		this.msgStatusStr = msgStatusStr;
	}
	public int getShippingCountNot() {
		return shippingCountNot;
	}
	public void setShippingCountNot(int shippingCountNot) {
		this.shippingCountNot = shippingCountNot;
	}
	public String getAddTimeFormat() {
		return addTimeFormat;
	}
	public void setAddTimeFormat(String addTimeFormat) {
		this.addTimeFormat = addTimeFormat;
	}
	public Integer getId() {
}
