package com.partsMeasurePage.dao;

import java.util.List;

import com.partsMeasurePage.bean.ApplyBillBean;
import com.partsMeasurePage.bean.Get_sample_bean;
import com.partsMeasurePage.mapper.GetSampleMapper;

/**
 *	送檢取料dao
 * @author C3005579
 * @date 2018年9月8日 下午3:36:31 
 */
public class GetSampleDao extends BaseDao {
	private GetSampleMapper mapper = sqlSession.getMapper(GetSampleMapper.class);
	
	// 查詢申請單list
	public List<ApplyBillBean> queryApplyBillList() {
		List<ApplyBillBean> queryApplyBilList = mapper.queryApplyBilList();
		closeResources();
		
		return queryApplyBilList;
	}
	
	// 新增送檢取料
	public String addGetSampleBill(Get_sample_bean bean) {
		String flag = "1";// 1成功，0失敗
		try {
			mapper.addGetSampleBill(bean);
		} catch (Exception e) {
			flag = "0";
			e.printStackTrace();
		}
		closeResources();
		
		return flag;
	}
	
	// 查詢待取料和結案的所有申請單信息
	public void queryAllBillInfo() {
		
	}
	
	
}


