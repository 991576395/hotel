package com.xuzy.hotel.order.web;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.minidao.pojo.MiniDaoPage;
import org.jeecgframework.p3.core.common.utils.AjaxJson;
import org.jeecgframework.p3.core.page.SystemTools;
import org.jeecgframework.p3.core.web.BaseController;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.vo.MapExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.jeecgframework.poi.util.ExcelUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.appinterface.app.base.exception.XuException;
import com.util.PHPAndJavaSerialize;
import com.util.PhpDateUtils;
import com.xuzy.hotel.deliveryinfo.entity.CvcDeliveryInfoEntity;
import com.xuzy.hotel.deliveryinfo.service.CvcDeliveryInfoService;
import com.xuzy.hotel.deliveryorder.entity.CvcDeliveryOrderEntity;
import com.xuzy.hotel.deliveryorder.service.CvcDeliveryOrderService;
import com.xuzy.hotel.inventory.service.CvcInventoryTableServiceI;
import com.xuzy.hotel.order.entity.CvcOrderInfoEntity;
import com.xuzy.hotel.order.module.Data;
import com.xuzy.hotel.order.module.DeliveryInfoPojo;
import com.xuzy.hotel.order.module.DelivetyJson;
import com.xuzy.hotel.order.service.CvcOrderInfoService;
import com.xuzy.hotel.orderaction.entity.CvcOrderActionEntity;
import com.xuzy.hotel.orderaction.service.CvcOrderActionService;
import com.xuzy.hotel.ordergoods.entity.CvcOrderGoodsEntity;
import com.xuzy.hotel.ordergoods.service.CvcOrderGoodsService;
import com.xuzy.hotel.revokegoods.entity.CvcRevokeGoodsEntity;
import com.xuzy.hotel.revokegoods.service.CvcRevokeGoodsService;
import com.xuzy.hotel.shipping.entity.CvcShippingEntity;
import com.xuzy.hotel.shipping.service.CvcShippingService;
import com.xuzy.hotel.yldeliveryinfo.entity.CvcYlDeliveryInfoEntity;
import com.xuzy.hotel.yldeliveryinfo.service.CvcYlDeliveryInfoService;
import com.xuzy.hotel.ylrequest.ConmentHttp;
import com.xuzy.hotel.ylrequest.ResponseHead;
import com.xuzy.hotel.ylrequest.TukeRequestBody;
import com.xuzy.hotel.ylrequest.module.RequestBackingExchangeOrderJson;
import com.xuzy.hotel.ylrequest.module.RequestDeliveryExchangeOrderJson;
import com.xuzy.hotel.ylrequest.module.RequestExchangeProcessHistoryAddJson;
import com.xuzy.hotel.ylrequest.module.RequestOFFHarbourExchangeOrderJson;
import com.xuzy.hotel.ylrequest.module.RequestReNewExchangeEMSJson;
import com.xuzy.hotel.ylrequest.module.RequestReNewExchangeSignDateJson;
import com.xuzy.hotel.ylrequest.module.RequestRefuseExchangeOrderJson;
import com.xuzy.hotel.ylrequest.module.RequestReturnedExchangeOrderJson;
import com.xuzy.hotel.ylrequest.module.RequestSignInExchangeOrderJson;

/**
 * 描述：订单表
 *
 * @author: www.jeecg.org
 * @since：2018年10月28日 18时23分43秒 星期日
 * @version:1.0
 */
@Controller
@RequestMapping("/cvcOrderInfo")
public class CvcOrderInfoController extends BaseController {
	@Autowired
	private CvcOrderInfoService cvcOrderInfoService;

	@Autowired
	private CvcOrderGoodsService cvcOrderGoodsService;

	@Autowired
	private CvcDeliveryOrderService cvcDeliveryOrderService;

	@Autowired
	private CvcRevokeGoodsService cvcRevokeGoodsService;

	@Autowired
	private CvcOrderActionService cvcOrderActionService;

	@Autowired
	private CvcShippingService cvcShippingService;

	@Autowired
	private CvcDeliveryInfoService cvcDeliveryInfoService;

	@Autowired
	private CvcInventoryTableServiceI cvcInventoryTableService;

	@Autowired
	private CvcYlDeliveryInfoService cvcYlDeliveryInfoService;

	/**
	 * 页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "exceptionOrderList")
	public ModelAndView exceptionOrderList(HttpServletRequest request) {
		return new ModelAndView("com/xuzy/hotel/order/tExceptionOrderTableList");
	}

	/**
	 * easyui AJAX请求数据
	 *
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */
	@RequestMapping(params = "exceptionOrderListDatagrid")
	public void exceptionOrderListDatagrid(CvcOrderInfoEntity query,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		MiniDaoPage<CvcOrderInfoEntity> list = cvcOrderInfoService.getExceptionOrderListAll(query, dataGrid.getPage(), dataGrid.getRows());
		if(CollectionUtils.isNotEmpty(list.getResults())) {
			for (CvcOrderInfoEntity entity : list.getResults()) {
				entity.setAddTime(PhpDateUtils.parseDate(Long.parseLong(entity.getAddTime()), "yyyy-MM-dd HH:mm:ss"));
				entity.setGetTime(PhpDateUtils.parseDate(Long.parseLong(entity.getGetTime()), "yyyy-MM-dd HH:mm:ss"));
				if(entity.getHandleTime() != null && entity.getHandleTime() > 0) {
					entity.setHandleTimeFormat(PhpDateUtils.parseDate(entity.getHandleTime(), "yyyy-MM-dd HH:mm:ss"));
				}else {
					entity.setHandleTimeFormat("");
				}
			}
		}
		dataGrid.setResults(SystemTools.convertPaginatedList(list));
		dataGrid.setTotal(cvcOrderInfoService.getExceptionOrderCount(query));
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 签收超时订单列表
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "timeOutOrderList")
	public ModelAndView timeOutOrderList(HttpServletRequest request) {
		return new ModelAndView("com/xuzy/hotel/order/tTimeOutOrderTableList");
	}


	@RequestMapping(params = "timeOutOrderListDatagrid")
	public void timeOutOrderListDatagrid(CvcOrderInfoEntity query,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		MiniDaoPage<CvcOrderInfoEntity> list = cvcOrderInfoService.getTimeOutOrderList(dataGrid.getPage(), dataGrid.getRows());
		if(CollectionUtils.isNotEmpty(list.getResults())) {
			for (CvcOrderInfoEntity entity : list.getResults()) {
				try {
					Date deleveryDate = DateUtils.parseDate(entity.getDeliverySn(),new String[] {"yyyyMMddHHmmssSSS"});
					entity.setTimeOutValue(org.jeecgframework.core.util.DateUtils.getShortTime(deleveryDate));
				} catch (ParseException e) {
					e.printStackTrace();
				}

				CvcDeliveryInfoEntity cvcDeliveryInfoEntity = cvcDeliveryInfoService
						.getDeliveryInfosByInvoiceNo(entity.getInvoiceNo());
				List<Data> deliveryInfos = new ArrayList<>();
				if (cvcDeliveryInfoEntity != null && StringUtils.isNotEmpty(cvcDeliveryInfoEntity.getData())) {
					deliveryInfos = PHPAndJavaSerialize.unserializePHParray(cvcDeliveryInfoEntity.getData(), Data.class);
				}
				if(CollectionUtils.isNotEmpty(deliveryInfos)) {
					entity.setNewDeleveryInfo(deliveryInfos.get(0).getFtime()+":"+deliveryInfos.get(0).getContext());
				}
			}
		}
		dataGrid.setResults(SystemTools.convertPaginatedList(list));
		dataGrid.setTotal(cvcOrderInfoService.getTimeOutOrderCount());
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "toPushOrder")
	public ModelAndView toPushOrder(HttpServletRequest request) {
		String orderStatus = request.getParameter("orderStatus")==null?"":request.getParameter("orderStatus");
		request.setAttribute("orderStatus", orderStatus);
		String batchNo = request.getParameter("batchNo")==null?"":request.getParameter("batchNo");
		request.setAttribute("batchNo", batchNo);
		return new ModelAndView("com/xuzy/hotel/order/push");
	}
	/**
	 * 页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		String orderStatus = request.getParameter("orderStatus")==null?"":request.getParameter("orderStatus");
		request.setAttribute("orderStatus", orderStatus);
		String batchNo = request.getParameter("batchNo")==null?"":request.getParameter("batchNo");
		request.setAttribute("batchNo", batchNo);
		String exceptionStatus = request.getParameter("exceptionStatus")==null?"":request.getParameter("exceptionStatus");
		request.setAttribute("exceptionStatus", exceptionStatus);
		String isShow = request.getParameter("isShow")==null?"":request.getParameter("isShow");
		request.setAttribute("isShow", isShow);
		return new ModelAndView("com/xuzy/hotel/order/tOrderTableList");
	}

	/**
	 * 导出excel
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(CvcOrderInfoEntity entity,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		if(StringUtils.isNotEmpty(entity.getAddTime_begin1())) {
			try {
				Date date1 = DateUtils.parseDate(entity.getAddTime_begin1(),new String[] {"yyyy-MM-dd HH:mm:ss"}) ;
				entity.setAddTimeBegin((int)PhpDateUtils.getTime(date1));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		if(StringUtils.isNotEmpty(entity.getAddTime_end2())) {
			try {
				Date date1 = DateUtils.parseDate(entity.getAddTime_end2(),new String[] {"yyyy-MM-dd HH:mm:ss"}) ;
				entity.setAddTimeEnd((int)PhpDateUtils.getTime(date1));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if(StringUtils.isNotEmpty(entity.getGetTimeStart())) {
			try {
				Date date1 = DateUtils.parseDate(entity.getGetTimeStart(),new String[] {"yyyyMMdd"}) ;
				entity.setGetTimeStart(String.valueOf(PhpDateUtils.getTime(date1)));
			} catch (ParseException e) {
				entity.setGetTimeStart("");
			}
		}
		if(StringUtils.isNotEmpty(entity.getGetTimeEnd())) {
			try {
				Date date1 = DateUtils.parseDate(entity.getGetTimeEnd(),new String[] {"yyyyMMdd"});
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date1);
				calendar.add(Calendar.DAY_OF_YEAR, 1);
				entity.setGetTimeEnd(String.valueOf(PhpDateUtils.getTime(calendar.getTime())));
			} catch (ParseException e) {
				entity.setGetTimeEnd("");
			}
		}
		List<CvcOrderInfoEntity> entitys = cvcOrderInfoService.getExcelAll(entity);
		modelMap.put(NormalExcelConstants.FILE_NAME,DateFormatUtils.format(Calendar.getInstance(), "yyyyMMddHHmmss"));
		modelMap.put(NormalExcelConstants.CLASS,CvcOrderInfoEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams(null, null,
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,entitys);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}

	protected static final String HSSF = ".xls";
	protected static final String XSSF = ".xlsx";
	/**
	 * 导出excel  通过商品分sheet
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByGoodType")
	public void exportXlsByGoodType(CvcOrderInfoEntity entity,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		List<CvcOrderInfoEntity> entitys = cvcOrderInfoService.getExcelAll(entity);

		Map<String, List<CvcOrderInfoEntity>> sheetOrder = new HashMap<String, List<CvcOrderInfoEntity>>();
		Set<String> keys = new HashSet<>();
		for (CvcOrderInfoEntity cvcOrderInfoEntity : entitys) {
			String key = cvcOrderInfoEntity.getGoodName();
			if(sheetOrder.containsKey(key)) {
				sheetOrder.get(key).add(cvcOrderInfoEntity);
			}else {
				List<CvcOrderInfoEntity> cvcOrderInfoEntities = new ArrayList<>();
				cvcOrderInfoEntities.add(cvcOrderInfoEntity);
				sheetOrder.put(key, cvcOrderInfoEntities);
				keys.add(key);
			}
		}
		//
		//多个Map key title 对应表格Title key entity 对应表格对应实体 key data
		List<Map<String, Object>> sheetOrderList = new ArrayList<>();

		for (Map.Entry<String, List<CvcOrderInfoEntity>> map: sheetOrder.entrySet()) {
			Map<String, Object> exMap = new HashMap<>();

			exMap.put("title", new ExportParams(null, null,map.getKey()));
			exMap.put("entity", CvcOrderInfoEntity.class);
			exMap.put("data", map.getValue());
			sheetOrderList.add(exMap);
		}

		Workbook workbook = ExcelExportUtil.exportExcel(sheetOrderList,"");
		String codedFileName = DateFormatUtils.format(Calendar.getInstance(), "yyyyMMddHHmmss");
		if (workbook instanceof HSSFWorkbook) {
			codedFileName += HSSF;
		} else {
			codedFileName += XSSF;
		}
		try {
			if (isIE(request)) {
				codedFileName = java.net.URLEncoder.encode(codedFileName, "UTF8");
			} else {
				codedFileName = new String(codedFileName.getBytes("UTF-8"), "ISO-8859-1");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		response.setHeader("content-disposition", "attachment;filename=" + codedFileName);
		ServletOutputStream out;
		try {
			out = response.getOutputStream();
			workbook.write(out);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

//		modelMap.put(NormalExcelConstants.FILE_NAME,DateFormatUtils.format(Calendar.getInstance(), "yyyyMMddHHmmss"));
//		modelMap.put(NormalExcelConstants.CLASS,CvcOrderInfoEntity.class);
//		TemplateExportParams exportParams = new TemplateExportParams();
//		exportParams.setTemplateUrl(path);
//		exportParams.setScanAllsheet(true);
//		exportParams.setSheetName(keys.toArray(new String[keys.size()]));
//		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams(null, null,
//				"导出信息"));
//		modelMap.put(MapExcelConstants.MAP_LIST,sheetOrderList);
//		return MapExcelConstants.JEECG_MAP_EXCEL_VIEW;
	}
	public boolean isIE(HttpServletRequest request) {
		return (request.getHeader("USER-AGENT").toLowerCase().indexOf("msie") > 0 || request.getHeader("USER-AGENT").toLowerCase().indexOf("rv:11.0") > 0) ? true : false;
	}

	/**
	 * 导出excel
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportExceptionEXls")
	public String exportExceptionEXls(CvcOrderInfoEntity entity,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		List<CvcOrderInfoEntity> entitys = cvcOrderInfoService.getExceptionExcelAll(entity);
		modelMap.put(NormalExcelConstants.FILE_NAME,DateFormatUtils.format(Calendar.getInstance(), "yyyyMMddHHmmss"));
		modelMap.put(NormalExcelConstants.CLASS,CvcOrderInfoEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams(null, null,
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,entitys);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}

	/**
	 * easyui AJAX请求数据
	 *
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(CvcOrderInfoEntity query,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		if(StringUtils.isNotEmpty(query.getAddTime_begin1())) {
			try {
				Date date1 = DateUtils.parseDate(query.getAddTime_begin1(),new String[] {"yyyy-MM-dd HH:mm:ss"}) ;
				query.setAddTimeBegin((int)PhpDateUtils.getTime(date1));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		if(StringUtils.isNotEmpty(query.getAddTime_end2())) {
			try {
				Date date1 = DateUtils.parseDate(query.getAddTime_end2(),new String[] {"yyyy-MM-dd HH:mm:ss"}) ;
				query.setAddTimeEnd((int)PhpDateUtils.getTime(date1));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		if(StringUtils.isNotEmpty(query.getGetTimeStart())) {
			try {
				Date date1 = DateUtils.parseDate(query.getGetTimeStart(),new String[] {"yyyyMMdd"}) ;
				query.setGetTimeStart(String.valueOf(PhpDateUtils.getTime(date1)));
			} catch (ParseException e) {
				query.setGetTimeStart("");
			}
		}

		if(StringUtils.isNotEmpty(query.getGetTimeEnd())) {
			try {
				Date date1 = DateUtils.parseDate(query.getGetTimeEnd(),new String[] {"yyyyMMdd"});
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date1);
				calendar.add(Calendar.DAY_OF_YEAR, 1);
				query.setGetTimeEnd(String.valueOf(PhpDateUtils.getTime(calendar.getTime())));
			} catch (ParseException e) {
				query.setGetTimeEnd("");
			}
		}



		if(query.getExceptionStatus() != null && query.getExceptionStatus() > 0) {
			//查询有异常订单
			query.setExceptionStatusString("1");
		}else if(query.getExceptionStatus() != null && query.getExceptionStatus() == 0){
			//查询无异常订单
			query.setExceptionStatusString("2");
		}

		MiniDaoPage<CvcOrderInfoEntity> list = cvcOrderInfoService.getAll(query, dataGrid.getPage(), dataGrid.getRows());
		if(CollectionUtils.isNotEmpty(list.getResults())) {
			for (CvcOrderInfoEntity entity : list.getResults()) {
				entity.setAddTime(PhpDateUtils.parseDate(Long.parseLong(entity.getAddTime()), "yyyy-MM-dd HH:mm:ss"));
				entity.setGetTime(PhpDateUtils.parseDate(Long.parseLong(entity.getGetTime()), "yyyy-MM-dd HH:mm:ss"));

				String value = (0 == entity.getExceptionStatus())?"无异常":"有异常";
				if(0 != entity.getExceptionStatus()) {
					value += (entity.getIsShow() == 1)?"(未处理)":"(已处理)";
				}

				entity.setExceptionStatusString(value);
			}
		}
		dataGrid.setResults(SystemTools.convertPaginatedList(list));
		dataGrid.setTotal(cvcOrderInfoService.getCount(query, dataGrid.getPage(), dataGrid.getRows()));
		TagUtil.datagrid(response, dataGrid);
	}



	/**
	 * 查看详情
	 *
	 * @return
	 */
	@RequestMapping(params = "toDetail", method = RequestMethod.GET)
	public ModelAndView cvcOrderInfoDetail(@RequestParam(required = true, value = "id") int id, HttpServletResponse response,
			HttpServletRequest request) throws Exception {
		CvcOrderInfoEntity cvcOrderInfoEntity = new CvcOrderInfoEntity();
		cvcOrderInfoEntity.setId(id);
		cvcOrderInfoEntity = cvcOrderInfoService.getOrder(cvcOrderInfoEntity);
		if(cvcOrderInfoEntity == null) {
			throw new XuException("订单查询失败！");
		}
		List<CvcDeliveryOrderEntity> deliveryOrders = cvcOrderInfoService.getDeliveryOrderByOrderId(id);
		List<DeliveryInfoPojo> deliveryInfoPojos = new ArrayList<>();
		for (CvcDeliveryOrderEntity deliveryOrder : deliveryOrders) {
			CvcDeliveryInfoEntity entity = cvcDeliveryInfoService
					.getDeliveryInfosByInvoiceNo(deliveryOrder.getInvoiceNo());
			List<Data> deliveryInfos = new ArrayList<>();
			if (entity != null && StringUtils.isNotEmpty(entity.getData())) {
				deliveryInfos = PHPAndJavaSerialize.unserializePHParray(entity.getData(), Data.class);
			}

			DeliveryInfoPojo pojo = new DeliveryInfoPojo();
			pojo.setInvoiceNo(deliveryOrder.getInvoiceNo());
			pojo.setDeliveryInfos(deliveryInfos);
			deliveryInfoPojos.add(pojo);
		}

		///*异常订单类型*/
//		1); // 疑难
//		2); // 退签
//		3); // 退回
//		4); // 快递单号异常
//		5); // 超时关闭
		boolean is_show_exception = new ArrayList<Integer>() {
			{
				add(1);
				add(2);
				add(3);
			}
		}.contains(cvcOrderInfoEntity.getExceptionStatus());
		//是否展示异常区域
		request.setAttribute("is_show_exception", is_show_exception);
		//订单状态
		request.setAttribute("exception_status",cvcOrderInfoEntity.getExceptionStatus());
		//是否已签收
		request.setAttribute("is_show_shipping_info", 5 == cvcOrderInfoEntity.getOrderStatus()); // 已签收订单
		request.setAttribute("deliveryOrders", deliveryOrders);
		request.setAttribute("deliveryInfoPojos", deliveryInfoPojos);

		//传递订单信息
		request.setAttribute("cvcOrderInfoEntity", cvcOrderInfoEntity);
		 /* 根据订单是否完成检查权限 */
		// getOrderStatus  define('OS_CONFIRMED',              1); // 已确认
		//getShippingStatus define('SS_SHIPPED',1); // 已发货
		//define('SS_RECEIVED',2); // 已收货
		//getPayStatus
		//define('PS_PAYING',                 1); // 付款中
		//define('PS_PAYED',                  2); // 已付款
		if(cvcOrderInfoEntity.getShippingStatus() == null) {
			cvcOrderInfoEntity.setShippingStatus(0);
		}

		if(cvcOrderInfoEntity.getPayStatus() == null) {
			cvcOrderInfoEntity.setPayStatus(0);
		}

	    if ( 1== cvcOrderInfoEntity.getOrderStatus() && (cvcOrderInfoEntity.getShippingStatus() == 1 || cvcOrderInfoEntity.getShippingStatus() == 2 )
	    		&& (cvcOrderInfoEntity.getPayStatus()==2 || cvcOrderInfoEntity.getPayStatus()==1)) {
	    	//检查权限
	    	throw new XuException("订单状态异常！");
	    }
	    //获取商品
	    List<CvcOrderGoodsEntity> cvcOrderGoodsEntities = cvcOrderGoodsService.getAll(id);
		request.setAttribute("goods_list", cvcOrderGoodsEntities);

		//发货情况
		List<CvcDeliveryOrderEntity> deliveryGoods = cvcDeliveryOrderService.getDeliveryCondition(id);
		request.setAttribute("delivery_goods", deliveryGoods);
		if(CollectionUtils.isNotEmpty(deliveryGoods)) {
			CvcShippingEntity cvcShipping = new CvcShippingEntity();
			cvcShipping.setEnabled(1);
			//查询快递公司
			MiniDaoPage<CvcShippingEntity> daoPage = cvcShippingService.getAll(cvcShipping, 1, 20);
			request.setAttribute("shippingEntitys", daoPage.getResults());
		}

		// 撤销商品情况
		List<CvcRevokeGoodsEntity> cvcRevokeGoodsEntities = cvcRevokeGoodsService.getByOrderId(id);
		request.setAttribute("revoke_goods", cvcRevokeGoodsEntities);

		//取得订单操作记录
		List<CvcOrderActionEntity> actionEntities = cvcOrderActionService.getNewByOrderId(id);
		request.setAttribute("action_list", actionEntities);
		return new ModelAndView("com/xuzy/hotel/order/tOrderDetail");
	}

	/**
	 * 更新签收时间
	 *
	 * @return
	 */
	@RequestMapping(params = "updateSigndate", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public AjaxJson updateSigndate(@RequestParam(required = true, value = "orderId") int orderId,@RequestParam(required = true, value = "signdate")String signdate) {
		AjaxJson j = new AjaxJson();
		if(orderId <= 0) {
			j.setSuccess(false);
			j.setMsg("该订单号不存在！");
			return j;
		}

		if(StringUtils.isEmpty(signdate)) {
			j.setSuccess(false);
			j.setMsg("请选择时间！");
			return j;
		}

		try {
			CvcOrderInfoEntity cvcOrderInfo = new CvcOrderInfoEntity();
			cvcOrderInfo.setId(orderId);
			CvcOrderInfoEntity cvcOrderInfo1 = cvcOrderInfoService.getOrder(cvcOrderInfo);
			if(cvcOrderInfo1 != null) {
				//请求伊利接口
				RequestReNewExchangeSignDateJson dateJson = new RequestReNewExchangeSignDateJson();
				dateJson.setOrderID(orderId);
				dateJson.setSignDate(signdate);
				ResponseHead head = ConmentHttp.sendHttp(new TukeRequestBody.Builder().setSequence(2)
						.setParams(dateJson).setServiceCode("CRMIF.ReNewExchangeSignDateJson").builder(), null);
				if(head.getReturn() >= 0) {
					cvcDeliveryOrderService.updateSigndate(orderId, signdate);
				}else {
					j.setSuccess(false);
					j.setMsg("接口调用失败！原因："+head.getReturnInfo());
					return j;
				}
			}else {
				j.setSuccess(false);
				j.setMsg("该订单号不存在！");
				return j;
			}
			j.setMsg("更新成功");
		} catch (Exception e) {
			log.info(e.getMessage());
			j.setSuccess(false);
			j.setMsg("更新失败");
		}
		return j;
	}



	/**
	 * 更新快递订单号
	 *
	 * @return
	 */
	@RequestMapping(params = "updateNu", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public AjaxJson updateNu(@RequestParam(required = true, value = "orderId") int orderId
			,@RequestParam(required = true, value = "invoiceNo")String invoiceNo,@RequestParam(required = true, value = "shippingName")String shippingName) {
		AjaxJson j = new AjaxJson();
		if(orderId <= 0) {
			j.setSuccess(false);
			j.setMsg("该订单号不存在！");
			return j;
		}

		if(StringUtils.isEmpty(shippingName) || StringUtils.isEmpty(invoiceNo)) {
			j.setSuccess(false);
			j.setMsg("请填写快递公司或快递单号！");
			return j;
		}

		try {
			//获取快递公司
			CvcShippingEntity cvcShipping = new CvcShippingEntity();
			cvcShipping.setEnabled(1);
			cvcShipping.setShippingName(shippingName);
			//查询快递公司
			MiniDaoPage<CvcShippingEntity> daoPage = cvcShippingService.getAll(cvcShipping, 1, 1);
			CvcShippingEntity cvcShippingEntity = null;
			if(CollectionUtils.isEmpty(daoPage.getResults())) {
				j.setSuccess(false);
				j.setMsg("该快递公司不存在！");
				return j;
			}else {
				cvcShippingEntity = daoPage.getResults().get(0);
			}
			CvcOrderInfoEntity cvcOrderInfo = new CvcOrderInfoEntity();
			cvcOrderInfo.setId(orderId);
			CvcOrderInfoEntity cvcOrderInfo1 = cvcOrderInfoService.getOrder(cvcOrderInfo);
			if(cvcOrderInfo1 != null) {
				//请求伊利接口  CRMIF.ReNewExchangeEMSJson
				RequestReNewExchangeEMSJson emsJson = new RequestReNewExchangeEMSJson();
				emsJson.setOrderID(orderId);
				emsJson.setEMSCompany(shippingName);
				emsJson.setEMSOdd(invoiceNo);
				ResponseHead head = ConmentHttp.sendHttp(new TukeRequestBody.Builder().setSequence(2)
						.setParams(emsJson).setServiceCode("CRMIF.ReNewExchangeEMSJson").builder(), null);
				if(head.getReturn() >= 0) {
					ConmentHttp.postorder(cvcShippingEntity.getShippingCode(), invoiceNo);
					cvcDeliveryOrderService.updateNu(orderId, cvcShippingEntity.getShippingId(),cvcShippingEntity.getShippingName(),invoiceNo);
					if(cvcOrderInfo1.getExceptionStatus() != null &&
							4 ==cvcOrderInfo1.getExceptionStatus()|| 5 == cvcOrderInfo1.getExceptionStatus()) {
						//更新异常订单
						cvcOrderInfoService.updateHandle(cvcOrderInfo1.getId(),1,(int)PhpDateUtils.getTime(),ResourceUtil.getSessionUser().getUserName());
					}
				}else {
					j.setSuccess(false);
					j.setMsg("接口调用失败！原因："+head.getReturnInfo());
					return j;
				}
			}else {
				j.setSuccess(false);
				j.setMsg("该订单号不存在！");
				return j;
			}
			j.setMsg("更新成功");
		} catch (Exception e) {
			log.info(e.getMessage());
			j.setSuccess(false);
			j.setMsg("更新失败");
		}
		return j;
	}


	/**
	 * 更新快递订单号
	 *
	 * @return
	 */
	@RequestMapping(params = "ship", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public AjaxJson ship(@RequestParam(required = true, value = "orderId") int orderId
			,@RequestParam(required = false, value = "preArrivalDate") String preArrivalDate
			,@RequestParam(required = false, value = "invoiceNo") String invoiceNo
			,@RequestParam(required = false, value = "city") String city
			,@RequestParam(required = false, value = "district") String district
			,@RequestParam(required = false, value = "agency_id") String agency_id
			,@RequestParam(required = false, value = "insure_fee") String insure_fee
			,@RequestParam(required = false, value = "shipping_fee") String shipping_fee
			,@RequestParam(required = false, value = "shippingId") Integer shippingId
			,@RequestParam(required = true, value = "shippingName") String shippingName) {
		AjaxJson j = new AjaxJson();
		if(orderId <= 0) {
			j.setSuccess(false);
			j.setMsg("该订单号不存在！");
			return j;
		}
		CvcOrderInfoEntity cvcOrderInfoEntity = new CvcOrderInfoEntity();
		cvcOrderInfoEntity.setId(orderId);
		cvcOrderInfoEntity = cvcOrderInfoService.getOrder(cvcOrderInfoEntity);
		if(cvcOrderInfoEntity == null) {
			j.setSuccess(false);
			j.setMsg("订单查询失败！");
			return j;
		}

		cvcOrderInfoEntity.setPreArrivalDate(preArrivalDate);

		if(StringUtils.isEmpty(preArrivalDate)) {
			j.setSuccess(false);
			j.setMsg("预计到达时间不能为空！");
			return j;
		}

		if(StringUtils.isEmpty(invoiceNo)) {
			j.setSuccess(false);
			j.setMsg("快递单号不能为空！");
			return j;
		}
		cvcOrderInfoEntity.setInvoiceNo(invoiceNo);

		if (1 == cvcOrderInfoEntity.getOrderStatus()
				&& (cvcOrderInfoEntity.getShippingStatus() == 1 || cvcOrderInfoEntity.getShippingStatus() == 2)
				&& (cvcOrderInfoEntity.getPayStatus() == 2 || cvcOrderInfoEntity.getPayStatus() == 1)) {
			// 检查权限
			j.setSuccess(false);
			j.setMsg("订单状态异常！");
			return j;
		}

		if (5 == cvcOrderInfoEntity.getOrderStatus()) {
			// 检查权限
			j.setSuccess(false);
			j.setMsg("订单已发货！");
			return j;
		}

		try {
			//发货
			j = cvcOrderInfoService.sendOrder(cvcOrderInfoEntity,shippingName,"",invoiceNo,preArrivalDate);
		} catch (Exception e) {
			log.error("发货失败",e);
			j.setSuccess(false);
			j.setMsg("发货失败");
		}
		return j;
	}




	/**
	 * 跳转到发货页面
	 *
	 * @return
	 */
	@RequestMapping(params = "toShip", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView toShip(@RequestParam(required = true, value = "orderId") int orderId,HttpServletRequest request) throws Exception {
		CvcOrderInfoEntity cvcOrderInfoEntity = new CvcOrderInfoEntity();
		cvcOrderInfoEntity.setId(orderId);
		cvcOrderInfoEntity = cvcOrderInfoService.getOrder(cvcOrderInfoEntity);
		if(cvcOrderInfoEntity == null) {
			throw new XuException("订单查询失败！");
		}

		cvcOrderInfoEntity.setInvoiceNo((cvcOrderInfoEntity.getShippingStatus() != null  && cvcOrderInfoEntity.getShippingStatus() == 0 )
				|| (cvcOrderInfoEntity.getShippingStatus() != null && cvcOrderInfoEntity.getShippingStatus() == 0) ? ResourceUtil.searchAllTypesByCode("0","sstatus"):
					cvcOrderInfoEntity.getInvoiceNo());
		 /* 查询：是否保价 */

		 /* 根据订单是否完成检查权限 */
		// getOrderStatus  define('OS_CONFIRMED',              1); // 已确认
		//getShippingStatus define('SS_SHIPPED',1); // 已发货
		//define('SS_RECEIVED',2); // 已收货
		//getPayStatus
		//define('PS_PAYING',                 1); // 付款中
		//define('PS_PAYED',                  2); // 已付款
	    if ( 1== cvcOrderInfoEntity.getOrderStatus() && (cvcOrderInfoEntity.getShippingStatus() == 1 || cvcOrderInfoEntity.getShippingStatus() == 2 )
	    		&& (cvcOrderInfoEntity.getPayStatus()==2 || cvcOrderInfoEntity.getPayStatus()==1)) {
	    	//检查权限
	    	throw new XuException("订单状态异常！");
	    }

	    //获取商品
	    List<CvcOrderGoodsEntity> cvcOrderGoodsEntities = cvcOrderGoodsService.getAll(orderId);
		request.setAttribute("goods_list", cvcOrderGoodsEntities);


		CvcShippingEntity cvcShipping = new CvcShippingEntity();
		cvcShipping.setEnabled(1);
		//查询快递公司
		MiniDaoPage<CvcShippingEntity> daoPage = cvcShippingService.getAll(cvcShipping, 1, 20);
		request.setAttribute("shippingEntitys", daoPage.getResults());

		//传递订单信息
		request.setAttribute("cvcOrderInfoEntity", cvcOrderInfoEntity);
		return new ModelAndView("com/xuzy/hotel/order/tOrderShip");
	}

	/**
	 * 修改状态
	 *
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "orderStatusUpdate", method = RequestMethod.GET)
	@ResponseBody
	public AjaxJson orderStatusUpdate(@RequestParam(required = true, value = "id") int id,
			@RequestParam(required = true, value = "tkOrderStatus") String tkOrderStatus,
			@RequestParam(required = false, value = "backingReason") String backingReason,
			@RequestParam(required = false, value = "returnReason") String returnReason) {
		AjaxJson j = new AjaxJson();
		try {
			if(StringUtils.isEmpty(tkOrderStatus)) {
				j.setSuccess(false);
				j.setMsg("参数异常");
				return j;
			}
			CvcOrderInfoEntity cvcOrderInfoEntity = cvcOrderInfoService.get(id);
			if(cvcOrderInfoEntity == null) {
				j.setSuccess(false);
				j.setMsg("操作订单不存在");
				return j;
			}


			if("offharbour".equals(tkOrderStatus)) {
				//推送至离港
				RequestOFFHarbourExchangeOrderJson  requestBody = new RequestOFFHarbourExchangeOrderJson();
				requestBody.setOrderID(id);
				requestBody.setEMSCompany(cvcOrderInfoEntity.getShippingName());
				requestBody.setEMSOdd(cvcOrderInfoEntity.getInvoiceNo());
				requestBody.setPreArrivalDate(cvcOrderInfoEntity.getPreArrivalDate());
				ResponseHead responseHead = ConmentHttp.sendHttp(new TukeRequestBody.Builder()
						.setSequence(2)
						.setServiceCode("CRMIF.OFFHarbourExchangeOrderJson")
						.setParams(requestBody).builder(), null);
				if(responseHead.getReturn() >= 0) {
					cvcOrderInfoService.updateStatusByOrderId(id, 3);
					j.setMsg("订单离港成功");
				}else {
					j.setSuccess(false);
					j.setMsg("订单离港失败 原因:"+responseHead.getReturnInfo());
				}
			} else if("send".equals(tkOrderStatus)) {
				//推送至配送中
				RequestDeliveryExchangeOrderJson  requestBody = new RequestDeliveryExchangeOrderJson();
				requestBody.setOrderID(id);
				requestBody.setDeliveryingDate(DateFormatUtils.format(Calendar.getInstance(), "yyyy-MM-dd HH:mm:ss"));
				ResponseHead responseHead = ConmentHttp.sendHttp(new TukeRequestBody.Builder()
						.setSequence(2)
						.setServiceCode("CRMIF.DeliveryExchangeOrderJson")
						.setParams(requestBody).builder(), null);
				if(responseHead.getReturn() >= 0) {
					cvcOrderInfoService.updateStatusByOrderId(id, 4);
					j.setMsg("订单配送成功");
				}else {
					j.setSuccess(false);
					j.setMsg("订单配送失败 原因:"+responseHead.getReturnInfo());
				}
			}else if("signin".equals(tkOrderStatus)) {
				CvcDeliveryInfoEntity cvcDeliveryInfoEntity = cvcDeliveryInfoService.getDeliveryInfosByInvoiceNo(cvcOrderInfoEntity.getInvoiceNo());
				if(cvcDeliveryInfoEntity == null) {
					j.setSuccess(false);
					j.setMsg("暂未查询到物流信息");
					return j;
				}
				List<Data> datas = PHPAndJavaSerialize.unserializePHParray(cvcDeliveryInfoEntity.getData(),DelivetyJson.class);
				if(CollectionUtils.isEmpty(datas)) {
					j.setSuccess(false);
					j.setMsg("暂未查询到物流信息");
					return j;
				}
				//推送至签收
				RequestSignInExchangeOrderJson  requestBody = new RequestSignInExchangeOrderJson();
				requestBody.setOrderID(id);
				requestBody.setSignInMan("");
//				requestBody.setSignInMan(datas.get(0).getContext());
				requestBody.setSignInDate(datas.get(0).getFtime() );
				ResponseHead responseHead = ConmentHttp.sendHttp(new TukeRequestBody.Builder()
						.setSequence(2)
						.setServiceCode("CRMIF.SignInExchangeOrderJson")
						.setParams(requestBody).builder(), null);
				if(responseHead.getReturn() >= 0) {
					cvcOrderInfoService.updateStatusByOrderId(id, 5);
					cvcDeliveryOrderService.updateSignDate(datas.get(0).getFtime(),cvcOrderInfoEntity.getInvoiceNo());
					j.setMsg("订单签收成功");
				}else {
					j.setSuccess(false);
					j.setMsg("订单签收失败 原因:"+responseHead.getReturnInfo());
				}
			}else if("return".equals(tkOrderStatus)) {
				RequestReturnedExchangeOrderJson exchangeOrderJson = new RequestReturnedExchangeOrderJson();
				exchangeOrderJson.setOrderID(id);
				exchangeOrderJson.setReturningReason(returnReason);
				ResponseHead head = ConmentHttp.sendHttp(new TukeRequestBody.Builder()
							.setParams(exchangeOrderJson).setSequence(2)
							.setServiceCode("CRMIF.ReturningExchangeOrderJson").builder(), null);
				if(head.getReturn() >= 0) {
					cvcOrderInfoService.updateStatusByOrderId(id, 7);
					exchangeOrderJson.setReturningReason("");
					exchangeOrderJson.setReturnedReason(returnReason);
					head = ConmentHttp.sendHttp(new TukeRequestBody.Builder()
							.setParams(exchangeOrderJson).setSequence(2)
							.setServiceCode("CRMIF.ReturnedExchangeOrderJson").builder(), null);
					if(head.getReturn() >= 0) {
						//退货
						cvcOrderInfoService.updateStatusByOrderId(id, 8);
						j.setMsg("订单退货成功");
					}else {
						j.setSuccess(false);
						j.setMsg("订单退货失败");
					}
				}else {
					j.setSuccess(false);
					j.setMsg("订单退货失败");
				}
			}else if("returnWareHouse".equals(tkOrderStatus)) {
				//申请返仓
				RequestBackingExchangeOrderJson exchangeOrderJson = new RequestBackingExchangeOrderJson();
				exchangeOrderJson.setOrderID(id);
				exchangeOrderJson.setBackingReason(backingReason);
				ResponseHead head = ConmentHttp.sendHttp(new TukeRequestBody.Builder()
							.setParams(exchangeOrderJson).setSequence(2)
							.setServiceCode("CRMIF.BackingExchangeOrderJson").builder(), null);
				if(head.getReturn() >= 0) {
					//申请返仓
					cvcOrderInfoService.updateStatusByOrderId(id, 23);
					if(cvcOrderInfoEntity.getOrderStatus() == 23) {
						//用户反仓完成
						RequestRefuseExchangeOrderJson exchangeOrderJson2 = new RequestRefuseExchangeOrderJson();
						exchangeOrderJson2.setOrderID(id);
						head = ConmentHttp.sendHttp(new TukeRequestBody.Builder()
									.setParams(exchangeOrderJson2).setSequence(2)
									.setServiceCode("CRMIF.BackedExchangeOrderJson").builder(), null);
						if(head.getReturn() >= 0) {
							cvcOrderInfoService.updateStatusByOrderId(id, 24);
							j.setMsg("订单推送返仓完成操作成功");
						}else {
							j.setSuccess(false);
							j.setMsg("订单推送返仓完成操作失败 原因:"+head.getReturnInfo());
						}
					}
				}else {
					j.setSuccess(false);
					j.setMsg("申请返仓失败 原因:"+head.getReturnInfo());
				}
			}else if("signFailure".equals(tkOrderStatus)) {
				//签收失败
				RequestRefuseExchangeOrderJson exchangeOrderJson = new RequestRefuseExchangeOrderJson();
				exchangeOrderJson.setOrderID(id);
				ResponseHead head = ConmentHttp.sendHttp(new TukeRequestBody.Builder()
							.setParams(exchangeOrderJson).setSequence(2)
							.setServiceCode("CRMIF.RefuseExchangeOrderJson").builder(), null);
				if(head.getReturn() >= 0) {
					cvcOrderInfoService.updateStatusByOrderId(id, 6);
					j.setMsg("订单签收失败操作成功");
				}else {
					j.setSuccess(false);
					j.setMsg("订单签收失败 原因:"+head.getReturnInfo());
				}
			}else if("quitOrder".equals(tkOrderStatus)) {
				//用户取消订单
				if(cvcOrderInfoEntity.getOrderStatus() == 2
						|| cvcOrderInfoEntity.getOrderStatus() == 3) {
					cvcOrderInfoService.updateStatusByOrderId(id, 9);
				}else {
					j.setSuccess(false);
					j.setMsg("推送失败:只能取消 配货中与离港状态订单！");
				}
			}else if("getOrderWuliu".equals(tkOrderStatus)) {
				//获取物流信息，并同步
				CvcShippingEntity cvcShipping = new CvcShippingEntity();
				cvcShipping.setEnabled(1);
				cvcShipping.setShippingName(cvcOrderInfoEntity.getShippingName());
				// 查询快递公司
				MiniDaoPage<CvcShippingEntity> daoPage = cvcShippingService.getAll(cvcShipping, 1, 1);
				CvcShippingEntity cvcShippingEntity = null;
				if (CollectionUtils.isEmpty(daoPage.getResults())) {
					j.setSuccess(false);
					j.setMsg("该快递公司不存在！");
					return j;
				} else {
					cvcShippingEntity = daoPage.getResults().get(0);
				}
				String result = ConmentHttp.getOrderWuliuAll(cvcShippingEntity.getShippingCode(),
						cvcOrderInfoEntity.getInvoiceNo(), cvcOrderInfoEntity.getTel());
				log.info("手动获取物流：" + result);
				if (StringUtils.isNotEmpty(result) && result.contains("\"message\":\"ok\"")) {
					ConmentHttp.postMyErrorOrder(result);
				} else {
					j.setSuccess(false);
					j.setMsg("同步失败:未查询到物流信息！");
				}
			}else if("signFailureGo".equals(tkOrderStatus)) {
				//签收失败
				RequestRefuseExchangeOrderJson exchangeOrderJson = new RequestRefuseExchangeOrderJson();
				exchangeOrderJson.setOrderID(id);
				ResponseHead head = ConmentHttp.sendHttp(new TukeRequestBody.Builder()
							.setParams(exchangeOrderJson).setSequence(2)
							.setServiceCode("CRMIF.RefuseExchangeOrderJson").builder(), null);
				if(head.getReturn() >= 0) {
					cvcOrderInfoService.updateStatusByOrderId(id, 6);
					j.setMsg("订单签收失败操作成功");
				}else {
					cvcOrderInfoService.updateStatusByOrderId(id, 6);
					j.setMsg("订单签收失败成功！ 伊利接口失败 原因:"+head.getReturnInfo());
				}
			}else if("offharbourFailureGo".equals(tkOrderStatus)){
				//推送至离港
				RequestOFFHarbourExchangeOrderJson  requestBody = new RequestOFFHarbourExchangeOrderJson();
				requestBody.setOrderID(id);
				requestBody.setEMSCompany(cvcOrderInfoEntity.getShippingName());
				requestBody.setEMSOdd(cvcOrderInfoEntity.getInvoiceNo());
				requestBody.setPreArrivalDate(cvcOrderInfoEntity.getPreArrivalDate());
				ResponseHead responseHead = ConmentHttp.sendHttp(new TukeRequestBody.Builder()
						.setSequence(2)
						.setServiceCode("CRMIF.OFFHarbourExchangeOrderJson")
						.setParams(requestBody).builder(), null);
				if(responseHead.getReturn() >= 0) {
					cvcOrderInfoService.updateStatusByOrderId(id, 3);
					j.setMsg("订单离港成功");
				}else {
					cvcOrderInfoService.updateStatusByOrderId(id, 3);
					j.setMsg("订单离港失败 原因:"+responseHead.getReturnInfo());
				}
			}else if("sendFailureGo".equals(tkOrderStatus)) {
				//推送至配送中
				RequestDeliveryExchangeOrderJson  requestBody = new RequestDeliveryExchangeOrderJson();
				requestBody.setOrderID(id);
				requestBody.setDeliveryingDate(DateFormatUtils.format(Calendar.getInstance(), "yyyy-MM-dd HH:mm:ss"));
				ResponseHead responseHead = ConmentHttp.sendHttp(new TukeRequestBody.Builder()
						.setSequence(2)
						.setServiceCode("CRMIF.DeliveryExchangeOrderJson")
						.setParams(requestBody).builder(), null);
				if(responseHead.getReturn() >= 0) {
					cvcOrderInfoService.updateStatusByOrderId(id, 4);
					j.setMsg("订单配送成功");
				}else {
					cvcOrderInfoService.updateStatusByOrderId(id, 4);
					j.setMsg("订单配送失败 原因:"+responseHead.getReturnInfo());
				}
			}else if("signinFailureGo".equals(tkOrderStatus)) {
				CvcDeliveryInfoEntity cvcDeliveryInfoEntity = cvcDeliveryInfoService.getDeliveryInfosByInvoiceNo(cvcOrderInfoEntity.getInvoiceNo());
				if(cvcDeliveryInfoEntity == null) {
					j.setSuccess(false);
					j.setMsg("暂未查询到物流信息");
					return j;
				}
				List<Data> datas = PHPAndJavaSerialize.unserializePHParray(cvcDeliveryInfoEntity.getData(),DelivetyJson.class);
				if(CollectionUtils.isEmpty(datas)) {
					j.setSuccess(false);
					j.setMsg("暂未查询到物流信息");
					return j;
				}
				//推送至签收
				RequestSignInExchangeOrderJson  requestBody = new RequestSignInExchangeOrderJson();
				requestBody.setOrderID(id);
				requestBody.setSignInMan("");
//				requestBody.setSignInMan(datas.get(0).getContext());
				requestBody.setSignInDate(datas.get(0).getFtime() );
				ResponseHead responseHead = ConmentHttp.sendHttp(new TukeRequestBody.Builder()
						.setSequence(2)
						.setServiceCode("CRMIF.SignInExchangeOrderJson")
						.setParams(requestBody).builder(), null);
				if(responseHead.getReturn() >= 0) {
					cvcOrderInfoService.updateStatusByOrderId(id, 5);
					cvcDeliveryOrderService.updateSignDate(datas.get(0).getFtime(),cvcOrderInfoEntity.getInvoiceNo());
					j.setMsg("订单签收成功");
				}else {
					cvcOrderInfoService.updateStatusByOrderId(id, 5);
					cvcDeliveryOrderService.updateSignDate(datas.get(0).getFtime(),cvcOrderInfoEntity.getInvoiceNo());
					j.setMsg("订单签收失败 原因:"+responseHead.getReturnInfo());
				}
			}
		} catch (Exception e) {
			log.info(e.getMessage());
			j.setSuccess(false);
			j.setMsg("推送失败");
		}
		return j;
	}





	/**
	 * 编辑
	 *
	 * @return
	 */
	@RequestMapping(params = "epUpdate", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public AjaxJson epUpdate(@RequestParam(required = true, value = "id") int id) {
		AjaxJson j = new AjaxJson();
		try {
			cvcOrderInfoService.updateEpStatus(id);
			j.setMsg("处理成功");
		} catch (Exception e) {
			log.info(e.getMessage());
			j.setSuccess(false);
			j.setMsg("处理失败");
		}
		return j;
	}

	/**
	 * 删除
	 *
	 * @return
	 */
	@RequestMapping(params = "doDelete", method = RequestMethod.GET)
	@ResponseBody
	public AjaxJson doDelete(@RequestParam(required = true, value = "id") String id) {
		AjaxJson j = new AjaxJson();
		try {
//			cvcOrderInfoService.doErrorList();
//			final CountDownLatch countDownLatch = new CountDownLatch(100);
//			Runnable Runnable = new Runnable() {
//
//				@Override
//				public void run() {
//					try {
//						countDownLatch.await();
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//
//					int reulst = cvcInventoryTableService.subInventory("qqqq111",1, 0);
//					synchronized (ConmentHttp.size) {
//						if(reulst == 1) {
//							ConmentHttp.size ++;
//							System.out.println("抢到一个商品，已抢："+ConmentHttp.size);
//						}
//					}
//				}
//			};
//			for(int i = 0; i < 100;i++) {
//				Thread thread = new Thread(Runnable);
//				thread.run();
//				countDownLatch.countDown();
//			}

//			j.setMsg("删除成功");
//			while(true) {

//			}


			CvcOrderInfoEntity query = new CvcOrderInfoEntity();
			query.setOrderStatus(3);
			MiniDaoPage<CvcOrderInfoEntity> list = cvcOrderInfoService.getAll(query, 1, 5000);
			for (CvcOrderInfoEntity entity : list.getResults()) {
				CvcShippingEntity cvcShipping = new CvcShippingEntity();
				cvcShipping.setEnabled(1);
				cvcShipping.setShippingName(entity.getShippingName());
				//查询快递公司
				MiniDaoPage<CvcShippingEntity> daoPage = cvcShippingService.getAll(cvcShipping, 1, 1);
				CvcShippingEntity cvcShippingEntity = daoPage.getResults().get(0);
				ConmentHttp.postorder(cvcShippingEntity.getShippingCode(), entity.getInvoiceNo());
			}

			query = new CvcOrderInfoEntity();
			query.setOrderStatus(4);
			list = cvcOrderInfoService.getAll(query, 1, 5000);
			for (CvcOrderInfoEntity entity : list.getResults()) {
				CvcShippingEntity cvcShipping = new CvcShippingEntity();
				cvcShipping.setEnabled(1);
				cvcShipping.setShippingName(entity.getShippingName());
				//查询快递公司
				MiniDaoPage<CvcShippingEntity> daoPage = cvcShippingService.getAll(cvcShipping, 1, 1);
				CvcShippingEntity cvcShippingEntity = daoPage.getResults().get(0);
				ConmentHttp.postorder(cvcShippingEntity.getShippingCode(), entity.getInvoiceNo());
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
			j.setSuccess(false);
			j.setMsg("删除失败");
		}
		return j;
	}

	/**
	 * 同步订单物流轨迹
	 * @param entity
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(params = "togelzGuiji")
	@ResponseBody
	public AjaxJson togelzGuiji(CvcOrderInfoEntity entity,DataGrid dataGrid,ModelMap modelMap) {
		AjaxJson j = new AjaxJson();
		if(StringUtils.isNotEmpty(entity.getAddTime_begin1())) {
			try {
				Date date1 = DateUtils.parseDate(entity.getAddTime_begin1(),new String[] {"yyyy-MM-dd HH:mm:ss"}) ;
				entity.setAddTimeBegin((int)PhpDateUtils.getTime(date1));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		if(StringUtils.isNotEmpty(entity.getAddTime_end2())) {
			try {
				Date date1 = DateUtils.parseDate(entity.getAddTime_end2(),new String[] {"yyyy-MM-dd HH:mm:ss"}) ;
				entity.setAddTimeEnd((int)PhpDateUtils.getTime(date1));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		MiniDaoPage<CvcOrderInfoEntity> entitys = cvcOrderInfoService.getAll(entity,1,100000);
		if(CollectionUtils.isNotEmpty(entitys.getResults())) {
			for (CvcOrderInfoEntity cvcOrderInfoEntity : entitys.getResults()) {
				List<CvcDeliveryOrderEntity> deliveryOrders = cvcOrderInfoService.getDeliveryOrderByOrderId(cvcOrderInfoEntity.getId());
				for (CvcDeliveryOrderEntity deliveryOrder : deliveryOrders) {
					if(deliveryOrder == null || deliveryOrder.getInvoiceNo() == null) {
						continue;
					}
					List<Data> deliveryInfos = new ArrayList<>();
					CvcDeliveryInfoEntity entity1 = cvcDeliveryInfoService
							.getDeliveryInfosByInvoiceNo(deliveryOrder.getInvoiceNo());
					if (entity1 != null && StringUtils.isNotEmpty(entity1.getData())) {
						deliveryInfos = PHPAndJavaSerialize.unserializePHParray(entity1.getData(), Data.class);
					}

					List<CvcYlDeliveryInfoEntity> ylDeliveryInfoEntitys =cvcYlDeliveryInfoService.getList(cvcOrderInfoEntity.getId(),deliveryOrder.getInvoiceNo());
					for(Data data:deliveryInfos) {
						if(ylDeliveryInfoEntitys == null || !ylDeliveryInfoEntitys.contains(new CvcYlDeliveryInfoEntity(cvcOrderInfoEntity.getId(),deliveryOrder.getInvoiceNo(),data.getFtime()))) {
							//未发送过
							RequestExchangeProcessHistoryAddJson request = new RequestExchangeProcessHistoryAddJson();
							request.setOrderID(cvcOrderInfoEntity.getId());
							request.setSwitNumber(PhpDateUtils.getOrderSn());
							request.setDescription(data.getContext());
							request.setProcessTime(data.getFtime());
							ResponseHead responseHead;
							try {
								responseHead = ConmentHttp.sendHttp(new TukeRequestBody.Builder().setSequence(2)
										.setServiceCode("CRMIF.ExchangeProcessHistoryAddJson")
										.setParams(request).builder(), null);
								if(responseHead.getReturn() >= 0) {
									//记录推送成功
									CvcYlDeliveryInfoEntity ylDeliveryInfoEntity = new CvcYlDeliveryInfoEntity();
									ylDeliveryInfoEntity.setOrderId(cvcOrderInfoEntity.getId());
									ylDeliveryInfoEntity.setNumber(deliveryOrder.getInvoiceNo());
									ylDeliveryInfoEntity.setContext(data.getContext());
									ylDeliveryInfoEntity.setFtime(data.getFtime());
									cvcYlDeliveryInfoService.insert(ylDeliveryInfoEntity);
								}
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		}
		return j;
	}


	/**
	 * 批量删除数据
	 *
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "batchDelete", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson batchDelete(@RequestParam(required = true, value = "ids") String[] ids) {
		AjaxJson j = new AjaxJson();
		try {
			cvcOrderInfoService.batchDelete(ids);
			j.setMsg("批量删除成功");
		} catch (Exception e) {
			log.info(e.getMessage());
			j.setSuccess(false);
			j.setMsg("批量删除失败");
		}
		return j;
	}
	
	
	
	/**
	 * 批量同步物流
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "batchTogetherWuliu")
	@ResponseBody
	public AjaxJson batchTogetherWuliu(CvcOrderInfoEntity entity,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		if(StringUtils.isNotEmpty(entity.getAddTime_begin1())) {
			try {
				Date date1 = DateUtils.parseDate(entity.getAddTime_begin1(),new String[] {"yyyy-MM-dd HH:mm:ss"}) ;
				entity.setAddTimeBegin((int)PhpDateUtils.getTime(date1));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		if(StringUtils.isNotEmpty(entity.getAddTime_end2())) {
			try {
				Date date1 = DateUtils.parseDate(entity.getAddTime_end2(),new String[] {"yyyy-MM-dd HH:mm:ss"}) ;
				entity.setAddTimeEnd((int)PhpDateUtils.getTime(date1));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if(StringUtils.isNotEmpty(entity.getGetTimeStart())) {
			try {
				Date date1 = DateUtils.parseDate(entity.getGetTimeStart(),new String[] {"yyyyMMdd"}) ;
				entity.setGetTimeStart(String.valueOf(PhpDateUtils.getTime(date1)));
			} catch (ParseException e) {
				entity.setGetTimeStart("");
			}
		}
		if(StringUtils.isNotEmpty(entity.getGetTimeEnd())) {
			try {
				Date date1 = DateUtils.parseDate(entity.getGetTimeEnd(),new String[] {"yyyyMMdd"});
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date1);
				calendar.add(Calendar.DAY_OF_YEAR, 1);
				entity.setGetTimeEnd(String.valueOf(PhpDateUtils.getTime(calendar.getTime())));
			} catch (ParseException e) {
				entity.setGetTimeEnd("");
			}
		}
		List<CvcOrderInfoEntity> entitys = cvcOrderInfoService.getExcelAll(entity);
		AjaxJson j = new AjaxJson();
		try {
			if (CollectionUtils.isEmpty(entitys)) {
				j.setMsg("无可同步订单");
				j.setSuccess(false);
				return j;
			}

			if (entitys.size() >= 100) {
				j.setMsg("最多同时同步100条，请修改查询条件");
				j.setSuccess(false);
				return j;
			}

			for (CvcOrderInfoEntity cvcOrderInfoEntity : entitys) {
				// 获取物流信息，并同步
				CvcShippingEntity cvcShipping = new CvcShippingEntity();
				cvcShipping.setEnabled(1);
				cvcShipping.setShippingName(cvcOrderInfoEntity.getShippingName());
				// 查询快递公司
				MiniDaoPage<CvcShippingEntity> daoPage = cvcShippingService.getAll(cvcShipping, 1, 1);
				CvcShippingEntity cvcShippingEntity = null;
				if (CollectionUtils.isEmpty(daoPage.getResults())) {
					j.setSuccess(false);
					j.setMsg(cvcOrderInfoEntity.getShippingName() + "快递公司不存在！");
					return j;
				} else {
					cvcShippingEntity = daoPage.getResults().get(0);
				}
				String result = ConmentHttp.getOrderWuliuAll(cvcShippingEntity.getShippingCode(),
						cvcOrderInfoEntity.getInvoiceNo(), cvcOrderInfoEntity.getTel());
				if (StringUtils.isNotEmpty(result) && result.contains("\"message\":\"ok\"")) {
					ConmentHttp.postMyErrorOrder(result);
				}
			}
			j.setMsg("批量同步成功");
		} catch (Exception e) {
			log.info(e.getMessage());
			j.setSuccess(false);
			j.setMsg("批量同步失败");
		}
		return j;
	}
	

}
