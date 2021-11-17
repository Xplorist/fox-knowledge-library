package com.partsMeasurePage.action;

import java.util.List;

import com.partsMeasurePage.bean.ApplyBillBean;
import com.partsMeasurePage.bean.Get_sample_bean;
import com.partsMeasurePage.dao.GetSampleDao;

/**
 *	送檢取料action
 * @author C3005579
 * @date 2018年9月8日 下午3:14:36 
 */
public class GetSampleAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private GetSampleDao dao = new GetSampleDao();
	private Get_sample_bean get_sample_bean;// 新增送檢取料單數據
	
	// 查詢待取料和結案的所有申請單list
	public void queryApplyBillList() {
		List<ApplyBillBean> applyBillList = dao.queryApplyBillList();
		
		jsonObject.put("applyBillList", applyBillList);
		sendJson(jsonObject);
	}
	
	// 新增送檢取料
	public void addGetSampleBill() {
		result = dao.addGetSampleBill(get_sample_bean);// 1成功，0失敗
		
		jsonObject.put("result", result);
		sendJson(jsonObject);
	}
	
	// 查詢待取料和結案的所有申請單信息
	public void queryAllBillInfo() {
		
	}
	
	public static void main(String[] args) {
		GetSampleDao dao = new GetSampleDao();
		List<ApplyBillBean> applyBillList = dao.queryApplyBillList();
		System.out.println(applyBillList.get(0).getBill_no());
	}

	public GetSampleAction() {
		super();
	}

	public GetSampleDao getDao() {
		return dao;
	}

	public void setDao(GetSampleDao dao) {
		this.dao = dao;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Get_sample_bean getGet_sample_bean() {
		return get_sample_bean;
	}

	public void setGet_sample_bean(Get_sample_bean get_sample_bean) {
		this.get_sample_bean = get_sample_bean;
	}
	
}


