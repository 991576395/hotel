package com.xuzy.hotel.ordergoods.entity;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

/**
 * 描述：订单商品表
 * 
 * @author: www.jeecg.org
 * @since：2018年11月25日 17时10分02秒 星期日
 * @version:1.0
 */
public class CvcOrderGoodsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * rec_id
	 */
	private Integer recId;
	/**
	 * order_id
	 */
	private Integer orderId;
	/**
	 * goods_id
	 */
	private String goodsId;
	/**
	 * goods_name
	 */
	private String goodsName;
	/**
	 * goods_sn
	 */
	private String goodsSn;
	/**
	 * product_id
	 */
	private Integer productId;
	/**
	 * goods_number
	 */
	private Integer goodsNumber;
	/**
	 * market_price
	 */
	private BigDecimal marketPrice;
	/**
	 * goods_price
	 */
	private BigDecimal goodsPrice;
	/**
	 * goods_attr
	 */
	private String goodsAttr;
	/**
	 * send_number
	 */
	private Integer sendNumber;
	/**
	 * is_real
	 */
	private Integer isReal;
	/**
	 * extension_code
	 */
	private String extensionCode;
	/**
	 * parent_id
	 */
	private Integer parentId;
	/**
	 * is_gift
	 */
	private Integer isGift;
	/**
	 * goods_attr_id
	 */
	private String goodsAttrId;
	/**
	 * upc_id
	 */
	private Integer upcId;
	/**
	 * 所属分销商用户id
	 */
	private Integer superiorId;
	/**
	 * 佣金
	 */
	private BigDecimal commission;
	/**
	 * 上级用户提点率
	 */
	private Integer superiorSome;
	/**
	 * 卖家id(此商品来自哪个店铺)
	 */
	private Integer sellerId;
	/**
	 * 商品sku
	 */
	private String systemSku;
	/**
	 * fav_goods_name
	 */
	private String favGoodsName;
	/**
	 * 所属项目组
	 */
	private String project;
	/**
	 * 团号
	 */
	private Integer groupNo;
	/**
	 * 进销存商品id
	 */
	private Integer centerId;

	private BigDecimal formatedSubtotal;

	public BigDecimal getFormatedSubtotal() {
		return formatedSubtotal;
	}

	public void setFormatedSubtotal(BigDecimal formatedSubtotal) {
		this.formatedSubtotal = formatedSubtotal;
	}

	public Integer getRecId() {
		return this.recId;
	}

	public void setRecId(Integer recId) {
		this.recId = recId;
	}

	public Integer getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public String getGoodsName() {
		return this.goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsSn() {
		return this.goodsSn;
	}

	public void setGoodsSn(String goodsSn) {
		this.goodsSn = goodsSn;
	}

	public Integer getProductId() {
		return this.productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getGoodsNumber() {
		return this.goodsNumber;
	}

	public void setGoodsNumber(Integer goodsNumber) {
		this.goodsNumber = goodsNumber;
	}

	public BigDecimal getMarketPrice() {
		return this.marketPrice;
	}

	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
	}

	public BigDecimal getGoodsPrice() {
		return this.goodsPrice;
	}

	public void setGoodsPrice(BigDecimal goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public String getGoodsAttr() {
		return this.goodsAttr;
	}

	public void setGoodsAttr(String goodsAttr) {
		this.goodsAttr = goodsAttr;
	}

	public Integer getSendNumber() {
		return this.sendNumber;
	}

	public void setSendNumber(Integer sendNumber) {
		this.sendNumber = sendNumber;
	}

	public Integer getIsReal() {
		return this.isReal;
	}

	public void setIsReal(Integer isReal) {
		this.isReal = isReal;
	}

	public String getExtensionCode() {
		return this.extensionCode;
	}

	public void setExtensionCode(String extensionCode) {
		this.extensionCode = extensionCode;
	}

	public Integer getParentId() {
		return this.parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getIsGift() {
		return this.isGift;
	}

	public void setIsGift(Integer isGift) {
		this.isGift = isGift;
	}

	public String getGoodsAttrId() {
		return this.goodsAttrId;
	}

	public void setGoodsAttrId(String goodsAttrId) {
		this.goodsAttrId = goodsAttrId;
	}

	public Integer getUpcId() {
		return this.upcId;
	}

	public void setUpcId(Integer upcId) {
		this.upcId = upcId;
	}

	public Integer getSuperiorId() {
		return this.superiorId;
	}

	public void setSuperiorId(Integer superiorId) {
		this.superiorId = superiorId;
	}

	public BigDecimal getCommission() {
		return this.commission;
	}

	public void setCommission(BigDecimal commission) {
		this.commission = commission;
	}

	public Integer getSuperiorSome() {
		return this.superiorSome;
	}

	public void setSuperiorSome(Integer superiorSome) {
		this.superiorSome = superiorSome;
	}

	public Integer getSellerId() {
		return this.sellerId;
	}

	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}

	public String getSystemSku() {
		return this.systemSku;
	}

	public void setSystemSku(String systemSku) {
		this.systemSku = systemSku;
	}

	public String getFavGoodsName() {
		return this.favGoodsName;
	}

	public void setFavGoodsName(String favGoodsName) {
		this.favGoodsName = favGoodsName;
	}

	public String getProject() {
		return this.project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public Integer getGroupNo() {
		return this.groupNo;
	}

	public void setGroupNo(Integer groupNo) {
		this.groupNo = groupNo;
	}

	public Integer getCenterId() {
		return this.centerId;
	}

	public void setCenterId(Integer centerId) {
		this.centerId = centerId;
	}
}
