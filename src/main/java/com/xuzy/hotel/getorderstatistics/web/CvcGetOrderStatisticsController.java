package com.xuzy.hotel.getorderstatistics.web;

import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.velocity.VelocityContext;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.minidao.pojo.MiniDaoPage;
import org.jeecgframework.p3.core.common.utils.AjaxJson;
import org.jeecgframework.p3.core.page.SystemTools;
import org.jeecgframework.p3.core.util.plugin.ViewVelocity;
import org.jeecgframework.p3.core.web.BaseController;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.reflect.TypeToken;
import com.util.PhpDateUtils;
import com.xuzy.hotel.getorderstatistics.entity.CvcGetOrderStatisticsEntity;
import com.xuzy.hotel.getorderstatistics.service.CvcGetOrderStatisticsService;
import com.xuzy.hotel.order.entity.CvcOrderInfoEntity;
import com.xuzy.hotel.order.service.CvcOrderInfoService;
import com.xuzy.hotel.ylrequest.Config;
import com.xuzy.hotel.ylrequest.ConmentHttp;
import com.xuzy.hotel.ylrequest.ResponseHead;
import com.xuzy.hotel.ylrequest.TukeRequestBody;
import com.xuzy.hotel.ylrequest.module.RequestGetExchangeOrderListWaitDeliveryJson;
import com.xuzy.hotel.ylrequest.module.RequestSetOrdersReadJson;
import com.xuzy.hotel.ylrequest.module.order.ExchangeOrder;

 /**
 * 描述：抓单表
 * @author: www.jeecg.org
 * @since：2018年11月10日 17时34分59秒 星期六 
 * @version:1.0
 */
@Controller
@RequestMapping("/cvcGetOrderStatistics")
public class CvcGetOrderStatisticsController extends BaseController{
  @Autowired
  private CvcGetOrderStatisticsService cvcGetOrderStatisticsService;
  
  @Autowired
  private CvcOrderInfoService cvcOrderInfoService;
  
  /**
	 * 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/xuzy/hotel/getorderstatistics/getOrderStatisticsList");
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
	public void datagrid(CvcGetOrderStatisticsEntity query,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		MiniDaoPage<CvcGetOrderStatisticsEntity> list = cvcGetOrderStatisticsService.getAll(query, dataGrid.getPage(), dataGrid.getRows());
		if(CollectionUtils.isNotEmpty(list.getResults())) {
			for (CvcGetOrderStatisticsEntity entity : list.getResults()) {
				entity.setAddTimeFormat(PhpDateUtils.parseDate(entity.getAddTime(), "yyyy-MM-dd HH:mm:ss"));
			}
		}
		
		dataGrid.setResults(SystemTools.convertPaginatedList(list));
		dataGrid.setTotal(cvcGetOrderStatisticsService.getCount(query, dataGrid.getPage(), dataGrid.getRows()));
		TagUtil.datagrid(response, dataGrid);
	}
	
	
	/**
	 * 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "toGetOrderList")
	public ModelAndView toGetOrderList(HttpServletRequest request) {
		return new ModelAndView("com/xuzy/hotel/getorderstatistics/getOrder");
	}
	
	String result = "[{\"ID\":11996438,\"ClientID\":19615832,\"State\":12,\"StateName\":\"已审核\",\"OrderSource\":5,\"OrderSourceName\":\"积分订单\",\"AcceptRemark\":\"13537384677\",\"TeleNum\":\"013428854087\",\"AcceptDate\":\"2018-12-08T10:27:52.73\",\"ConfirmDate\":\"2018-12-08T13:28:00.803\",\"PreArrivalDate\":\"2018-12-08T10:27:50.88\",\"OFFHardbourDate\":\"1900-01-01T00:00:00\",\"BeginDeliveryDate\":\"1900-01-01T00:00:00\",\"SignInDate\":\"1900-01-01T00:00:00\",\"SignInMan\":\"\",\"Remark\":\"\",\"DeliveryCompany\":\"\",\"DeliverySheetCode\":\"\",\"DeliveryOfficialCity\":54146,\"DeliveryOfficialCityCode\":\"130901\",\"DeliveryOfficialCityName\":\"广东省 东莞市 市辖区 城区街道\",\"DeliveryAddre\":\"广东省东莞市市辖区城区街道 广东省东莞市莞城区步步高小区侨苑11座402 (备用电话：013428854087)\",\"Consignee\":\"丁女土\",\"Deliverer\":16,\"AccountType\":\"金领冠\",\"Items\":[{\"ID\":15980240,\"Product\":\"10070169\",\"BookQuantity\":1,\"SignInQuantity\":1}]},{\"ID\":11998106,\"ClientID\":15388937,\"State\":12,\"StateName\":\"已审核\",\"OrderSource\":5,\"OrderSourceName\":\"积分订单\",\"AcceptRemark\":\"通过伊利微信兑换\",\"TeleNum\":\"15294109303\",\"AcceptDate\":\"2018-12-08T16:13:49.063\",\"ConfirmDate\":\"2018-12-09T14:45:32.23\",\"PreArrivalDate\":\"2018-12-08T16:13:49.343\",\"OFFHardbourDate\":\"1900-01-01T00:00:00\",\"BeginDeliveryDate\":\"1900-01-01T00:00:00\",\"SignInDate\":\"1900-01-01T00:00:00\",\"SignInMan\":\"\",\"Remark\":\"\",\"DeliveryCompany\":\"\",\"DeliverySheetCode\":\"\",\"DeliveryOfficialCity\":2681,\"DeliveryOfficialCityCode\":\"621225\",\"DeliveryOfficialCityName\":\"甘肃省 陇南市 西和县\",\"DeliveryAddre\":\"甘肃省陇南市西和县 石堡镇新尧村 (备用电话：018794990736)\",\"Consignee\":\"赵晓霞\",\"Deliverer\":16,\"AccountType\":\"金领冠\",\"Items\":[{\"ID\":15981908,\"Product\":\"10010423\",\"BookQuantity\":1,\"SignInQuantity\":1}]},{\"ID\":11969600,\"ClientID\":11049541,\"State\":12,\"StateName\":\"已审核\",\"OrderSource\":5,\"OrderSourceName\":\"积分订单\",\"AcceptRemark\":\"\",\"TeleNum\":\"18975398511\",\"AcceptDate\":\"2018-11-30T13:45:42.703\",\"ConfirmDate\":\"2018-12-08T16:54:54.733\",\"PreArrivalDate\":\"2018-11-30T13:45:41.47\",\"OFFHardbourDate\":\"1900-01-01T00:00:00\",\"BeginDeliveryDate\":\"1900-01-01T00:00:00\",\"SignInDate\":\"1900-01-01T00:00:00\",\"SignInMan\":\"\",\"Remark\":\"\",\"DeliveryCompany\":\"\",\"DeliverySheetCode\":\"\",\"DeliveryOfficialCity\":29979,\"DeliveryOfficialCityCode\":\"430221\",\"DeliveryOfficialCityName\":\"湖南省 株洲市 株洲县 渌口镇\",\"DeliveryAddre\":\"湖南省株洲市株洲县渌口镇 梅苑路人和批发部 (备用电话：018975398511)\",\"Consignee\":\"唐星\",\"Deliverer\":16,\"AccountType\":\"金领冠\",\"Items\":[{\"ID\":15953402,\"Product\":\"10010423\",\"BookQuantity\":1,\"SignInQuantity\":1}]},{\"ID\":11998914,\"ClientID\":18734224,\"State\":12,\"StateName\":\"已审核\",\"OrderSource\":5,\"OrderSourceName\":\"积分订单\",\"AcceptRemark\":\"通过伊利微信兑换\",\"TeleNum\":\"18631140984\",\"AcceptDate\":\"2018-12-08T19:12:41.26\",\"ConfirmDate\":\"2018-12-09T14:39:23.313\",\"PreArrivalDate\":\"2018-12-08T19:12:41.7\",\"OFFHardbourDate\":\"1900-01-01T00:00:00\",\"BeginDeliveryDate\":\"1900-01-01T00:00:00\",\"SignInDate\":\"1900-01-01T00:00:00\",\"SignInMan\":\"\",\"Remark\":\"\",\"DeliveryCompany\":\"\",\"DeliverySheetCode\":\"\",\"DeliveryOfficialCity\":1941,\"DeliveryOfficialCityCode\":\"130604\",\"DeliveryOfficialCityName\":\"河北省 保定市 南市区\",\"DeliveryAddre\":\"河北省保定市南市区 先锋小区21号楼一单元301 (备用电话：018631140984)\",\"Consignee\":\"杨天伟\",\"Deliverer\":16,\"AccountType\":\"金领冠\",\"Items\":[{\"ID\":15982716,\"Product\":\"10010423\",\"BookQuantity\":1,\"SignInQuantity\":1}]},{\"ID\":11998472,\"ClientID\":22192863,\"State\":12,\"StateName\":\"已审核\",\"OrderSource\":5,\"OrderSourceName\":\"积分订单\",\"AcceptRemark\":\"\",\"TeleNum\":\"15046045781\",\"AcceptDate\":\"2018-12-08T17:27:18.91\",\"ConfirmDate\":\"2018-12-09T14:34:49.107\",\"PreArrivalDate\":\"2018-12-08T17:27:18.89\",\"OFFHardbourDate\":\"1900-01-01T00:00:00\",\"BeginDeliveryDate\":\"1900-01-01T00:00:00\",\"SignInDate\":\"1900-01-01T00:00:00\",\"SignInMan\":\"\",\"Remark\":\"\",\"DeliveryCompany\":\"\",\"DeliverySheetCode\":\"\",\"DeliveryOfficialCity\":51046,\"DeliveryOfficialCityCode\":\"230111\",\"DeliveryOfficialCityName\":\"黑龙江省 哈尔滨市 呼兰区 呼兰镇\",\"DeliveryAddre\":\"黑龙江省哈尔滨市呼兰区呼兰镇 呼兰区呼兰镇 四百二楼爱特孕婴 (备用电话：015046045781)\",\"Consignee\":\"关晓滢\",\"Deliverer\":16,\"AccountType\":\"金领冠\",\"Items\":[{\"ID\":15982274,\"Product\":\"10010423\",\"BookQuantity\":2,\"SignInQuantity\":2}]},{\"ID\":11998621,\"ClientID\":17682157,\"State\":12,\"StateName\":\"已审核\",\"OrderSource\":5,\"OrderSourceName\":\"积分订单\",\"AcceptRemark\":\"\",\"TeleNum\":\"018887011215\",\"AcceptDate\":\"2018-12-08T18:03:31.707\",\"ConfirmDate\":\"2018-12-09T14:36:32.787\",\"PreArrivalDate\":\"2018-12-08T18:03:31.32\",\"OFFHardbourDate\":\"1900-01-01T00:00:00\",\"BeginDeliveryDate\":\"1900-01-01T00:00:00\",\"SignInDate\":\"1900-01-01T00:00:00\",\"SignInMan\":\"\",\"Remark\":\"\",\"DeliveryCompany\":\"\",\"DeliverySheetCode\":\"\",\"DeliveryOfficialCity\":7601,\"DeliveryOfficialCityCode\":\"532801\",\"DeliveryOfficialCityName\":\"云南省 西双版纳傣族自治州 景洪市 景洪镇\",\"DeliveryAddre\":\"云南省西双版纳傣族自治州景洪市景洪镇 幸福源5幢3单元1403室 (备用电话：018887011215)\",\"Consignee\":\"邓菊\",\"Deliverer\":16,\"AccountType\":\"金领冠\",\"Items\":[{\"ID\":15982423,\"Product\":\"10010423\",\"BookQuantity\":1,\"SignInQuantity\":1}]},{\"ID\":11999957,\"ClientID\":16366644,\"State\":12,\"StateName\":\"已审核\",\"OrderSource\":5,\"OrderSourceName\":\"积分订单\",\"AcceptRemark\":\"商家中心------6502040023于2018-12-08 15:41:13,将状态更新为订单提交\",\"TeleNum\":\"13565451532\",\"AcceptDate\":\"2018-12-08T15:41:13.637\",\"ConfirmDate\":\"2018-12-09T14:31:19.907\",\"PreArrivalDate\":\"1900-01-01T00:00:00\",\"OFFHardbourDate\":\"1900-01-01T00:00:00\",\"BeginDeliveryDate\":\"1900-01-01T00:00:00\",\"SignInDate\":\"1900-01-01T00:00:00\",\"SignInMan\":\"\",\"Remark\":\"\",\"DeliveryCompany\":\"\",\"DeliverySheetCode\":\"\",\"DeliveryOfficialCity\":478,\"DeliveryOfficialCityCode\":\"650204\",\"DeliveryOfficialCityName\":\"新疆维吾尔自治区 克拉玛依市 白碱滩区\",\"DeliveryAddre\":\"新疆省克拉玛依市白碱滩区，中兴路与花园路交叉口东行80米南白碱滩贸易市场内 (备用电话：013900000000)\",\"Consignee\":\"曹海存\",\"Deliverer\":16,\"AccountType\":\"金领冠\",\"Items\":[{\"ID\":15983759,\"Product\":\"1001009\",\"BookQuantity\":1,\"SignInQuantity\":1}]},{\"ID\":12000543,\"ClientID\":15384994,\"State\":12,\"StateName\":\"已审核\",\"OrderSource\":5,\"OrderSourceName\":\"积分订单\",\"AcceptRemark\":\"\",\"TeleNum\":\"018568888629\",\"AcceptDate\":\"2018-12-09T11:04:01.713\",\"ConfirmDate\":\"2018-12-09T14:35:03.597\",\"PreArrivalDate\":\"2018-12-09T11:04:01.52\",\"OFFHardbourDate\":\"1900-01-01T00:00:00\",\"BeginDeliveryDate\":\"1900-01-01T00:00:00\",\"SignInDate\":\"1900-01-01T00:00:00\",\"SignInMan\":\"\",\"Remark\":\"\",\"DeliveryCompany\":\"\",\"DeliverySheetCode\":\"\",\"DeliveryOfficialCity\":36479,\"DeliveryOfficialCityCode\":\"410526\",\"DeliveryOfficialCityName\":\"河南省 安阳市 滑县 八里营乡\",\"DeliveryAddre\":\"河南省安阳市滑县八里营乡 八里营乡 安阳市滑县八里营乡小博士 (备用电话：018568888629)\",\"Consignee\":\"女士\",\"Deliverer\":16,\"AccountType\":\"金领冠\",\"Items\":[{\"ID\":15984345,\"Product\":\"10010423\",\"BookQuantity\":1,\"SignInQuantity\":1}]},{\"ID\":11997378,\"ClientID\":19518217,\"State\":12,\"StateName\":\"已审核\",\"OrderSource\":5,\"OrderSourceName\":\"积分订单\",\"AcceptRemark\":\"通过伊利微信兑换\",\"TeleNum\":\"13523477706\",\"AcceptDate\":\"2018-12-08T13:54:30.49\",\"ConfirmDate\":\"2018-12-09T14:33:40.547\",\"PreArrivalDate\":\"2018-12-08T13:54:30.687\",\"OFFHardbourDate\":\"1900-01-01T00:00:00\",\"BeginDeliveryDate\":\"1900-01-01T00:00:00\",\"SignInDate\":\"1900-01-01T00:00:00\",\"SignInMan\":\"\",\"Remark\":\"\",\"DeliveryCompany\":\"\",\"DeliverySheetCode\":\"\",\"DeliveryOfficialCity\":1019,\"DeliveryOfficialCityCode\":\"410181\",\"DeliveryOfficialCityName\":\"河南省 郑州市 巩义市\",\"DeliveryAddre\":\"河南省郑州市巩义市 站街镇凤和小区 (备用电话：013523477706)\",\"Consignee\":\"张秀佳\",\"Deliverer\":16,\"AccountType\":\"金领冠\",\"Items\":[{\"ID\":15981180,\"Product\":\"10010423\",\"BookQuantity\":1,\"SignInQuantity\":1}]},{\"ID\":11999467,\"ClientID\":13944119,\"State\":12,\"StateName\":\"已审核\",\"OrderSource\":5,\"OrderSourceName\":\"积分订单\",\"AcceptRemark\":\"通过伊利微信兑换\",\"TeleNum\":\"15357710697\",\"AcceptDate\":\"2018-12-08T21:06:27.3\",\"ConfirmDate\":\"2018-12-09T14:38:29.137\",\"PreArrivalDate\":\"2018-12-08T21:06:27.787\",\"OFFHardbourDate\":\"1900-01-01T00:00:00\",\"BeginDeliveryDate\":\"1900-01-01T00:00:00\",\"SignInDate\":\"1900-01-01T00:00:00\",\"SignInMan\":\"\",\"Remark\":\"\",\"DeliveryCompany\":\"\",\"DeliverySheetCode\":\"\",\"DeliveryOfficialCity\":1748,\"DeliveryOfficialCityCode\":\"341421\",\"DeliveryOfficialCityName\":\"安徽省 巢湖市 庐江县\",\"DeliveryAddre\":\"安徽省巢湖市庐江县 黄山路北路一号烟草专卖局 (备用电话：015357710697)\",\"Consignee\":\"章荣根\",\"Deliverer\":16,\"AccountType\":\"金领冠\",\"Items\":[{\"ID\":15983269,\"Product\":\"10010423\",\"BookQuantity\":1,\"SignInQuantity\":1}]},{\"ID\":11996372,\"ClientID\":8709682,\"State\":12,\"StateName\":\"已审核\",\"OrderSource\":5,\"OrderSourceName\":\"积分订单\",\"AcceptRemark\":\"\",\"TeleNum\":\"015965679670\",\"AcceptDate\":\"2018-12-08T10:14:33.633\",\"ConfirmDate\":\"2018-12-08T13:34:15.56\",\"PreArrivalDate\":\"2018-12-08T10:14:31.643\",\"OFFHardbourDate\":\"1900-01-01T00:00:00\",\"BeginDeliveryDate\":\"1900-01-01T00:00:00\",\"SignInDate\":\"1900-01-01T00:00:00\",\"SignInMan\":\"\",\"Remark\":\"\",\"DeliveryCompany\":\"\",\"DeliverySheetCode\":\"\",\"DeliveryOfficialCity\":22019,\"DeliveryOfficialCityCode\":\"371721\",\"DeliveryOfficialCityName\":\"山东省 菏泽市 曹县 韩集镇\",\"DeliveryAddre\":\"山东省菏泽市曹县韩集镇 韩集镇曹楼村295号 (备用电话：015965679670)\",\"Consignee\":\"王山云\",\"Deliverer\":16,\"AccountType\":\"金领冠\",\"Items\":[{\"ID\":15980174,\"Product\":\"10010423\",\"BookQuantity\":1,\"SignInQuantity\":1}]},{\"ID\":11998397,\"ClientID\":21270909,\"State\":12,\"StateName\":\"已审核\",\"OrderSource\":5,\"OrderSourceName\":\"积分订单\",\"AcceptRemark\":\"\",\"TeleNum\":\"15188132855\",\"AcceptDate\":\"2018-12-08T17:10:54.547\",\"ConfirmDate\":\"2018-12-09T14:38:33.967\",\"PreArrivalDate\":\"2018-12-08T17:10:54.187\",\"OFFHardbourDate\":\"1900-01-01T00:00:00\",\"BeginDeliveryDate\":\"1900-01-01T00:00:00\",\"SignInDate\":\"1900-01-01T00:00:00\",\"SignInMan\":\"\",\"Remark\":\"\",\"DeliveryCompany\":\"\",\"DeliverySheetCode\":\"\",\"DeliveryOfficialCity\":6848,\"DeliveryOfficialCityCode\":\"530322\",\"DeliveryOfficialCityName\":\"云南省 曲靖市 陆良县 中枢镇\",\"DeliveryAddre\":\"云南省曲靖市陆良县中枢镇 春光路菜园小区 (备用电话：013150541484)\",\"Consignee\":\"易礼瑞\",\"Deliverer\":16,\"AccountType\":\"金领冠\",\"Items\":[{\"ID\":15982199,\"Product\":\"10010423\",\"BookQuantity\":1,\"SignInQuantity\":1}]},{\"ID\":11970340,\"ClientID\":11597258,\"State\":12,\"StateName\":\"已审核\",\"OrderSource\":5,\"OrderSourceName\":\"积分订单\",\"AcceptRemark\":\"\",\"TeleNum\":\"15292193301\",\"AcceptDate\":\"2018-11-30T18:12:50.063\",\"ConfirmDate\":\"2018-12-08T16:54:54.52\",\"PreArrivalDate\":\"2018-11-30T18:12:50.063\",\"OFFHardbourDate\":\"1900-01-01T00:00:00\",\"BeginDeliveryDate\":\"1900-01-01T00:00:00\",\"SignInDate\":\"1900-01-01T00:00:00\",\"SignInMan\":\"\",\"Remark\":\"\",\"DeliveryCompany\":\"\",\"DeliverySheetCode\":\"\",\"DeliveryOfficialCity\":29979,\"DeliveryOfficialCityCode\":\"430221\",\"DeliveryOfficialCityName\":\"湖南省 株洲市 株洲县 渌口镇\",\"DeliveryAddre\":\"湖南省株洲市株洲县渌口镇 渌口镇梅苑路人和批发部 (备用电话：018274245600)\",\"Consignee\":\"胡芝凡\",\"Deliverer\":16,\"AccountType\":\"金领冠\",\"Items\":[{\"ID\":15954142,\"Product\":\"10010423\",\"BookQuantity\":1,\"SignInQuantity\":1}]},{\"ID\":11993547,\"ClientID\":8233482,\"State\":12,\"StateName\":\"已审核\",\"OrderSource\":5,\"OrderSourceName\":\"积分订单\",\"AcceptRemark\":\"\",\"TeleNum\":\"013657889479\",\"AcceptDate\":\"2018-12-07T15:03:37.673\",\"ConfirmDate\":\"2018-12-08T13:28:39.077\",\"PreArrivalDate\":\"2018-12-07T15:03:36.413\",\"OFFHardbourDate\":\"1900-01-01T00:00:00\",\"BeginDeliveryDate\":\"1900-01-01T00:00:00\",\"SignInDate\":\"1900-01-01T00:00:00\",\"SignInMan\":\"\",\"Remark\":\"\",\"DeliveryCompany\":\"\",\"DeliverySheetCode\":\"\",\"DeliveryOfficialCity\":54397,\"DeliveryOfficialCityCode\":\"450107\",\"DeliveryOfficialCityName\":\"广西壮族自治区 南宁市 西乡塘区 北湖街道\",\"DeliveryAddre\":\"广西壮族自治区南宁市西乡塘区北湖街道 皂角村25-1号 (备用电话：013657889479)\",\"Consignee\":\"韦娟清\",\"Deliverer\":16,\"AccountType\":\"金领冠\",\"Items\":[{\"ID\":15977349,\"Product\":\"10010423\",\"BookQuantity\":1,\"SignInQuantity\":1}]},{\"ID\":11993661,\"ClientID\":22772120,\"State\":12,\"StateName\":\"已审核\",\"OrderSource\":5,\"OrderSourceName\":\"积分订单\",\"AcceptRemark\":\"\",\"TeleNum\":\"15733744981\",\"AcceptDate\":\"2018-12-07T15:23:52.867\",\"ConfirmDate\":\"2018-12-08T13:28:58.193\",\"PreArrivalDate\":\"2018-12-07T15:23:52.837\",\"OFFHardbourDate\":\"1900-01-01T00:00:00\",\"BeginDeliveryDate\":\"1900-01-01T00:00:00\",\"SignInDate\":\"1900-01-01T00:00:00\",\"SignInMan\":\"\",\"Remark\":\"\",\"DeliveryCompany\":\"\",\"DeliverySheetCode\":\"\",\"DeliveryOfficialCity\":539,\"DeliveryOfficialCityCode\":\"130981\",\"DeliveryOfficialCityName\":\"河北省 沧州市 泊头市\",\"DeliveryAddre\":\"河北省沧州市泊头市 上河城a区8号楼三单308 (备用电话：015733744981)\",\"Consignee\":\"肖杰\",\"Deliverer\":16,\"AccountType\":\"金领冠\",\"Items\":[{\"ID\":15977463,\"Product\":\"10010423\",\"BookQuantity\":1,\"SignInQuantity\":1}]},{\"ID\":11993797,\"ClientID\":18854026,\"State\":12,\"StateName\":\"已审核\",\"OrderSource\":5,\"OrderSourceName\":\"积分订单\",\"AcceptRemark\":\"通过伊利微信兑换\",\"TeleNum\":\"18837299799\",\"AcceptDate\":\"2018-12-07T15:59:14.777\",\"ConfirmDate\":\"2018-12-08T13:30:04.933\",\"PreArrivalDate\":\"2018-12-07T15:59:14.733\",\"OFFHardbourDate\":\"1900-01-01T00:00:00\",\"BeginDeliveryDate\":\"1900-01-01T00:00:00\",\"SignInDate\":\"1900-01-01T00:00:00\",\"SignInMan\":\"\",\"Remark\":\"\",\"DeliveryCompany\":\"\",\"DeliverySheetCode\":\"\",\"DeliveryOfficialCity\":1701,\"DeliveryOfficialCityCode\":\"410506\",\"DeliveryOfficialCityName\":\"河南省 安阳市 龙安区\",\"DeliveryAddre\":\"河南省安阳市龙安区 文源名居西区快递柜 (备用电话：018837299799)\",\"Consignee\":\"李姿\",\"Deliverer\":16,\"AccountType\":\"金领冠\",\"Items\":[{\"ID\":15977599,\"Product\":\"10010423\",\"BookQuantity\":1,\"SignInQuantity\":1}]},{\"ID\":11996712,\"ClientID\":16756299,\"State\":12,\"StateName\":\"已审核\",\"OrderSource\":5,\"OrderSourceName\":\"积分订单\",\"AcceptRemark\":\"通过伊利微信兑换\",\"TeleNum\":\"13884210283\",\"AcceptDate\":\"2018-12-08T11:19:14.44\",\"ConfirmDate\":\"2018-12-08T13:27:15.947\",\"PreArrivalDate\":\"2018-12-08T11:19:14.713\",\"OFFHardbourDate\":\"1900-01-01T00:00:00\",\"BeginDeliveryDate\":\"1900-01-01T00:00:00\",\"SignInDate\":\"1900-01-01T00:00:00\",\"SignInMan\":\"\",\"Remark\":\"\",\"DeliveryCompany\":\"\",\"DeliverySheetCode\":\"\",\"DeliveryOfficialCity\":1461,\"DeliveryOfficialCityCode\":\"620421\",\"DeliveryOfficialCityName\":\"甘肃省 白银市 靖远县\",\"DeliveryAddre\":\"甘肃省白银市靖远县 北城嘉靖苑2号楼 (备用电话：013884210283)\",\"Consignee\":\"杜卫婷\",\"Deliverer\":16,\"AccountType\":\"金领冠\",\"Items\":[{\"ID\":15980514,\"Product\":\"10010423\",\"BookQuantity\":1,\"SignInQuantity\":1}]},{\"ID\":11998584,\"ClientID\":12525117,\"State\":12,\"StateName\":\"已审核\",\"OrderSource\":5,\"OrderSourceName\":\"积分订单\",\"AcceptRemark\":\"\",\"TeleNum\":\"13526159691\",\"AcceptDate\":\"2018-12-08T17:53:32.127\",\"ConfirmDate\":\"2018-12-09T14:31:15.177\",\"PreArrivalDate\":\"2018-12-08T17:53:32.493\",\"OFFHardbourDate\":\"1900-01-01T00:00:00\",\"BeginDeliveryDate\":\"1900-01-01T00:00:00\",\"SignInDate\":\"1900-01-01T00:00:00\",\"SignInMan\":\"\",\"Remark\":\"\",\"DeliveryCompany\":\"\",\"DeliverySheetCode\":\"\",\"DeliveryOfficialCity\":450,\"DeliveryOfficialCityCode\":\"410522\",\"DeliveryOfficialCityName\":\"河南省 安阳市 安阳县\",\"DeliveryAddre\":\"河南省安阳市安阳县曲沟镇安车村 (备用电话：017630582893)\",\"Consignee\":\"史艳花\",\"Deliverer\":16,\"AccountType\":\"金领冠\",\"Items\":[{\"ID\":15982386,\"Product\":\"10010423\",\"BookQuantity\":1,\"SignInQuantity\":1}]},{\"ID\":11993814,\"ClientID\":13724292,\"State\":12,\"StateName\":\"已审核\",\"OrderSource\":5,\"OrderSourceName\":\"积分订单\",\"AcceptRemark\":\"\",\"TeleNum\":\"15274939109\",\"AcceptDate\":\"2018-12-07T16:11:25.61\",\"ConfirmDate\":\"2018-12-08T13:32:08.023\",\"PreArrivalDate\":\"2018-12-07T16:11:25.557\",\"OFFHardbourDate\":\"1900-01-01T00:00:00\",\"BeginDeliveryDate\":\"1900-01-01T00:00:00\",\"SignInDate\":\"1900-01-01T00:00:00\",\"SignInMan\":\"\",\"Remark\":\"\",\"DeliveryCompany\":\"\",\"DeliverySheetCode\":\"\",\"DeliveryOfficialCity\":596,\"DeliveryOfficialCityCode\":\"430121\",\"DeliveryOfficialCityName\":\"湖南省 长沙市 长沙县\",\"DeliveryAddre\":\"湖南省长沙市长沙县 泉塘东四路旭辉华庭6栋 (备用电话：015274939109)\",\"Consignee\":\"齐秀香\",\"Deliverer\":16,\"AccountType\":\"金领冠\",\"Items\":[{\"ID\":15977616,\"Product\":\"10010423\",\"BookQuantity\":1,\"SignInQuantity\":1}]},{\"ID\":11996488,\"ClientID\":20399170,\"State\":12,\"StateName\":\"已审核\",\"OrderSource\":5,\"OrderSourceName\":\"积分订单\",\"AcceptRemark\":\"\",\"TeleNum\":\"013139247835\",\"AcceptDate\":\"2018-12-08T10:38:58.103\",\"ConfirmDate\":\"2018-12-08T13:27:33.08\",\"PreArrivalDate\":\"2018-12-08T10:38:56.377\",\"OFFHardbourDate\":\"1900-01-01T00:00:00\",\"BeginDeliveryDate\":\"1900-01-01T00:00:00\",\"SignInDate\":\"1900-01-01T00:00:00\",\"SignInMan\":\"\",\"Remark\":\"\",\"DeliveryCompany\":\"\",\"DeliverySheetCode\":\"\",\"DeliveryOfficialCity\":45058,\"DeliveryOfficialCityCode\":\"620123\",\"DeliveryOfficialCityName\":\"甘肃省 兰州市 榆中县 城关镇\",\"DeliveryAddre\":\"甘肃省兰州市榆中县城关镇 贝婴美南街店 (备用电话：013139247835)\",\"Consignee\":\"王婷\",\"Deliverer\":16,\"AccountType\":\"金领冠\",\"Items\":[{\"ID\":15980290,\"Product\":\"10010423\",\"BookQuantity\":1,\"SignInQuantity\":1}]},{\"ID\":11995544,\"ClientID\":18843921,\"State\":12,\"StateName\":\"已审核\",\"OrderSource\":5,\"OrderSourceName\":\"积分订单\",\"AcceptRemark\":\"通过伊利微信兑换\",\"TeleNum\":\"18068503317\",\"AcceptDate\":\"2018-12-07T21:51:56.393\",\"ConfirmDate\":\"2018-12-08T13:32:16.887\",\"PreArrivalDate\":\"2018-12-07T21:51:56.383\",\"OFFHardbourDate\":\"1900-01-01T00:00:00\",\"BeginDeliveryDate\":\"1900-01-01T00:00:00\",\"SignInDate\":\"1900-01-01T00:00:00\",\"SignInMan\":\"\",\"Remark\":\"\",\"DeliveryCompany\":\"\",\"DeliverySheetCode\":\"\",\"DeliveryOfficialCity\":2648,\"DeliveryOfficialCityCode\":\"320412\",\"DeliveryOfficialCityName\":\"江苏省 常州市 武进区\",\"DeliveryAddre\":\"江苏省常州市武进区 礼嘉镇毛家村委月家湾14号 (备用电话：018068503317)\",\"Consignee\":\"陆晓青\",\"Deliverer\":16,\"AccountType\":\"金领冠\",\"Items\":[{\"ID\":15979346,\"Product\":\"10010423\",\"BookQuantity\":1,\"SignInQuantity\":1}]},{\"ID\":11995937,\"ClientID\":16366644,\"State\":12,\"StateName\":\"已审核\",\"OrderSource\":5,\"OrderSourceName\":\"积分订单\",\"AcceptRemark\":\"商家中心------4103040002于2018-12-07 11:10:29,将状态更新为订单提交\",\"TeleNum\":\"13015560386\",\"AcceptDate\":\"2018-12-07T11:10:28.933\",\"ConfirmDate\":\"2018-12-08T13:25:41.383\",\"PreArrivalDate\":\"1900-01-01T00:00:00\",\"OFFHardbourDate\":\"1900-01-01T00:00:00\",\"BeginDeliveryDate\":\"1900-01-01T00:00:00\",\"SignInDate\":\"1900-01-01T00:00:00\",\"SignInMan\":\"\",\"Remark\":\"\",\"DeliveryCompany\":\"\",\"DeliverySheetCode\":\"\",\"DeliveryOfficialCity\":573,\"DeliveryOfficialCityCode\":\"410304\",\"DeliveryOfficialCityName\":\"河南省 洛阳市 瀍河回族区\",\"DeliveryAddre\":\"河南省洛阳市瀍河区中州东路122号 (备用电话：013900000000)\",\"Consignee\":\"孙晓丽\",\"Deliverer\":16,\"AccountType\":\"金领冠\",\"Items\":[{\"ID\":15979739,\"Product\":\"1001006\",\"BookQuantity\":1,\"SignInQuantity\":1}]},{\"ID\":11993165,\"ClientID\":18713086,\"State\":12,\"StateName\":\"已审核\",\"OrderSource\":5,\"OrderSourceName\":\"积分订单\",\"AcceptRemark\":\"通过伊利微信兑换\",\"TeleNum\":\"18592067658\",\"AcceptDate\":\"2018-12-07T13:40:12.693\",\"ConfirmDate\":\"2018-12-08T13:27:49.04\",\"PreArrivalDate\":\"2018-12-07T13:40:12.693\",\"OFFHardbourDate\":\"1900-01-01T00:00:00\",\"BeginDeliveryDate\":\"1900-01-01T00:00:00\",\"SignInDate\":\"1900-01-01T00:00:00\",\"SignInMan\":\"\",\"Remark\":\"\",\"DeliveryCompany\":\"\",\"DeliverySheetCode\":\"\",\"DeliveryOfficialCity\":1680,\"DeliveryOfficialCityCode\":\"620822\",\"DeliveryOfficialCityName\":\"甘肃省 平凉市 灵台县\",\"DeliveryAddre\":\"甘肃省平凉市灵台县 邵寨镇邵寨中心小学大门向东100米5楼 (备用电话：018592067658)\",\"Consignee\":\"杨静\",\"Deliverer\":16,\"AccountType\":\"金领冠\",\"Items\":[{\"ID\":15976967,\"Product\":\"10010423\",\"BookQuantity\":1,\"SignInQuantity\":1}]},{\"ID\":11996458,\"ClientID\":20785221,\"State\":12,\"StateName\":\"已审核\",\"OrderSource\":5,\"OrderSourceName\":\"积分订单\",\"AcceptRemark\":\"\",\"TeleNum\":\"13598113837\",\"AcceptDate\":\"2018-12-08T10:31:12.087\",\"ConfirmDate\":\"2018-12-08T13:28:23.863\",\"PreArrivalDate\":\"2018-12-08T10:31:12.133\",\"OFFHardbourDate\":\"1900-01-01T00:00:00\",\"BeginDeliveryDate\":\"1900-01-01T00:00:00\",\"SignInDate\":\"1900-01-01T00:00:00\",\"SignInMan\":\"\",\"Remark\":\"\",\"DeliveryCompany\":\"\",\"DeliverySheetCode\":\"\",\"DeliveryOfficialCity\":43542,\"DeliveryOfficialCityCode\":\"440306\",\"DeliveryOfficialCityName\":\"广东省 深圳市 宝安区 公明镇\",\"DeliveryAddre\":\"广东省深圳市宝安区公明镇 广东省深圳市公明镇光明新区新围第三工业区六排二栋 (备用电话：013598113837)\",\"Consignee\":\"栗先生\",\"Deliverer\":16,\"AccountType\":\"金领冠\",\"Items\":[{\"ID\":15980260,\"Product\":\"10010423\",\"BookQuantity\":1,\"SignInQuantity\":1}]},{\"ID\":11975790,\"ClientID\":19165209,\"State\":12,\"StateName\":\"已审核\",\"OrderSource\":5,\"OrderSourceName\":\"积分订单\",\"AcceptRemark\":\"通过伊利微信兑换\",\"TeleNum\":\"15287072593\",\"AcceptDate\":\"2018-12-02T15:43:09.513\",\"ConfirmDate\":\"2018-12-09T08:31:02.92\",\"PreArrivalDate\":\"2018-12-02T15:43:09.437\",\"OFFHardbourDate\":\"1900-01-01T00:00:00\",\"BeginDeliveryDate\":\"1900-01-01T00:00:00\",\"SignInDate\":\"1900-01-01T00:00:00\",\"SignInMan\":\"\",\"Remark\":\"\",\"DeliveryCompany\":\"\",\"DeliverySheetCode\":\"\",\"DeliveryOfficialCity\":3192,\"DeliveryOfficialCityCode\":\"530627\",\"DeliveryOfficialCityName\":\"云南省 昭通市 镇雄县\",\"DeliveryAddre\":\"云南省昭通市镇雄县 以勒镇以勒村三合社51号 (备用电话：015287072593)\",\"Consignee\":\"汪宇燕\",\"Deliverer\":16,\"AccountType\":\"金领冠\",\"Items\":[{\"ID\":15959592,\"Product\":\"10010423\",\"BookQuantity\":1,\"SignInQuantity\":1}]},{\"ID\":11993945,\"ClientID\":16537087,\"State\":12,\"StateName\":\"已审核\",\"OrderSource\":5,\"OrderSourceName\":\"积分订单\",\"AcceptRemark\":\"通过伊利微信兑换\",\"TeleNum\":\"15166222288\",\"AcceptDate\":\"2018-12-07T17:07:11.36\",\"ConfirmDate\":\"2018-12-08T13:34:14.55\",\"PreArrivalDate\":\"2018-12-07T17:07:11.373\",\"OFFHardbourDate\":\"1900-01-01T00:00:00\",\"BeginDeliveryDate\":\"1900-01-01T00:00:00\",\"SignInDate\":\"1900-01-01T00:00:00\",\"SignInMan\":\"\",\"Remark\":\"\",\"DeliveryCompany\":\"\",\"DeliverySheetCode\":\"\",\"DeliveryOfficialCity\":1520,\"DeliveryOfficialCityCode\":\"370521\",\"DeliveryOfficialCityName\":\"山东省 东营市 垦利县\",\"DeliveryAddre\":\"山东省东营市垦利县 康力花园20楼 (备用电话：015166222288)\",\"Consignee\":\"武福建\",\"Deliverer\":16,\"AccountType\":\"金领冠\",\"Items\":[{\"ID\":15977747,\"Product\":\"10010423\",\"BookQuantity\":1,\"SignInQuantity\":1}]},{\"ID\":11995449,\"ClientID\":152505,\"State\":12,\"StateName\":\"已审核\",\"OrderSource\":5,\"OrderSourceName\":\"积分订单\",\"AcceptRemark\":\"通过伊利微信兑换\",\"TeleNum\":\"13273196075\",\"AcceptDate\":\"2018-12-07T21:27:45.313\",\"ConfirmDate\":\"2018-12-08T13:29:30.96\",\"PreArrivalDate\":\"2018-12-07T21:27:45.33\",\"OFFHardbourDate\":\"1900-01-01T00:00:00\",\"BeginDeliveryDate\":\"1900-01-01T00:00:00\",\"SignInDate\":\"1900-01-01T00:00:00\",\"SignInMan\":\"\",\"Remark\":\"\",\"DeliveryCompany\":\"\",\"DeliverySheetCode\":\"\",\"DeliveryOfficialCity\":2098,\"DeliveryOfficialCityCode\":\"130104\",\"DeliveryOfficialCityName\":\"河北省 石家庄市 桥西区\",\"DeliveryAddre\":\"河北省石家庄市桥西区 槐安西路266号天水丽城1-3-1802 (备用电话：017732802817)\",\"Consignee\":\"王芳\",\"Deliverer\":16,\"AccountType\":\"金领冠\",\"Items\":[{\"ID\":15979251,\"Product\":\"10010423\",\"BookQuantity\":1,\"SignInQuantity\":1}]},{\"ID\":11997121,\"ClientID\":22049527,\"State\":12,\"StateName\":\"已审核\",\"OrderSource\":5,\"OrderSourceName\":\"积分订单\",\"AcceptRemark\":\"通过伊利微信兑换\",\"TeleNum\":\"18767757552\",\"AcceptDate\":\"2018-12-08T12:53:16.42\",\"ConfirmDate\":\"2018-12-08T13:38:57.027\",\"PreArrivalDate\":\"2018-12-08T12:53:16.683\",\"OFFHardbourDate\":\"1900-01-01T00:00:00\",\"BeginDeliveryDate\":\"1900-01-01T00:00:00\",\"SignInDate\":\"1900-01-01T00:00:00\",\"SignInMan\":\"\",\"Remark\":\"\",\"DeliveryCompany\":\"\",\"DeliverySheetCode\":\"\",\"DeliveryOfficialCity\":556,\"DeliveryOfficialCityCode\":\"330327\",\"DeliveryOfficialCityName\":\"浙江省 温州市 苍南县\",\"DeliveryAddre\":\"浙江省温州市苍南县 灵溪镇百丈村消防局旁三箭工地门卫室 (备用电话：018767757552)\",\"Consignee\":\"章玲\",\"Deliverer\":16,\"AccountType\":\"金领冠\",\"Items\":[{\"ID\":15980923,\"Product\":\"10010423\",\"BookQuantity\":1,\"SignInQuantity\":1}]},{\"ID\":11992154,\"ClientID\":22208728,\"State\":12,\"StateName\":\"已审核\",\"OrderSource\":5,\"OrderSourceName\":\"积分订单\",\"AcceptRemark\":\"修改送货地址信息\",\"TeleNum\":\"15844413262\",\"AcceptDate\":\"2018-12-07T09:45:45.63\",\"ConfirmDate\":\"2018-12-08T10:06:19.25\",\"PreArrivalDate\":\"2018-12-07T09:45:45.597\",\"OFFHardbourDate\":\"1900-01-01T00:00:00\",\"BeginDeliveryDate\":\"1900-01-01T00:00:00\",\"SignInDate\":\"1900-01-01T00:00:00\",\"SignInMan\":\"\",\"Remark\":\"\",\"DeliveryCompany\":\"\",\"DeliverySheetCode\":\"\",\"DeliveryOfficialCity\":1016,\"DeliveryOfficialCityCode\":\"220381\",\"DeliveryOfficialCityName\":\"吉林省 四平市 公主岭市\",\"DeliveryAddre\":\"吉林省四平市公主岭市 范家屯镇融城小区3号楼3单元406 (备用电话：015844413262)\",\"Consignee\":\"李贺\",\"Deliverer\":16,\"AccountType\":\"金领冠\",\"Items\":[{\"ID\":15975956,\"Product\":\"10010423\",\"BookQuantity\":1,\"SignInQuantity\":1}]},{\"ID\":11997284,\"ClientID\":16810115,\"State\":12,\"StateName\":\"已审核\",\"OrderSource\":5,\"OrderSourceName\":\"积分订单\",\"AcceptRemark\":\"\",\"TeleNum\":\"15399495181\",\"AcceptDate\":\"2018-12-08T13:33:42.99\",\"ConfirmDate\":\"2018-12-09T14:45:11.24\",\"PreArrivalDate\":\"2018-12-08T13:33:42.967\",\"OFFHardbourDate\":\"1900-01-01T00:00:00\",\"BeginDeliveryDate\":\"1900-01-01T00:00:00\",\"SignInDate\":\"1900-01-01T00:00:00\",\"SignInMan\":\"\",\"Remark\":\"\",\"DeliveryCompany\":\"\",\"DeliverySheetCode\":\"\",\"DeliveryOfficialCity\":16283,\"DeliveryOfficialCityCode\":\"610431\",\"DeliveryOfficialCityName\":\"陕西省 咸阳市 武功县 长宁镇\",\"DeliveryAddre\":\"陕西省咸阳市武功县长宁镇街道120号 (备用电话：015399495181)\",\"Consignee\":\"王丽\",\"Deliverer\":16,\"AccountType\":\"金领冠\",\"Items\":[{\"ID\":15981086,\"Product\":\"10010423\",\"BookQuantity\":1,\"SignInQuantity\":1}]},{\"ID\":11994918,\"ClientID\":20091762,\"State\":12,\"StateName\":\"已审核\",\"OrderSource\":5,\"OrderSourceName\":\"积分订单\",\"AcceptRemark\":\"通过伊利微信兑换\",\"TeleNum\":\"15136319882\",\"AcceptDate\":\"2018-12-07T20:12:59.473\",\"ConfirmDate\":\"2018-12-08T13:30:17.99\",\"PreArrivalDate\":\"2018-12-07T20:12:59.527\",\"OFFHardbourDate\":\"1900-01-01T00:00:00\",\"BeginDeliveryDate\":\"1900-01-01T00:00:00\",\"SignInDate\":\"1900-01-01T00:00:00\",\"SignInMan\":\"\",\"Remark\":\"\",\"DeliveryCompany\":\"\",\"DeliverySheetCode\":\"\",\"DeliveryOfficialCity\":2922,\"DeliveryOfficialCityCode\":\"410381\",\"DeliveryOfficialCityName\":\"河南省 洛阳市 偃师市\",\"DeliveryAddre\":\"河南省洛阳市偃师市顾县镇营道口（犇牛蛋糕店） (备用电话：015136319882)\",\"Consignee\":\"苏婷婷\",\"Deliverer\":16,\"AccountType\":\"金领冠\",\"Items\":[{\"ID\":15978720,\"Product\":\"10010423\",\"BookQuantity\":1,\"SignInQuantity\":1}]},{\"ID\":11996656,\"ClientID\":20667610,\"State\":12,\"StateName\":\"已审核\",\"OrderSource\":5,\"OrderSourceName\":\"积分订单\",\"AcceptRemark\":\"通过伊利WAP兑换\",\"TeleNum\":\"13854449842\",\"AcceptDate\":\"2018-12-08T11:08:39.017\",\"ConfirmDate\":\"2018-12-08T13:34:56.417\",\"PreArrivalDate\":\"2018-12-08T11:08:39.33\",\"OFFHardbourDate\":\"1900-01-01T00:00:00\",\"BeginDeliveryDate\":\"1900-01-01T00:00:00\",\"SignInDate\":\"1900-01-01T00:00:00\",\"SignInMan\":\"\",\"Remark\":\"\",\"DeliveryCompany\":\"\",\"DeliverySheetCode\":\"\",\"DeliveryOfficialCity\":3233,\"DeliveryOfficialCityCode\":\"370782\",\"DeliveryOfficialCityName\":\"山东省 潍坊市 诸城市\",\"DeliveryAddre\":\"山东省潍坊市诸城市 昌城镇中疃子村福满家超市 (备用电话：013854449842)\",\"Consignee\":\"陈永芳\",\"Deliverer\":16,\"AccountType\":\"金领冠\",\"Items\":[{\"ID\":15980458,\"Product\":\"10010423\",\"BookQuantity\":1,\"SignInQuantity\":1}]},{\"ID\":11995995,\"ClientID\":14369554,\"State\":12,\"StateName\":\"已审核\",\"OrderSource\":5,\"OrderSourceName\":\"积分订单\",\"AcceptRemark\":\"通过伊利微信兑换\",\"TeleNum\":\"18855455778\",\"AcceptDate\":\"2018-12-08T03:43:23.787\",\"ConfirmDate\":\"2018-12-08T13:26:42.907\",\"PreArrivalDate\":\"2018-12-08T03:43:23.787\",\"OFFHardbourDate\":\"1900-01-01T00:00:00\",\"BeginDeliveryDate\":\"1900-01-01T00:00:00\",\"SignInDate\":\"1900-01-01T00:00:00\",\"SignInMan\":\"\",\"Remark\":\"\",\"DeliveryCompany\":\"\",\"DeliverySheetCode\":\"\",\"DeliveryOfficialCity\":1982,\"DeliveryOfficialCityCode\":\"340406\",\"DeliveryOfficialCityName\":\"安徽省 淮南市 潘集区\",\"DeliveryAddre\":\"安徽省淮南市潘集区 平圩镇新淮村常拐西片 (备用电话：018855455778)\",\"Consignee\":\"段祥祥\",\"Deliverer\":16,\"AccountType\":\"金领冠\",\"Items\":[{\"ID\":15979797,\"Product\":\"10010423\",\"BookQuantity\":1,\"SignInQuantity\":1}]},{\"ID\":11996638,\"ClientID\":20290017,\"State\":12,\"StateName\":\"已审核\",\"OrderSource\":5,\"OrderSourceName\":\"积分订单\",\"AcceptRemark\":\"兑换礼品\",\"TeleNum\":\"015848911068\",\"AcceptDate\":\"2018-12-08T11:06:07.683\",\"ConfirmDate\":\"2018-12-08T13:33:35.327\",\"PreArrivalDate\":\"2018-12-08T11:06:07.933\",\"OFFHardbourDate\":\"1900-01-01T00:00:00\",\"BeginDeliveryDate\":\"1900-01-01T00:00:00\",\"SignInDate\":\"1900-01-01T00:00:00\",\"SignInMan\":\"\",\"Remark\":\"\",\"DeliveryCompany\":\"\",\"DeliverySheetCode\":\"\",\"DeliveryOfficialCity\":22947,\"DeliveryOfficialCityCode\":\"150121\",\"DeliveryOfficialCityName\":\"内蒙古自治区 呼和浩特市 土默特左旗 沙尔沁乡\",\"DeliveryAddre\":\"内蒙古自治区呼和浩特市土默特左旗沙尔沁乡 内蒙古呼和浩特土默特左旗沙尔沁镇沙尔沁村圆通 (备用电话：015848911068)\",\"Consignee\":\"吕花平\",\"Deliverer\":16,\"AccountType\":\"金领冠\",\"Items\":[{\"ID\":15980440,\"Product\":\"10010423\",\"BookQuantity\":1,\"SignInQuantity\":1}]},{\"ID\":11997297,\"ClientID\":22161890,\"State\":12,\"StateName\":\"已审核\",\"OrderSource\":5,\"OrderSourceName\":\"积分订单\",\"AcceptRemark\":\"\",\"TeleNum\":\"18790656024\",\"AcceptDate\":\"2018-12-08T13:36:08.367\",\"ConfirmDate\":\"2018-12-09T14:33:09.847\",\"PreArrivalDate\":\"2018-12-08T13:36:08.343\",\"OFFHardbourDate\":\"1900-01-01T00:00:00\",\"BeginDeliveryDate\":\"1900-01-01T00:00:00\",\"SignInDate\":\"1900-01-01T00:00:00\",\"SignInMan\":\"\",\"Remark\":\"\",\"DeliveryCompany\":\"\",\"DeliverySheetCode\":\"\",\"DeliveryOfficialCity\":36649,\"DeliveryOfficialCityCode\":\"410727\",\"DeliveryOfficialCityName\":\"河南省 新乡市 封丘县 黄陵镇\",\"DeliveryAddre\":\"河南省新乡市封丘县黄陵镇 黄陵镇黄陵村 (备用电话：018624719166)\",\"Consignee\":\"李然\",\"Deliverer\":16,\"AccountType\":\"金领冠\",\"Items\":[{\"ID\":15981099,\"Product\":\"10010423\",\"BookQuantity\":1,\"SignInQuantity\":1}]},{\"ID\":11998335,\"ClientID\":17434031,\"State\":12,\"StateName\":\"已审核\",\"OrderSource\":5,\"OrderSourceName\":\"积分订单\",\"AcceptRemark\":\"\",\"TeleNum\":\"18319387374\",\"AcceptDate\":\"2018-12-08T16:59:15.387\",\"ConfirmDate\":\"2018-12-09T14:31:25.12\",\"PreArrivalDate\":\"2018-12-08T16:59:15.677\",\"OFFHardbourDate\":\"1900-01-01T00:00:00\",\"BeginDeliveryDate\":\"1900-01-01T00:00:00\",\"SignInDate\":\"1900-01-01T00:00:00\",\"SignInMan\":\"\",\"Remark\":\"\",\"DeliveryCompany\":\"\",\"DeliverySheetCode\":\"\",\"DeliveryOfficialCity\":496,\"DeliveryOfficialCityCode\":\"440306\",\"DeliveryOfficialCityName\":\"广东省 深圳市 宝安区\",\"DeliveryAddre\":\"广东省深圳市宝安区 光明新区公明镇上村下南第三工业区B区4栋裕丰盛 (备用电话：013040899330)\",\"Consignee\":\"吴联华\",\"Deliverer\":16,\"AccountType\":\"金领冠\",\"Items\":[{\"ID\":15982137,\"Product\":\"10010423\",\"BookQuantity\":1,\"SignInQuantity\":1}]},{\"ID\":11989996,\"ClientID\":17889143,\"State\":12,\"StateName\":\"已审核\",\"OrderSource\":5,\"OrderSourceName\":\"积分订单\",\"AcceptRemark\":\"修改送货地址信息\",\"TeleNum\":\"18833486987\",\"AcceptDate\":\"2018-12-06T15:25:07.3\",\"ConfirmDate\":\"2018-12-10T08:41:39.103\",\"PreArrivalDate\":\"2018-12-06T15:25:07.29\",\"OFFHardbourDate\":\"1900-01-01T00:00:00\",\"BeginDeliveryDate\":\"1900-01-01T00:00:00\",\"SignInDate\":\"1900-01-01T00:00:00\",\"SignInMan\":\"\",\"Remark\":\"\",\"DeliveryCompany\":\"\",\"DeliverySheetCode\":\"\",\"DeliveryOfficialCity\":1922,\"DeliveryOfficialCityCode\":\"130527\",\"DeliveryOfficialCityName\":\"河北省 邢台市 南和县\",\"DeliveryAddre\":\"河北省邢台市南和县郝桥镇霍庄村 (备用电话：018833486987)\",\"Consignee\":\"凡青\",\"Deliverer\":16,\"AccountType\":\"金领冠\",\"Items\":[{\"ID\":15973798,\"Product\":\"10010423\",\"BookQuantity\":1,\"SignInQuantity\":1}]},{\"ID\":11994111,\"ClientID\":19120779,\"State\":12,\"StateName\":\"已审核\",\"OrderSource\":5,\"OrderSourceName\":\"积分订单\",\"AcceptRemark\":\"\",\"TeleNum\":\"013512076713\",\"AcceptDate\":\"2018-12-07T18:00:13.647\",\"ConfirmDate\":\"2018-12-08T13:36:44.547\",\"PreArrivalDate\":\"2018-12-07T18:00:12.283\",\"OFFHardbourDate\":\"1900-01-01T00:00:00\",\"BeginDeliveryDate\":\"1900-01-01T00:00:00\",\"SignInDate\":\"1900-01-01T00:00:00\",\"SignInMan\":\"\",\"Remark\":\"\",\"DeliveryCompany\":\"\",\"DeliverySheetCode\":\"\",\"DeliveryOfficialCity\":10093,\"DeliveryOfficialCityCode\":\"120111\",\"DeliveryOfficialCityName\":\"天津市 天津市辖区 西青区 中北镇\",\"DeliveryAddre\":\"天津市天津市辖区西青区中北镇 天津市辖区西青区中北镇 天津市西青区中北镇王顶堤商贸城D1二楼橙04号 (备用电话：013512076713)\",\"Consignee\":\"吴梅华\",\"Deliverer\":16,\"AccountType\":\"金领冠\",\"Items\":[{\"ID\":15977913,\"Product\":\"10010423\",\"BookQuantity\":1,\"SignInQuantity\":1}]},{\"ID\":11995938,\"ClientID\":16366644,\"State\":12,\"StateName\":\"已审核\",\"OrderSource\":5,\"OrderSourceName\":\"积分订单\",\"AcceptRemark\":\"商家中心------33021201837于2018-12-07 11:15:17,将状态更新为订单提交\",\"TeleNum\":\"13486866544\",\"AcceptDate\":\"2018-12-07T11:15:17.15\",\"ConfirmDate\":\"2018-12-08T13:25:46.483\",\"PreArrivalDate\":\"1900-01-01T00:00:00\",\"OFFHardbourDate\":\"1900-01-01T00:00:00\",\"BeginDeliveryDate\":\"1900-01-01T00:00:00\",\"SignInDate\":\"1900-01-01T00:00:00\",\"SignInMan\":\"\",\"Remark\":\"\",\"DeliveryCompany\":\"\",\"DeliverySheetCode\":\"\",\"DeliveryOfficialCity\":2993,\"DeliveryOfficialCityCode\":\"330212\",\"DeliveryOfficialCityName\":\"浙江省 宁波市 鄞州区\",\"DeliveryAddre\":\"宁波市梅墟街道梅景路554号阳光宝贝 (备用电话：013900000000)\",\"Consignee\":\"潘先生\",\"Deliverer\":16,\"AccountType\":\"金领冠\",\"Items\":[{\"ID\":15979740,\"Product\":\"1001003\",\"BookQuantity\":1,\"SignInQuantity\":1}]},{\"ID\":11997027,\"ClientID\":19046598,\"State\":12,\"StateName\":\"已审核\",\"OrderSource\":5,\"OrderSourceName\":\"积分订单\",\"AcceptRemark\":\"邮寄到店里\",\"TeleNum\":\"018009463750\",\"AcceptDate\":\"2018-12-08T12:29:30\",\"ConfirmDate\":\"2018-12-08T13:37:54.593\",\"PreArrivalDate\":\"2018-12-08T12:29:29.8\",\"OFFHardbourDate\":\"1900-01-01T00:00:00\",\"BeginDeliveryDate\":\"1900-01-01T00:00:00\",\"SignInDate\":\"1900-01-01T00:00:00\",\"SignInMan\":\"\",\"Remark\":\"\",\"DeliveryCompany\":\"\",\"DeliverySheetCode\":\"\",\"DeliveryOfficialCity\":45783,\"DeliveryOfficialCityCode\":\"621125\",\"DeliveryOfficialCityName\":\"甘肃省 定西市 漳县 城关镇\",\"DeliveryAddre\":\"甘肃省定西市漳县城关镇 贵清路5号楼安宝儿 (备用电话：018009463750)\",\"Consignee\":\"王丽霞\",\"Deliverer\":16,\"AccountType\":\"金领冠\",\"Items\":[{\"ID\":15980829,\"Product\":\"10070169\",\"BookQuantity\":1,\"SignInQuantity\":1}]},{\"ID\":11993747,\"ClientID\":22135246,\"State\":12,\"StateName\":\"已审核\",\"OrderSource\":5,\"OrderSourceName\":\"积分订单\",\"AcceptRemark\":\"\",\"TeleNum\":\"13415230990\",\"AcceptDate\":\"2018-12-07T15:43:34.683\",\"ConfirmDate\":\"2018-12-08T13:28:20.393\",\"PreArrivalDate\":\"2018-12-07T15:43:34.7\",\"OFFHardbourDate\":\"1900-01-01T00:00:00\",\"BeginDeliveryDate\":\"1900-01-01T00:00:00\",\"SignInDate\":\"1900-01-01T00:00:00\",\"SignInMan\":\"\",\"Remark\":\"\",\"DeliveryCompany\":\"\",\"DeliverySheetCode\":\"\",\"DeliveryOfficialCity\":44586,\"DeliveryOfficialCityCode\":\"441821\",\"DeliveryOfficialCityName\":\"广东省 清远市 佛冈县 石角镇\",\"DeliveryAddre\":\"广东省清远市佛冈县石角镇 沿江中路19号港深豪苑4栋1梯 (备用电话：013415230990)\",\"Consignee\":\"欧阳\",\"Deliverer\":16,\"AccountType\":\"金领冠\",\"Items\":[{\"ID\":15977549,\"Product\":\"10010423\",\"BookQuantity\":1,\"SignInQuantity\":1}]},{\"ID\":11996819,\"ClientID\":21449702,\"State\":12,\"StateName\":\"已审核\",\"OrderSource\":5,\"OrderSourceName\":\"积分订单\",\"AcceptRemark\":\"\",\"TeleNum\":\"13388379322\",\"AcceptDate\":\"2018-12-08T11:39:56.53\",\"ConfirmDate\":\"2018-12-08T13:38:53.7\",\"PreArrivalDate\":\"2018-12-08T11:39:56.783\",\"OFFHardbourDate\":\"1900-01-01T00:00:00\",\"BeginDeliveryDate\":\"1900-01-01T00:00:00\",\"SignInDate\":\"1900-01-01T00:00:00\",\"SignInMan\":\"\",\"Remark\":\"\",\"DeliveryCompany\":\"\",\"DeliverySheetCode\":\"\",\"DeliveryOfficialCity\":11885,\"DeliveryOfficialCityCode\":\"511024\",\"DeliveryOfficialCityName\":\"四川省 内江市 威远县 严陵镇\",\"DeliveryAddre\":\"四川省内江市威远县严陵镇 建业大道石牛仓库 乖品母婴C001区 (备用电话：018048711248)\",\"Consignee\":\"甘其容\",\"Deliverer\":16,\"AccountType\":\"金领冠\",\"Items\":[{\"ID\":15980621,\"Product\":\"10010423\",\"BookQuantity\":1,\"SignInQuantity\":1}]},{\"ID\":11996800,\"ClientID\":17937138,\"State\":12,\"StateName\":\"已审核\",\"OrderSource\":5,\"OrderSourceName\":\"积分订单\",\"AcceptRemark\":\"通过伊利微信兑换\",\"TeleNum\":\"13333377424\",\"AcceptDate\":\"2018-12-08T11:34:51.41\",\"ConfirmDate\":\"2018-12-08T13:29:28.173\",\"PreArrivalDate\":\"2018-12-08T11:34:51.677\",\"OFFHardbourDate\":\"1900-01-01T00:00:00\",\"BeginDeliveryDate\":\"1900-01-01T00:00:00\",\"SignInDate\":\"1900-01-01T00:00:00\",\"SignInMan\":\"\",\"Remark\":\"\",\"DeliveryCompany\":\"\",\"DeliverySheetCode\":\"\",\"DeliveryOfficialCity\":1449,\"DeliveryOfficialCityCode\":\"130121\",\"DeliveryOfficialCityName\":\"河北省 石家庄市 井陉县\",\"DeliveryAddre\":\"河北省石家庄市井陉县 微水镇建设北路15号 (备用电话：013315154586)\",\"Consignee\":\"王丽\",\"Deliverer\":16,\"AccountType\":\"金领冠\",\"Items\":[{\"ID\":15980602,\"Product\":\"10010423\",\"BookQuantity\":1,\"SignInQuantity\":1}]},{\"ID\":11998353,\"ClientID\":17282463,\"State\":12,\"StateName\":\"已审核\",\"OrderSource\":5,\"OrderSourceName\":\"积分订单\",\"AcceptRemark\":\"兑记分\",\"TeleNum\":\"018919294898\",\"AcceptDate\":\"2018-12-08T17:02:05.28\",\"ConfirmDate\":\"2018-12-09T14:51:47.003\",\"PreArrivalDate\":\"2018-12-08T17:02:04.907\",\"OFFHardbourDate\":\"1900-01-01T00:00:00\",\"BeginDeliveryDate\":\"1900-01-01T00:00:00\",\"SignInDate\":\"1900-01-01T00:00:00\",\"SignInMan\":\"\",\"Remark\":\"\",\"DeliveryCompany\":\"\",\"DeliverySheetCode\":\"\",\"DeliveryOfficialCity\":46424,\"DeliveryOfficialCityCode\":\"623022\",\"DeliveryOfficialCityName\":\"甘肃省 甘南藏族自治州 卓尼县 柳林镇\",\"DeliveryAddre\":\"甘肃省甘南藏族自治州卓尼县柳林镇 洮河林业局21号楼 (备用电话：018919294898)\",\"Consignee\":\" A乔红芳\",\"Deliverer\":16,\"AccountType\":\"金领冠\",\"Items\":[{\"ID\":15982155,\"Product\":\"10010423\",\"BookQuantity\":1,\"SignInQuantity\":1}]},{\"ID\":11996465,\"ClientID\":18341542,\"State\":12,\"StateName\":\"已审核\",\"OrderSource\":5,\"OrderSourceName\":\"积分订单\",\"AcceptRemark\":\"通过伊利微信兑换\",\"TeleNum\":\"13967618634\",\"AcceptDate\":\"2018-12-08T10:32:50.883\",\"ConfirmDate\":\"2018-12-08T13:37:27.74\",\"PreArrivalDate\":\"2018-12-08T10:32:50.92\",\"OFFHardbourDate\":\"1900-01-01T00:00:00\",\"BeginDeliveryDate\":\"1900-01-01T00:00:00\",\"SignInDate\":\"1900-01-01T00:00:00\",\"SignInMan\":\"\",\"Remark\":\"\",\"DeliveryCompany\":\"\",\"DeliverySheetCode\":\"\",\"DeliveryOfficialCity\":2728,\"DeliveryOfficialCityCode\":\"331024\",\"DeliveryOfficialCityName\":\"浙江省 台州市 仙居县\",\"DeliveryAddre\":\"浙江省台州市仙居县 安州街道岭下张村171号 (备用电话：013967618634)\",\"Consignee\":\"陈旭霞\",\"Deliverer\":16,\"AccountType\":\"金领冠\",\"Items\":[{\"ID\":15980267,\"Product\":\"10010423\",\"BookQuantity\":1,\"SignInQuantity\":1}]},{\"ID\":11996651,\"ClientID\":13767826,\"State\":12,\"StateName\":\"已审核\",\"OrderSource\":5,\"OrderSourceName\":\"积分订单\",\"AcceptRemark\":\"麻烦包装好点\",\"TeleNum\":\"13476656733\",\"AcceptDate\":\"2018-12-08T11:08:26.413\",\"ConfirmDate\":\"2018-12-08T13:31:30.207\",\"PreArrivalDate\":\"2018-12-08T11:08:26.73\",\"OFFHardbourDate\":\"1900-01-01T00:00:00\",\"BeginDeliveryDate\":\"1900-01-01T00:00:00\",\"SignInDate\":\"1900-01-01T00:00:00\",\"SignInMan\":\"\",\"Remark\":\"\",\"DeliveryCompany\":\"\",\"DeliverySheetCode\":\"\",\"DeliveryOfficialCity\":1806,\"DeliveryOfficialCityCode\":\"421181\",\"DeliveryOfficialCityName\":\"湖北省 黄冈市 麻城市\",\"DeliveryAddre\":\"湖北省黄冈市麻城市 闵集乡南湖卫生院对面 (备用电话：013476656733)\",\"Consignee\":\"夏先丹\",\"Deliverer\":16,\"AccountType\":\"金领冠\",\"Items\":[{\"ID\":15980453,\"Product\":\"10010423\",\"BookQuantity\":1,\"SignInQuantity\":1}]},{\"ID\":11998939,\"ClientID\":20792776,\"State\":12,\"StateName\":\"已审核\",\"OrderSource\":5,\"OrderSourceName\":\"积分订单\",\"AcceptRemark\":\"通过伊利微信兑换\",\"TeleNum\":\"15668102125\",\"AcceptDate\":\"2018-12-08T19:18:03.557\",\"ConfirmDate\":\"2018-12-09T14:47:39.197\",\"PreArrivalDate\":\"2018-12-08T19:18:04.003\",\"OFFHardbourDate\":\"1900-01-01T00:00:00\",\"BeginDeliveryDate\":\"1900-01-01T00:00:00\",\"SignInDate\":\"1900-01-01T00:00:00\",\"SignInMan\":\"\",\"Remark\":\"\",\"DeliveryCompany\":\"\",\"DeliverySheetCode\":\"\",\"DeliveryOfficialCity\":2921,\"DeliveryOfficialCityCode\":\"370882\",\"DeliveryOfficialCityName\":\"山东省 济宁市 兖州市\",\"DeliveryAddre\":\"山东省济宁市兖州市 鼓楼街道奎星苑E区二号楼一单元 (备用电话：015668102125)\",\"Consignee\":\"张蒙\",\"Deliverer\":16,\"AccountType\":\"金领冠\",\"Items\":[{\"ID\":15982741,\"Product\":\"10010423\",\"BookQuantity\":1,\"SignInQuantity\":1}]},{\"ID\":11999940,\"ClientID\":16366644,\"State\":12,\"StateName\":\"已审核\",\"OrderSource\":5,\"OrderSourceName\":\"积分订单\",\"AcceptRemark\":\"商家中心------34142201229于2018-12-08 12:28:43,将状态更新为订单提交\",\"TeleNum\":\"13135405007\",\"AcceptDate\":\"2018-12-08T12:28:43.84\",\"ConfirmDate\":\"2018-12-09T14:44:53.48\",\"PreArrivalDate\":\"1900-01-01T00:00:00\",\"OFFHardbourDate\":\"1900-01-01T00:00:00\",\"BeginDeliveryDate\":\"1900-01-01T00:00:00\",\"SignInDate\":\"1900-01-01T00:00:00\",\"SignInMan\":\"\",\"Remark\":\"\",\"DeliveryCompany\":\"\",\"DeliverySheetCode\":\"\",\"DeliveryOfficialCity\":2615,\"DeliveryOfficialCityCode\":\"341422\",\"DeliveryOfficialCityName\":\"安徽省 芜湖市 无为县\",\"DeliveryAddre\":\"安徽省芜湖市无为县无城镇晏公花园13栋405 (备用电话：013900000000)\",\"Consignee\":\"李斌\",\"Deliverer\":16,\"AccountType\":\"金领冠\",\"Items\":[{\"ID\":15983742,\"Product\":\"1001006\",\"BookQuantity\":1,\"SignInQuantity\":1}]},{\"ID\":11993502,\"ClientID\":19618701,\"State\":12,\"StateName\":\"已审核\",\"OrderSource\":5,\"OrderSourceName\":\"积分订单\",\"AcceptRemark\":\"通过伊利微信兑换\",\"TeleNum\":\"15932298105\",\"AcceptDate\":\"2018-12-07T14:52:42.693\",\"ConfirmDate\":\"2018-12-08T13:29:54.343\",\"PreArrivalDate\":\"2018-12-07T14:52:42.68\",\"OFFHardbourDate\":\"1900-01-01T00:00:00\",\"BeginDeliveryDate\":\"1900-01-01T00:00:00\",\"SignInDate\":\"1900-01-01T00:00:00\",\"SignInMan\":\"\",\"Remark\":\"\",\"DeliveryCompany\":\"\",\"DeliverySheetCode\":\"\",\"DeliveryOfficialCity\":2191,\"DeliveryOfficialCityCode\":\"130526\",\"DeliveryOfficialCityName\":\"河北省 邢台市 任县\",\"DeliveryAddre\":\"河北省邢台市任县 骆庄乡骆一村 (备用电话：015932298105)\",\"Consignee\":\"陈娇\",\"Deliverer\":16,\"AccountType\":\"金领冠\",\"Items\":[{\"ID\":15977304,\"Product\":\"10010423\",\"BookQuantity\":1,\"SignInQuantity\":1}]},{\"ID\":11997471,\"ClientID\":15804475,\"State\":12,\"StateName\":\"已审核\",\"OrderSource\":5,\"OrderSourceName\":\"积分订单\",\"AcceptRemark\":\"\",\"TeleNum\":\"15194886234\",\"AcceptDate\":\"2018-12-08T14:14:06.377\",\"ConfirmDate\":\"2018-12-09T14:43:42.11\",\"PreArrivalDate\":\"2018-12-08T14:14:06.337\",\"OFFHardbourDate\":\"1900-01-01T00:00:00\",\"BeginDeliveryDate\":\"1900-01-01T00:00:00\",\"SignInDate\":\"1900-01-01T00:00:00\",\"SignInMan\":\"\",\"Remark\":\"\",\"DeliveryCompany\":\"\",\"DeliverySheetCode\":\"\",\"DeliveryOfficialCity\":2442,\"DeliveryOfficialCityCode\":\"130627\",\"DeliveryOfficialCityName\":\"河北省 保定市 唐县\",\"DeliveryAddre\":\"河北省保定市唐县 北罗镇北罗辛庄村 (备用电话：015194886234)\",\"Consignee\":\"段少红\",\"Deliverer\":16,\"AccountType\":\"金领冠\",\"Items\":[{\"ID\":15981273,\"Product\":\"10010423\",\"BookQuantity\":1,\"SignInQuantity\":1}]},{\"ID\":11996973,\"ClientID\":15830352,\"State\":12,\"StateName\":\"已审核\",\"OrderSource\":5,\"OrderSourceName\":\"积分订单\",\"AcceptRemark\":\"\",\"TeleNum\":\"018686925333\",\"AcceptDate\":\"2018-12-08T12:15:45.6\",\"ConfirmDate\":\"2018-12-08T13:38:22.46\",\"PreArrivalDate\":\"2018-12-08T12:15:45.747\",\"OFFHardbourDate\":\"1900-01-01T00:00:00\",\"BeginDeliveryDate\":\"1900-01-01T00:00:00\",\"SignInDate\":\"1900-01-01T00:00:00\",\"SignInMan\":\"\",\"Remark\":\"\",\"DeliveryCompany\":\"\",\"DeliverySheetCode\":\"\",\"DeliveryOfficialCity\":34521,\"DeliveryOfficialCityCode\":\"230281\",\"DeliveryOfficialCityName\":\"黑龙江省 齐齐哈尔市 讷河市 讷河镇\",\"DeliveryAddre\":\"黑龙江省齐齐哈尔市讷河市讷河镇 黑龙江齐齐哈尔市讷河市北二道街百汇名苑斜对面天福孕婴杨红收15046238739 (备用电话：018686925333)\",\"Consignee\":\"王丽楠\",\"Deliverer\":16,\"AccountType\":\"金领冠\",\"Items\":[{\"ID\":15980775,\"Product\":\"10010423\",\"BookQuantity\":1,\"SignInQuantity\":1}]},{\"ID\":11997373,\"ClientID\":12376094,\"State\":12,\"StateName\":\"已审核\",\"OrderSource\":5,\"OrderSourceName\":\"积分订单\",\"AcceptRemark\":\"\",\"TeleNum\":\"13388379322\",\"AcceptDate\":\"2018-12-08T13:54:05.11\",\"ConfirmDate\":\"2018-12-09T14:44:20.883\",\"PreArrivalDate\":\"2018-12-08T13:54:05.407\",\"OFFHardbourDate\":\"1900-01-01T00:00:00\",\"BeginDeliveryDate\":\"1900-01-01T00:00:00\",\"SignInDate\":\"1900-01-01T00:00:00\",\"SignInMan\":\"\",\"Remark\":\"\",\"DeliveryCompany\":\"\",\"DeliverySheetCode\":\"\",\"DeliveryOfficialCity\":11885,\"DeliveryOfficialCityCode\":\"511024\",\"DeliveryOfficialCityName\":\"四川省 内江市 威远县 严陵镇\",\"DeliveryAddre\":\"四川省内江市威远县严陵镇 建业大道石牛仓库 乖品母婴C001区 (备用电话：018783260232)\",\"Consignee\":\"甘其容\",\"Deliverer\":16,\"AccountType\":\"金领冠\",\"Items\":[{\"ID\":15981175,\"Product\":\"10010423\",\"BookQuantity\":1,\"SignInQuantity\":1}]},{\"ID\":11999079,\"ClientID\":22421207,\"State\":12,\"StateName\":\"已审核\",\"OrderSource\":5,\"OrderSourceName\":\"积分订单\",\"AcceptRemark\":\"\",\"TeleNum\":\"13820143492\",\"AcceptDate\":\"2018-12-08T19:42:14.17\",\"ConfirmDate\":\"2018-12-09T14:52:29.37\",\"PreArrivalDate\":\"2018-12-08T19:42:14.15\",\"OFFHardbourDate\":\"1900-01-01T00:00:00\",\"BeginDeliveryDate\":\"1900-01-01T00:00:00\",\"SignInDate\":\"1900-01-01T00:00:00\",\"SignInMan\":\"\",\"Remark\":\"\",\"DeliveryCompany\":\"\",\"DeliverySheetCode\":\"\",\"DeliveryOfficialCity\":10194,\"DeliveryOfficialCityCode\":\"120223\",\"DeliveryOfficialCityName\":\"天津市 天津市辖县 静海县 子牙镇\",\"DeliveryAddre\":\"天津市天津市辖县静海县子牙镇 天津静海子牙镇潘庄子村 李家亮13820143492 (备用电话：018698007598)\",\"Consignee\":\"李家亮\",\"Deliverer\":16,\"AccountType\":\"金领冠\",\"Items\":[{\"ID\":15982881,\"Product\":\"10010423\",\"BookQuantity\":1,\"SignInQuantity\":1}]},{\"ID\":12000279,\"ClientID\":19575927,\"State\":12,\"StateName\":\"已审核\",\"OrderSource\":5,\"OrderSourceName\":\"积分订单\",\"AcceptRemark\":\"\",\"TeleNum\":\"13280144929\",\"AcceptDate\":\"2018-12-09T10:03:36.567\",\"ConfirmDate\":\"2018-12-09T14:37:05.25\",\"PreArrivalDate\":\"2018-12-09T10:03:36.567\",\"OFFHardbourDate\":\"1900-01-01T00:00:00\",\"BeginDeliveryDate\":\"1900-01-01T00:00:00\",\"SignInDate\":\"1900-01-01T00:00:00\",\"SignInMan\":\"\",\"Remark\":\"\",\"DeliveryCompany\":\"\",\"DeliverySheetCode\":\"\",\"DeliveryOfficialCity\":20643,\"DeliveryOfficialCityCode\":\"370705\",\"DeliveryOfficialCityName\":\"山东省 潍坊市 奎文区 新城街道\",\"DeliveryAddre\":\"山东省潍坊市奎文区新城街道 潍坊市高新技术开发区北宫街惠贤路路口东北角潍柴佳苑 (备用电话：015306462402)\",\"Consignee\":\"于先生\",\"Deliverer\":16,\"AccountType\":\"金领冠\",\"Items\":[{\"ID\":15984081,\"Product\":\"10010423\",\"BookQuantity\":1,\"SignInQuantity\":1}]},{\"ID\":11994541,\"ClientID\":21513080,\"State\":12,\"StateName\":\"已审核\",\"OrderSource\":5,\"OrderSourceName\":\"积分订单\",\"AcceptRemark\":\"通过伊利WAP兑换\",\"TeleNum\":\"13563979733\",\"AcceptDate\":\"2018-12-07T19:40:42.853\",\"ConfirmDate\":\"2018-12-08T13:34:31.593\",\"PreArrivalDate\":\"2018-12-07T19:40:42.863\",\"OFFHardbourDate\":\"1900-01-01T00:00:00\",\"BeginDeliveryDate\":\"1900-01-01T00:00:00\",\"SignInDate\":\"1900-01-01T00:00:00\",\"SignInMan\":\"\",\"Remark\":\"\",\"DeliveryCompany\":\"\",\"DeliverySheetCode\":\"\",\"DeliveryOfficialCity\":1146,\"DeliveryOfficialCityCode\":\"371312\",\"DeliveryOfficialCityName\":\"山东省 临沂市 河东区\",\"DeliveryAddre\":\"山东省临沂市河东区九曲镇坡卜村 (备用电话：013563979733)\",\"Consignee\":\"张杭青\",\"Deliverer\":16,\"AccountType\":\"金领冠\",\"Items\":[{\"ID\":15978343,\"Product\":\"10010423\",\"BookQuantity\":1,\"SignInQuantity\":1}]},{\"ID\":11996620,\"ClientID\":17825787,\"State\":12,\"StateName\":\"已审核\",\"OrderSource\":5,\"OrderSourceName\":\"积分订单\",\"AcceptRemark\":\"通过伊利微信兑换\",\"TeleNum\":\"15258272295\",\"AcceptDate\":\"2018-12-08T11:03:16.66\",\"ConfirmDate\":\"2018-12-08T13:37:22.337\",\"PreArrivalDate\":\"2018-12-08T11:03:16.97\",\"OFFHardbourDate\":\"1900-01-01T00:00:00\",\"BeginDeliveryDate\":\"1900-01-01T00:00:00\",\"SignInDate\":\"1900-01-01T00:00:00\",\"SignInMan\":\"\",\"Remark\":\"\",\"DeliveryCompany\":\"\",\"DeliverySheetCode\":\"\",\"DeliveryOfficialCity\":3062,\"DeliveryOfficialCityCode\":\"330281\",\"DeliveryOfficialCityName\":\"浙江省 宁波市 余姚市\",\"DeliveryAddre\":\"浙江省宁波市余姚市阳明街道舜泰路万家福超市 (备用电话：015258272295)\",\"Consignee\":\"小静\",\"Deliverer\":16,\"AccountType\":\"金领冠\",\"Items\":[{\"ID\":15980422,\"Product\":\"10010423\",\"BookQuantity\":1,\"SignInQuantity\":1}]},{\"ID\":11999930,\"ClientID\":16366644,\"State\":12,\"StateName\":\"已审核\",\"OrderSource\":5,\"OrderSourceName\":\"积分订单\",\"AcceptRemark\":\"商家中心------41900110171于2018-12-08 08:58:53,将状态更新为订单提交\",\"TeleNum\":\"19939111196\",\"AcceptDate\":\"2018-12-08T08:58:53.52\",\"ConfirmDate\":\"2018-12-09T14:35:45.723\",\"PreArrivalDate\":\"1900-01-01T00:00:00\",\"OFFHardbourDate\":\"1900-01-01T00:00:00\",\"BeginDeliveryDate\":\"1900-01-01T00:00:00\",\"SignInDate\":\"1900-01-01T00:00:00\",\"SignInMan\":\"\",\"Remark\":\"\",\"DeliveryCompany\":\"\",\"DeliverySheetCode\":\"\",\"DeliveryOfficialCity\":151028,\"DeliveryOfficialCityCode\":\"\",\"DeliveryOfficialCityName\":\"河南省 济源市 济源市Z\",\"DeliveryAddre\":\"河南省济源市东留村富康苑小区3巷1号 (备用电话：013900000000)\",\"Consignee\":\"赵江洁\",\"Deliverer\":16,\"AccountType\":\"金领冠\",\"Items\":[{\"ID\":15983732,\"Product\":\"1001011\",\"BookQuantity\":1,\"SignInQuantity\":1}]},{\"ID\":11997537,\"ClientID\":14364519,\"State\":12,\"StateName\":\"已审核\",\"OrderSource\":5,\"OrderSourceName\":\"积分订单\",\"AcceptRemark\":\"\",\"TeleNum\":\"015538122931\",\"AcceptDate\":\"2018-12-08T14:25:30.303\",\"ConfirmDate\":\"2018-12-09T14:32:55.003\",\"PreArrivalDate\":\"2018-12-08T14:25:29.993\",\"OFFHardbourDate\":\"1900-01-01T00:00:00\",\"BeginDeliveryDate\":\"1900-01-01T00:00:00\",\"SignInDate\":\"1900-01-01T00:00:00\",\"SignInMan\":\"\",\"Remark\":\"\",\"DeliveryCompany\":\"\",\"DeliverySheetCode\":\"\",\"DeliveryOfficialCity\":35835,\"DeliveryOfficialCityCode\":\"410103\",\"DeliveryOfficialCityName\":\"河南省 郑州市 二七区 淮河路街道\",\"DeliveryAddre\":\"河南省郑州市二七区淮河路街道 政通路兴华小区四号楼一单元三号楼 (备用电话：015538122931)\",\"Consignee\":\"李小伍（林林）\",\"Deliverer\":16,\"AccountType\":\"金领冠\",\"Items\":[{\"ID\":15981339,\"Product\":\"10010423\",\"BookQuantity\":1,\"SignInQuantity\":1}]},{\"ID\":12001000,\"ClientID\":18233032,\"State\":12,\"StateName\":\"已审核\",\"OrderSource\":5,\"OrderSourceName\":\"积分订单\",\"AcceptRemark\":\"通过伊利WAP兑换\",\"TeleNum\":\"13023072198\",\"AcceptDate\":\"2018-12-09T12:55:34\",\"ConfirmDate\":\"2018-12-09T14:33:10.973\",\"PreArrivalDate\":\"2018-12-09T12:55:34.78\",\"OFFHardbourDate\":\"1900-01-01T00:00:00\",\"BeginDeliveryDate\":\"1900-01-01T00:00:00\",\"SignInDate\":\"1900-01-01T00:00:00\",\"SignInMan\":\"\",\"Remark\":\"\",\"DeliveryCompany\":\"\",\"DeliverySheetCode\":\"\",\"DeliveryOfficialCity\":915,\"DeliveryOfficialCityCode\":\"340421\",\"DeliveryOfficialCityName\":\"安徽省 淮南市 凤台县\",\"DeliveryAddre\":\"安徽省淮南市凤台县 新集镇东朱村后朱 (备用电话：013023072198)\",\"Consignee\":\"郭焕焕\",\"Deliverer\":16,\"AccountType\":\"金领冠\",\"Items\":[{\"ID\":15984802,\"Product\":\"10010423\",\"BookQuantity\":1,\"SignInQuantity\":1}]},{\"ID\":12000275,\"ClientID\":21756455,\"State\":12,\"StateName\":\"已审核\",\"OrderSource\":5,\"OrderSourceName\":\"积分订单\",\"AcceptRemark\":\"\",\"TeleNum\":\"015242941678\",\"AcceptDate\":\"2018-12-09T10:03:00.2\",\"ConfirmDate\":\"2018-12-09T14:35:52.117\",\"PreArrivalDate\":\"2018-12-09T10:03:00.177\",\"OFFHardbourDate\":\"1900-01-01T00:00:00\",\"BeginDeliveryDate\":\"1900-01-01T00:00:00\",\"SignInDate\":\"1900-01-01T00:00:00\",\"SignInMan\":\"\",\"Remark\":\"\",\"DeliveryCompany\":\"\",\"DeliverySheetCode\":\"\",\"DeliveryOfficialCity\":25197,\"DeliveryOfficialCityCode\":\"211422\",\"DeliveryOfficialCityName\":\"辽宁省 葫芦岛市 建昌县 建昌镇\",\"DeliveryAddre\":\"辽宁省葫芦岛市建昌县建昌镇 大十字街联通对面宝贝坊收 (备用电话：015242941678)\",\"Consignee\":\"刘平\",\"Deliverer\":16,\"AccountType\":\"金领冠\",\"Items\":[{\"ID\":15984077,\"Product\":\"10010423\",\"BookQuantity\":1,\"SignInQuantity\":1}]},{\"ID\":11995946,\"ClientID\":16366644,\"State\":12,\"StateName\":\"已审核\",\"OrderSource\":5,\"OrderSourceName\":\"积分订单\",\"AcceptRemark\":\"RMS------3404040047于2018-12-07 12:14:52,将状态更新为订单提交\",\"TeleNum\":\"13625543826\",\"AcceptDate\":\"2018-12-07T12:14:52.163\",\"ConfirmDate\":\"2018-12-08T13:25:37.877\",\"PreArrivalDate\":\"1900-01-01T00:00:00\",\"OFFHardbourDate\":\"1900-01-01T00:00:00\",\"BeginDeliveryDate\":\"1900-01-01T00:00:00\",\"SignInDate\":\"1900-01-01T00:00:00\",\"SignInMan\":\"\",\"Remark\":\"\",\"DeliveryCompany\":\"\",\"DeliverySheetCode\":\"\",\"DeliveryOfficialCity\":2470,\"DeliveryOfficialCityCode\":\"340403\",\"DeliveryOfficialCityName\":\"安徽省 淮南市 田家庵区\",\"DeliveryAddre\":\"安徽省淮南市田家庵区人民北路淮南市妇幼保健院对面喜多宝贝母婴生活馆 (备用电话：013900000000)\",\"Consignee\":\"宫德红\",\"Deliverer\":16,\"AccountType\":\"金领冠\",\"Items\":[{\"ID\":15979748,\"Product\":\"1001009\",\"BookQuantity\":1,\"SignInQuantity\":1}]},{\"ID\":11996870,\"ClientID\":21431890,\"State\":12,\"StateName\":\"已审核\",\"OrderSource\":5,\"OrderSourceName\":\"积分订单\",\"AcceptRemark\":\"\",\"TeleNum\":\"13388379322\",\"AcceptDate\":\"2018-12-08T11:51:47.25\",\"ConfirmDate\":\"2018-12-08T13:38:53.937\",\"PreArrivalDate\":\"2018-12-08T11:51:47.52\",\"OFFHardbourDate\":\"1900-01-01T00:00:00\",\"BeginDeliveryDate\":\"1900-01-01T00:00:00\",\"SignInDate\":\"1900-01-01T00:00:00\",\"SignInMan\":\"\",\"Remark\":\"\",\"DeliveryCompany\":\"\",\"DeliverySheetCode\":\"\",\"DeliveryOfficialCity\":11885,\"DeliveryOfficialCityCode\":\"511024\",\"DeliveryOfficialCityName\":\"四川省 内江市 威远县 严陵镇\",\"DeliveryAddre\":\"四川省内江市威远县严陵镇 建业大道石牛仓库 乖品母婴C001区 (备用电话：013541606805)\",\"Consignee\":\"甘其容\",\"Deliverer\":16,\"AccountType\":\"金领冠\",\"Items\":[{\"ID\":15980672,\"Product\":\"10010423\",\"BookQuantity\":1,\"SignInQuantity\":1}]},{\"ID\":11999432,\"ClientID\":19913810,\"State\":12,\"StateName\":\"已审核\",\"OrderSource\":5,\"OrderSourceName\":\"积分订单\",\"AcceptRemark\":\"通过伊利微信兑换\",\"TeleNum\":\"15169164678\",\"AcceptDate\":\"2018-12-08T20:57:49.66\",\"ConfirmDate\":\"2018-12-09T14:37:32.63\",\"PreArrivalDate\":\"2018-12-08T20:57:50.17\",\"OFFHardbourDate\":\"1900-01-01T00:00:00\",\"BeginDeliveryDate\":\"1900-01-01T00:00:00\",\"SignInDate\":\"1900-01-01T00:00:00\",\"SignInMan\":\"\",\"Remark\":\"\",\"DeliveryCompany\":\"\",\"DeliverySheetCode\":\"\",\"DeliveryOfficialCity\":1596,\"DeliveryOfficialCityCode\":\"370112\",\"DeliveryOfficialCityName\":\"山东省 济南市 历城区\",\"DeliveryAddre\":\"山东省济南市历城区 工业北路恒大城H栋2206 (备用电话：015169164678)\",\"Consignee\":\"刘曼\",\"Deliverer\":16,\"AccountType\":\"金领冠\",\"Items\":[{\"ID\":15983234,\"Product\":\"10010423\",\"BookQuantity\":1,\"SignInQuantity\":1}]},{\"ID\":11995944,\"ClientID\":16366644,\"State\":12,\"StateName\":\"已审核\",\"OrderSource\":5,\"OrderSourceName\":\"积分订单\",\"AcceptRemark\":\"商家中心------13052501084于2018-12-07 11:38:36,将状态更新为订单提交\",\"TeleNum\":\"15131361332\",\"AcceptDate\":\"2018-12-07T11:38:36.49\",\"ConfirmDate\":\"2018-12-08T13:25:45.667\",\"PreArrivalDate\":\"1900-01-01T00:00:00\",\"OFFHardbourDate\":\"1900-01-01T00:00:00\",\"BeginDeliveryDate\":\"1900-01-01T00:00:00\",\"SignInDate\":\"1900-01-01T00:00:00\",\"SignInMan\":\"\",\"Remark\":\"\",\"DeliveryCompany\":\"\",\"DeliverySheetCode\":\"\",\"DeliveryOfficialCity\":1738,\"DeliveryOfficialCityCode\":\"130525\",\"DeliveryOfficialCityName\":\"河北省 邢台市 隆尧县\",\"DeliveryAddre\":\"隆尧县康庄西路全城宝宝儿童摄影西大门东边 (备用电话：013900000000)\",\"Consignee\":\"雷雨\",\"Deliverer\":16,\"AccountType\":\"金领冠\",\"Items\":[{\"ID\":15979746,\"Product\":\"1001006\",\"BookQuantity\":1,\"SignInQuantity\":1}]},{\"ID\":11994257,\"ClientID\":7302237,\"State\":12,\"StateName\":\"已审核\",\"OrderSource\":5,\"OrderSourceName\":\"积分订单\",\"AcceptRemark\":\"\",\"TeleNum\":\"18253009993\",\"AcceptDate\":\"2018-12-07T18:42:21.227\",\"ConfirmDate\":\"2018-12-08T13:34:18.96\",\"PreArrivalDate\":\"2018-12-07T18:42:21.277\",\"OFFHardbourDate\":\"1900-01-01T00:00:00\",\"BeginDeliveryDate\":\"1900-01-01T00:00:00\",\"SignInDate\":\"1900-01-01T00:00:00\",\"SignInMan\":\"\",\"Remark\":\"\",\"DeliveryCompany\":\"\",\"DeliverySheetCode\":\"\",\"DeliveryOfficialCity\":21994,\"DeliveryOfficialCityCode\":\"371702\",\"DeliveryOfficialCityName\":\"山东省 菏泽市 牡丹区 吴店镇\",\"DeliveryAddre\":\"山东省菏泽市牡丹区吴店镇 刘寨村 (备用电话：018253009993)\",\"Consignee\":\"婷\",\"Deliverer\":16,\"AccountType\":\"金领冠\",\"Items\":[{\"ID\":15978059,\"Product\":\"10010423\",\"BookQuantity\":1,\"SignInQuantity\":1}]},{\"ID\":11999598,\"ClientID\":17658325,\"State\":12,\"StateName\":\"已审核\",\"OrderSource\":5,\"OrderSourceName\":\"积分订单\",\"AcceptRemark\":\"\",\"TeleNum\":\"15973151656\",\"AcceptDate\":\"2018-12-08T21:38:44.107\",\"ConfirmDate\":\"2018-12-09T14:44:16.167\",\"PreArrivalDate\":\"2018-12-08T21:38:43.977\",\"OFFHardbourDate\":\"1900-01-01T00:00:00\",\"BeginDeliveryDate\":\"1900-01-01T00:00:00\",\"SignInDate\":\"1900-01-01T00:00:00\",\"SignInMan\":\"\",\"Remark\":\"\",\"DeliveryCompany\":\"\",\"DeliverySheetCode\":\"\",\"DeliveryOfficialCity\":29878,\"DeliveryOfficialCityCode\":\"430122\",\"DeliveryOfficialCityName\":\"湖南省 长沙市 望城县 黄金乡\",\"DeliveryAddre\":\"湖南省长沙市望城县黄金乡 金山桥社区新城国际花都广场子母孕婴 (备用电话：013618480849)\",\"Consignee\":\"陈任翔\",\"Deliverer\":16,\"AccountType\":\"金领冠\",\"Items\":[{\"ID\":15983400,\"Product\":\"10070169\",\"BookQuantity\":1,\"SignInQuantity\":1}]},{\"ID\":11995964,\"ClientID\":16366644,\"State\":12,\"StateName\":\"已审核\",\"OrderSource\":5,\"OrderSourceName\":\"积分订单\",\"AcceptRemark\":\"商家中心------43012440279于2018-12-07 18:57:30,将状态更新为订单提交\",\"TeleNum\":\"13667393649\",\"AcceptDate\":\"2018-12-07T18:57:30.023\",\"ConfirmDate\":\"2018-12-08T13:25:41.64\",\"PreArrivalDate\":\"1900-01-01T00:00:00\",\"OFFHardbourDate\":\"1900-01-01T00:00:00\",\"BeginDeliveryDate\":\"1900-01-01T00:00:00\",\"SignInDate\":\"1900-01-01T00:00:00\",\"SignInMan\":\"\",\"Remark\":\"\",\"DeliveryCompany\":\"\",\"DeliverySheetCode\":\"\",\"DeliveryOfficialCity\":1977,\"DeliveryOfficialCityCode\":\"430124\",\"DeliveryOfficialCityName\":\"湖南省 长沙市 宁乡县\",\"DeliveryAddre\":\"湖南省长沙市宁乡县大润发超市员工通道 (备用电话：013900000000)\",\"Consignee\":\"唐日华\",\"Deliverer\":16,\"AccountType\":\"金领冠\",\"Items\":[{\"ID\":15979766,\"Product\":\"1001006\",\"BookQuantity\":1,\"SignInQuantity\":1}]},{\"ID\":11998082,\"ClientID\":14541286,\"State\":12,\"StateName\":\"已审核\",\"OrderSource\":5,\"OrderSourceName\":\"积分订单\",\"AcceptRemark\":\"\",\"TeleNum\":\"18092155186\",\"AcceptDate\":\"2018-12-08T16:09:59.023\",\"ConfirmDate\":\"2018-12-09T14:33:30.867\",\"PreArrivalDate\":\"2018-12-08T16:09:58.96\",\"OFFHardbourDate\":\"1900-01-01T00:00:00\",\"BeginDeliveryDate\":\"1900-01-01T00:00:00\",\"SignInDate\":\"1900-01-01T00:00:00\",\"SignInMan\":\"\",\"Remark\":\"\",\"DeliveryCompany\":\"\",\"DeliverySheetCode\":\"\",\"DeliveryOfficialCity\":992,\"DeliveryOfficialCityCode\":\"610126\",\"DeliveryOfficialCityName\":\"陕西省 西安市 高陵县\",\"DeliveryAddre\":\"陕西省西安市高陵县 西安市高陵区泾河工业园泾渭苑一区55栋一单元102 (备用电话：018092155186)\",\"Consignee\":\"罗颖\",\"Deliverer\":16,\"AccountType\":\"金领冠\",\"Items\":[{\"ID\":15981884,\"Product\":\"10010423\",\"BookQuantity\":1,\"SignInQuantity\":1}]},{\"ID\":11998290,\"ClientID\":14457596,\"State\":12,\"StateName\":\"已审核\",\"OrderSource\":5,\"OrderSourceName\":\"积分订单\",\"AcceptRemark\":\"\",\"TeleNum\":\"18197269817\",\"AcceptDate\":\"2018-12-08T16:50:01.45\",\"ConfirmDate\":\"2018-12-09T14:34:51.563\",\"PreArrivalDate\":\"2018-12-08T16:50:01.08\",\"OFFHardbourDate\":\"1900-01-01T00:00:00\",\"BeginDeliveryDate\":\"1900-01-01T00:00:00\",\"SignInDate\":\"1900-01-01T00:00:00\",\"SignInMan\":\"\",\"Remark\":\"\",\"DeliveryCompany\":\"\",\"DeliverySheetCode\":\"\",\"DeliveryOfficialCity\":51994,\"DeliveryOfficialCityCode\":\"632126\",\"DeliveryOfficialCityName\":\"青海省 海东地区 互助土族自治县 威远镇\",\"DeliveryAddre\":\"青海省海东地区互助土族自治县威远镇 七彩星河湾华联生活超市 (备用电话：013649783990)\",\"Consignee\":\"李世梅\",\"Deliverer\":16,\"AccountType\":\"金领冠\",\"Items\":[{\"ID\":15982092,\"Product\":\"10010423\",\"BookQuantity\":1,\"SignInQuantity\":1}]},{\"ID\":12000726,\"ClientID\":16714367,\"State\":12,\"StateName\":\"已审核\",\"OrderSource\":5,\"OrderSourceName\":\"积分订单\",\"AcceptRemark\":\"\",\"TeleNum\":\"15214079410\",\"AcceptDate\":\"2018-12-09T11:40:45.81\",\"ConfirmDate\":\"2018-12-09T14:31:48.6\",\"PreArrivalDate\":\"2018-12-09T11:40:45.777\",\"OFFHardbourDate\":\"1900-01-01T00:00:00\",\"BeginDeliveryDate\":\"1900-01-01T00:00:00\",\"SignInDate\":\"1900-01-01T00:00:00\",\"SignInMan\":\"\",\"Remark\":\"\",\"DeliveryCompany\":\"\",\"DeliverySheetCode\":\"\",\"DeliveryOfficialCity\":628,\"DeliveryOfficialCityCode\":\"630105\",\"DeliveryOfficialCityName\":\"青海省 西宁市 城北区\",\"DeliveryAddre\":\"青海省西宁市城北区柴达木路金座雅园3期13号楼1081室 (备用电话：015214079410)\",\"Consignee\":\"刘文惠\",\"Deliverer\":16,\"AccountType\":\"金领冠\",\"Items\":[{\"ID\":15984528,\"Product\":\"10010423\",\"BookQuantity\":1,\"SignInQuantity\":1}]},{\"ID\":11999815,\"ClientID\":20869200,\"State\":12,\"StateName\":\"已审核\",\"OrderSource\":5,\"OrderSourceName\":\"积分订单\",\"AcceptRemark\":\"\",\"TeleNum\":\"15635413431\",\"AcceptDate\":\"2018-12-08T22:52:31.02\",\"ConfirmDate\":\"2018-12-09T14:49:23.95\",\"PreArrivalDate\":\"2018-12-08T22:52:31.083\",\"OFFHardbourDate\":\"1900-01-01T00:00:00\",\"BeginDeliveryDate\":\"1900-01-01T00:00:00\",\"SignInDate\":\"1900-01-01T00:00:00\",\"SignInMan\":\"\",\"Remark\":\"\",\"DeliveryCompany\":\"\",\"DeliverySheetCode\":\"\",\"DeliveryOfficialCity\":18604,\"DeliveryOfficialCityCode\":\"140721\",\"DeliveryOfficialCityName\":\"山西省 晋中市 榆社县 城关镇\",\"DeliveryAddre\":\"山西省晋中市榆社县城关镇 城关卫生院 (备用电话：015635413431)\",\"Consignee\":\"张翔\",\"Deliverer\":16,\"AccountType\":\"金领冠\",\"Items\":[{\"ID\":15983617,\"Product\":\"10010423\",\"BookQuantity\":1,\"SignInQuantity\":1}]},{\"ID\":11999959,\"ClientID\":16366644,\"State\":12,\"StateName\":\"已审核\",\"OrderSource\":5,\"OrderSourceName\":\"积分订单\",\"AcceptRemark\":\"RMS------熊黎于2018-12-08 17:01:21,将状态更新为订单提交\",\"TeleNum\":\"18627302400\",\"AcceptDate\":\"2018-12-08T17:01:21.103\",\"ConfirmDate\":\"2018-12-09T14:46:51.627\",\"PreArrivalDate\":\"1900-01-01T00:00:00\",\"OFFHardbourDate\":\"1900-01-01T00:00:00\",\"BeginDeliveryDate\":\"1900-01-01T00:00:00\",\"SignInDate\":\"1900-01-01T00:00:00\",\"SignInMan\":\"\",\"Remark\":\"\",\"DeliveryCompany\":\"\",\"DeliverySheetCode\":\"\",\"DeliveryOfficialCity\":2799,\"DeliveryOfficialCityCode\":\"431322\",\"DeliveryOfficialCityName\":\"湖南省 娄底市 新化县\",\"DeliveryAddre\":\"岳阳市岳阳楼区巴陵东路123号市一医院家属区雷锋山小区2栋 (备用电话：013900000000)\",\"Consignee\":\"张先生\",\"Deliverer\":16,\"AccountType\":\"金领冠\",\"Items\":[{\"ID\":15983761,\"Product\":\"1001011\",\"BookQuantity\":1,\"SignInQuantity\":1}]}]";
	
	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "getOrderData")
	public void getOrderData(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
//		MiniDaoPage<CvcGetOrderStatisticsEntity> list = cvcGetOrderStatisticsService.getAll(query, dataGrid.getPage(), dataGrid.getRows());
//		dataGrid.setResults(SystemTools.convertPaginatedList(list));
//		dataGrid.setTotal(cvcGetOrderStatisticsService.getCount(query, dataGrid.getPage(), dataGrid.getRows()));
//		TagUtil.datagrid(response, dataGrid);
		String ifLoad = request.getParameter("ifLoad")==null?"":request.getParameter("ifLoad");
		if(StringUtils.isEmpty(ifLoad)) {
			dataGrid.setResults(SystemTools.convertPaginatedList(null));
			dataGrid.setTotal(0);
			TagUtil.datagrid(response, dataGrid);
			return;
		}
		RequestGetExchangeOrderListWaitDeliveryJson requestGetExchangeOrderListWaitDeliveryJson = new RequestGetExchangeOrderListWaitDeliveryJson();
		requestGetExchangeOrderListWaitDeliveryJson.setDeliverer(Config.DELIVERER);
		CvcGetOrderStatisticsEntity cvcGetOrderStatisticsEntity = null;
		//抓单
		try {
			ResponseHead head =  ConmentHttp.sendHttp(new TukeRequestBody.Builder()
					.setSequence(3).setParams(requestGetExchangeOrderListWaitDeliveryJson)
					.setServiceCode("CRMIF.GetExchangeOrderListWaitDeliveryJson").builder(), ExchangeOrder.class);
			if(head.getReturn() >= 0) {
				List<ExchangeOrder> exchangeOrders = ConmentHttp.gson.fromJson(head.getResult(), new TypeToken<List<ExchangeOrder>>(){}.getType());
//				List<ExchangeOrder> exchangeOrders = ConmentHttp.gson.fromJson(result, new TypeToken<List<ExchangeOrder>>(){}.getType());
				if(CollectionUtils.isNotEmpty(exchangeOrders)) {
					cvcGetOrderStatisticsEntity = cvcGetOrderStatisticsService.addOrUpdateOrder(exchangeOrders);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
//		if(cvcGetOrderStatisticsEntity != null) {
		String unified_batch_no = DateFormatUtils.format(Calendar.getInstance(), "yyyyMMdd");
		CvcOrderInfoEntity query = new CvcOrderInfoEntity();
		query.setBatchNo(unified_batch_no);
		// query.setBatchNo("20181102");
		MiniDaoPage<CvcOrderInfoEntity> list = cvcOrderInfoService.getAll(query, dataGrid.getPage(),
				dataGrid.getRows());
		if (CollectionUtils.isNotEmpty(list.getResults())) {
			for (CvcOrderInfoEntity entity : list.getResults()) {
				entity.setAddTime(PhpDateUtils.parseDate(Long.parseLong(entity.getAddTime()), "yyyy-MM-dd HH:mm:ss"));
				entity.setGetTime(PhpDateUtils.parseDate(Long.parseLong(entity.getGetTime()), "yyyy-MM-dd HH:mm:ss"));
			}
		}
		dataGrid.setResults(SystemTools.convertPaginatedList(list));
		dataGrid.setTotal(cvcOrderInfoService.getCount(query, dataGrid.getPage(), dataGrid.getRows()));
		TagUtil.datagrid(response, dataGrid);
//		}
	}
	
	
	
	 /**
	  * 详情
	  * @return
	  */
	@RequestMapping(params="toDetail",method = RequestMethod.GET)
	public void cvcGetOrderStatisticsDetail(@RequestParam(required = true, value = "id" ) String id,HttpServletResponse response,HttpServletRequest request)throws Exception{
			VelocityContext velocityContext = new VelocityContext();
			String viewName = "hotel/getorderstatistics/cvcGetOrderStatistics-detail.vm";
			CvcGetOrderStatisticsEntity cvcGetOrderStatistics = cvcGetOrderStatisticsService.get(id);
			velocityContext.put("cvcGetOrderStatistics",cvcGetOrderStatistics);
			ViewVelocity.view(request,response,viewName,velocityContext);
	}
	
	
	/**
	 * 确认订单
	 * @return
	 */
	@RequestMapping(params = "getAllOrderAndRead",method ={RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public AjaxJson getAllOrderAndRead(){
		AjaxJson j = new AjaxJson();
		int size = 0;
		try {
			boolean ifStop = false;
			while(!ifStop) {
				RequestGetExchangeOrderListWaitDeliveryJson requestGetExchangeOrderListWaitDeliveryJson = new RequestGetExchangeOrderListWaitDeliveryJson();
				requestGetExchangeOrderListWaitDeliveryJson.setDeliverer(Config.DELIVERER);
				CvcGetOrderStatisticsEntity cvcGetOrderStatisticsEntity = null;
				//抓单
				try {
					ResponseHead head =  ConmentHttp.sendHttp(new TukeRequestBody.Builder()
							.setSequence(3).setParams(requestGetExchangeOrderListWaitDeliveryJson)
							.setServiceCode("CRMIF.GetExchangeOrderListWaitDeliveryJson").builder(), ExchangeOrder.class);
					if(head.getReturn() >= 0) {
						List<ExchangeOrder> exchangeOrders = ConmentHttp.gson.fromJson(head.getResult(), new TypeToken<List<ExchangeOrder>>(){}.getType());
						if(CollectionUtils.isNotEmpty(exchangeOrders)) {
							cvcGetOrderStatisticsEntity = cvcGetOrderStatisticsService.addOrUpdateOrder(exchangeOrders);
							//确认订单
							String batchNo = DateFormatUtils.format(Calendar.getInstance(), "yyyyMMdd");
							List<CvcOrderInfoEntity> cvcOrderInfoEntities = cvcOrderInfoService.getCanReadOrders(batchNo);
							if(CollectionUtils.isEmpty(cvcOrderInfoEntities)) {
								j.setSuccess(false);
								j.setMsg("无可确认订单，请刷新页面重试！");
								return j; 
							}
							
							StringBuffer sb = new StringBuffer();
							for (CvcOrderInfoEntity cvcOrderInfoEntity : cvcOrderInfoEntities) {
								sb.append(cvcOrderInfoEntity.getId());
								sb.append(",");
							}
							if(sb.length() > 0) {
								sb.deleteCharAt(sb.length()-1);
							}
							RequestSetOrdersReadJson params = new RequestSetOrdersReadJson();
							params.setOrderIDs(sb.toString());
							head = ConmentHttp.sendHttp(new TukeRequestBody.Builder()
									.setSequence(2)
									.setServiceCode("CRMIF.SetOrdersReadJson")
									.setParams(params).builder(), null);
							if(head.getReturn() >= 0) {
								cvcGetOrderStatisticsService.addwaitDeliveryCount(cvcOrderInfoEntities.size(),batchNo);
								size += cvcOrderInfoEntities.size();
							}else {
								j.setSuccess(false);
								j.setMsg("确认失败，原因:伊利接口调用失败!");
							}
						}else {
							ifStop = true;
							//停止
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					ifStop = true;
				}
			}
		} catch (Exception e) {
		    log.info(e.getMessage());
			j.setSuccess(false);
			j.setMsg("抓单失败");
			return j;
		}
		j.setSuccess(true);
		j.setMsg("你已成功抓取"+size+"个订单!");
		return j;
	}
	
	/**
	 * 确认订单
	 * @return
	 */
	@RequestMapping(params = "setOrderRead",method ={RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public AjaxJson setOrderRead(@RequestParam(required = false, value = "batchNo")String batchNo){
		AjaxJson j = new AjaxJson();
		try {
			List<CvcOrderInfoEntity> cvcOrderInfoEntities = cvcOrderInfoService.getCanReadOrders(batchNo);
			if(CollectionUtils.isEmpty(cvcOrderInfoEntities)) {
				j.setSuccess(false);
				j.setMsg("无可确认订单，请刷新页面重试！");
				return j; 
			}
			
			StringBuffer sb = new StringBuffer();
			for (CvcOrderInfoEntity cvcOrderInfoEntity : cvcOrderInfoEntities) {
				sb.append(cvcOrderInfoEntity.getId());
				sb.append(",");
			}
			if(sb.length() > 0) {
				sb.deleteCharAt(sb.length()-1);
			}
			
			RequestSetOrdersReadJson params = new RequestSetOrdersReadJson();
			params.setOrderIDs(sb.toString());
			ResponseHead head = ConmentHttp.sendHttp(new TukeRequestBody.Builder()
					.setSequence(2)
					.setServiceCode("CRMIF.SetOrdersReadJson")
					.setParams(params).builder(), null);
			if(head.getReturn() >= 0) {
				cvcGetOrderStatisticsService.addwaitDeliveryCount(cvcOrderInfoEntities.size(),batchNo);
				
				j.setSuccess(true);
				j.setMsg("你已成功读取"+cvcOrderInfoEntities.size()+"个订单!");
			}else {
				j.setSuccess(false);
				j.setMsg("确认失败，原因:伊利接口调用失败!");
				return j;
			}
		} catch (Exception e) {
		    log.info(e.getMessage());
			j.setSuccess(false);
			j.setMsg("保存失败");
		}
		return j;
	}
	
	/**
	 * 仓库配货
	 * @return
	 */
	@RequestMapping(params = "allocateOrder",method ={RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public AjaxJson allocateOrder(@RequestParam(required = false, value = "batchNo")String batchNo){
		AjaxJson j = new AjaxJson();
		try {
			List<CvcOrderInfoEntity> cvcOrderInfoEntities = cvcOrderInfoService.getCanCangKu();
			if(CollectionUtils.isEmpty(cvcOrderInfoEntities)) {
				j.setSuccess(false);
				j.setMsg("无可仓库配货，请刷新页面重试！");
				return j; 
			}
			
			StringBuffer sb = new StringBuffer();
			for (CvcOrderInfoEntity cvcOrderInfoEntity : cvcOrderInfoEntities) {
				sb.append(cvcOrderInfoEntity.getId());
				sb.append(",");
			}
			if(sb.length() > 0) {
				sb.deleteCharAt(sb.length()-1);
			}
			
			RequestSetOrdersReadJson params = new RequestSetOrdersReadJson();
			params.setOrderIDs(sb.toString());
			ResponseHead head = ConmentHttp.sendHttp(new TukeRequestBody.Builder()
					.setSequence(2)
					.setServiceCode("CRMIF.AllocateExchangeOrderJson")
					.setParams(params).builder(), null);
			if(head.getReturn() >= 0) {
				cvcOrderInfoService.updateAllocateOrder();
				
				j.setSuccess(true);
				j.setMsg("你已成功配货"+cvcOrderInfoEntities.size()+"个订单!");
			}else {
				j.setSuccess(false);
				j.setMsg("配货失败，原因:伊利接口调用失败!");
				return j;
			}
		} catch (Exception e) {
		    log.info(e.getMessage());
			j.setSuccess(false);
			j.setMsg("配货失败");
		}
		return j;
	}
	
	/**
	 * 跳转到添加页面
	 * @return
	 */
	@RequestMapping(params = "toAdd",method ={RequestMethod.GET, RequestMethod.POST})
	public void toAddDialog(HttpServletRequest request,HttpServletResponse response)throws Exception{
		 VelocityContext velocityContext = new VelocityContext();
		 String viewName = "hotel/getorderstatistics/cvcGetOrderStatistics-add.vm";
		 ViewVelocity.view(request,response,viewName,velocityContext);
	}

	

	/**
	 * 跳转到编辑页面
	 * @return
	 */
	@RequestMapping(params="toEdit",method = RequestMethod.GET)
	public void toEdit(@RequestParam(required = true, value = "id" ) String id,HttpServletResponse response,HttpServletRequest request) throws Exception{
			 VelocityContext velocityContext = new VelocityContext();
			 CvcGetOrderStatisticsEntity cvcGetOrderStatistics = cvcGetOrderStatisticsService.get(id);
			 velocityContext.put("cvcGetOrderStatistics",cvcGetOrderStatistics);
			 String viewName = "hotel/getorderstatistics/cvcGetOrderStatistics-edit.vm";
			 ViewVelocity.view(request,response,viewName,velocityContext);
	}

	/**
	 * 编辑
	 * @return
	 */
	@RequestMapping(params = "doEdit",method ={RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public AjaxJson doEdit(@ModelAttribute CvcGetOrderStatisticsEntity cvcGetOrderStatistics){
		AjaxJson j = new AjaxJson();
		try {
			cvcGetOrderStatisticsService.update(cvcGetOrderStatistics);
			j.setMsg("编辑成功");
		} catch (Exception e) {
		    log.info(e.getMessage());
			j.setSuccess(false);
			j.setMsg("编辑失败");
		}
		return j;
	}


	/**
	 * 删除
	 * @return
	 */
	@RequestMapping(params="doDelete",method = RequestMethod.GET)
	@ResponseBody
	public AjaxJson doDelete(@RequestParam(required = true, value = "id" ) String id){
			AjaxJson j = new AjaxJson();
			try {
				cvcGetOrderStatisticsService.delete(id);
				j.setMsg("删除成功");
			} catch (Exception e) {
			    log.info(e.getMessage());
				j.setSuccess(false);
				j.setMsg("删除失败");
			}
			return j;
	}
	
	/**
	 * 批量删除数据
	 * @param ids
	 * @return
	 */
	@RequestMapping(params="batchDelete",method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson batchDelete(@RequestParam(required = true, value = "ids") String[] ids) {
		AjaxJson j = new AjaxJson();
		try {
			cvcGetOrderStatisticsService.batchDelete(ids);
			j.setMsg("批量删除成功");
		} catch(Exception e) {
			log.info(e.getMessage());
			j.setSuccess(false);
			j.setMsg("批量删除失败");
		}
		return j;
	}

}

