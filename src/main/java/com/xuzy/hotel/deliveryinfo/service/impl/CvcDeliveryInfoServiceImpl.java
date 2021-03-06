package com.xuzy.hotel.deliveryinfo.service.impl;

import javax.annotation.Resource;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.time.DateFormatUtils;
import org.jeecgframework.minidao.pojo.MiniDaoPage;
import org.springframework.stereotype.Service;

import com.xuzy.hotel.deliveryinfo.dao.CvcDeliveryInfoDao;
import com.xuzy.hotel.deliveryinfo.entity.CvcDeliveryInfoEntity;
import com.xuzy.hotel.deliveryinfo.service.CvcDeliveryInfoService;

/**
 * 描述：物流表
 * @author: www.jeecg.org
 * @since：2018年11月20日 20时42分45秒 星期二 
 * @version:1.0
 */

@Service("cvcDeliveryInfoService")
public class CvcDeliveryInfoServiceImpl implements CvcDeliveryInfoService {
	@Resource
	private CvcDeliveryInfoDao cvcDeliveryInfoDao;

	@Override
	public CvcDeliveryInfoEntity get(String id) {
		return cvcDeliveryInfoDao.get(id);
	}

	@Override
	public int update(CvcDeliveryInfoEntity cvcDeliveryInfo) {
		return cvcDeliveryInfoDao.update(cvcDeliveryInfo);
	}

	@Override
	public void insert(CvcDeliveryInfoEntity cvcDeliveryInfo) {
		cvcDeliveryInfoDao.insert(cvcDeliveryInfo);
		
	}

	@Override
	public MiniDaoPage<CvcDeliveryInfoEntity> getAll(CvcDeliveryInfoEntity cvcDeliveryInfo, int page, int rows) {
		return cvcDeliveryInfoDao.getAll(cvcDeliveryInfo, page, rows);
	}

	@Override
	public void delete(String id) {
		cvcDeliveryInfoDao.delete(id);
		
	}
	
	@Override
	public void batchDelete(String[] ids) {
		for(int i = 0; i < ids.length; i++) {
			String id = ids[i];
			cvcDeliveryInfoDao.deleteById(id);
		}
	}
	
	@Override
	public CvcDeliveryInfoEntity getDeliveryInfosByInvoiceNo(String invoiceNo) {
		return cvcDeliveryInfoDao.getAll(invoiceNo);
	}

	@Override
	public List<CvcDeliveryInfoEntity> getAllError() {
		return cvcDeliveryInfoDao.getAllError();
	}

	@Override
	public List<CvcDeliveryInfoEntity> getListOneHours() {
		Calendar calendar = Calendar.getInstance();
		String endTime = DateFormatUtils.format(calendar, "yyyy-MM-dd HH24");
		calendar.add(Calendar.HOUR_OF_DAY, -24);
		String startTime = DateFormatUtils.format(calendar, "yyyy-MM-dd HH24");
		return cvcDeliveryInfoDao.getListOneHours(startTime,endTime);
	}

	@Override
	public CvcDeliveryInfoEntity getFirstTime(String invoice_no) {
		return cvcDeliveryInfoDao.getFirstTime(invoice_no);
	}
}
