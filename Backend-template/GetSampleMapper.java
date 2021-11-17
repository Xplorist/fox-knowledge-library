package com.partsMeasurePage.mapper;

import java.util.List;

import com.partsMeasurePage.bean.ApplyBillBean;
import com.partsMeasurePage.bean.Get_sample_bean;

/**
 *	送檢取料mapper
 * @author C3005579
 * @date 2018年9月8日 下午3:16:24 
 */
public interface GetSampleMapper {
	// 查詢申請單
	List<ApplyBillBean> queryApplyBilList();
	
	// 新增送檢取料
	void addGetSampleBill(Get_sample_bean bean);
	
	
}


