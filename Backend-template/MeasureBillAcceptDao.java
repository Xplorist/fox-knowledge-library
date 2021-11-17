package com.partsMeasurePage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.partsMeasurePage.bean.ApplyBillBean;

import db.macIqisDB.MacIqisDB;

/**
 *	檢測室接單dao
 * @author C3005579
 * @date 2018年9月3日 下午3:41:29 
 */
public class MeasureBillAcceptDao {
	private String errorFlag = null;
	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;

	// 初始化dao變量
	public void initResources() {
		try {
			errorFlag = "0";
			conn = MacIqisDB.getConnection();
			ps = null;
			rs = null;
			conn.setAutoCommit(false);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	// 關閉dao變量
	public void closeResources() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (conn != null && !conn.isClosed()) {
				if ("1".equals(errorFlag)) {
					conn.rollback();
				} else {
					conn.commit();
				}
				conn.setAutoCommit(true);
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// dao模板
	public void template() {
		initResources();

		try {
			String sql = "";
			ps = conn.prepareStatement(sql);
			ps.setString(1, "");
			rs = ps.executeQuery();

			while (rs.next()) {
				rs.getString("");
			}
		} catch (SQLException e) {
			errorFlag = "1";
			e.printStackTrace();
		}

		closeResources();
	}
	
	// 查詢檢測室接單數據
	public List<ApplyBillBean> queryAcceptBill(String starttime, String endtime) {
		List<ApplyBillBean> result = new ArrayList<ApplyBillBean>();
		initResources();
		
		try {
			String sql = "select t.bill_no, \n"
						+"       t.model, \n"
						+"       t.process_no, \n"
						+"       t.measure_method, \n"
						+"       t.urgency_degree, \n"
						+"       t.sample_num, \n"
						+"       t.send_emp_no, \n"
						+"       t.send_emp_name, \n"
						+"       to_char(t.apply_date, 'yyyy/mm/dd hh24:mi:ss') apply_date, \n"
						+"       t.measure_type, \n"
						+"       t.apply_dept_code, \n"
						+"       t.apply_cost_code, \n"
						+"       t.send_testroom, \n"
						+"       to_char(t.send_date, 'yyyy/mm/dd hh24:mi:ss') send_date, \n"
						+"       t.apply_emp_no, \n"
						+"       t.apply_emp_name, \n"
						+"       t.apply_emp_tel, \n"
						+"       t.apply_emp_email, \n"
						+"       t.send_emp_no, \n"
						+"       t.send_emp_name, \n"
						+"       t.send_emp_tel, \n"
						+"       t.send_emp_email, \n"
						+"       t.file_origin_name, \n"
						+"       t.file_save_name, \n"
						+"       t.file_save_path, \n"
						+"       t.attention_item, \n"
						+"       t.creater, \n"
						+"       t.bill_status, \n"
						+"       a.testroom, \n"
						+"       a.bill_receiver_no, \n"
						+"       a.bill_receiver_name, \n"
						+"       a.is_send_email, \n"
						+"       a.memo, \n"
						+"       to_char(a.createdate, 'yyyy/mm/dd hh24:mi:ss') bill_accept_date,  \n"
						+"       a.bill_accept_status \n"
						+"from PARTS_MEASURE_APPLY_BILL t, PARTS_MEASURE_ACCEPT_BILL a \n"
						+" where t.bill_no = a.bill_no(+) \n"
						+"   and t.bill_status in ('S0', 'S2', 'S3') \n"
						+"   and t.is_use = 'Y' \n"
						+"   and ((a.createdate >= to_date(?, 'yyyy/mm/dd') and \n"
						+"       a.createdate <= to_date(?, 'yyyy/mm/dd') + 1) or \n"
						+"       a.createdate is null) \n"
						+"order by a.createdate desc \n";
			
			ps = conn.prepareStatement(sql);
			ps.setString(1, starttime);
			ps.setString(2, endtime);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				ApplyBillBean bean = new ApplyBillBean();
				bean.setBill_no(rs.getString("bill_no"));
				bean.setModel(rs.getString("model"));
				bean.setProcess_no(rs.getString("process_no"));
				bean.setMeasure_method(rs.getString("measure_method"));
				bean.setUrgency_degree(rs.getString("urgency_degree"));
				bean.setSample_num(rs.getInt("sample_num"));
				bean.setSend_emp_no(rs.getString("send_emp_no"));
				bean.setSend_emp_name(rs.getString("send_emp_name"));
				bean.setApply_date(rs.getString("apply_date"));
				bean.setMeasure_type(rs.getString("measure_type"));
				bean.setApply_dept_code(rs.getString("apply_dept_code"));
				bean.setApply_cost_code(rs.getString("apply_cost_code"));
				bean.setSend_testroom(rs.getString("send_testroom"));
				bean.setSend_date(rs.getString("send_date"));
				bean.setApply_emp_no(rs.getString("apply_emp_no"));
				bean.setApply_emp_name(rs.getString("apply_emp_name"));
				bean.setApply_emp_tel(rs.getString("apply_emp_tel"));
				bean.setApply_emp_email(rs.getString("apply_emp_email"));
				bean.setSend_emp_no(rs.getString("send_emp_no"));
				bean.setSend_emp_name(rs.getString("send_emp_name"));
				bean.setSend_emp_tel(rs.getString("send_emp_tel"));
				bean.setSend_emp_email(rs.getString("send_emp_email"));
				bean.setFile_origin_name(rs.getString("file_origin_name"));
				bean.setFile_save_name(rs.getString("file_save_name"));
				bean.setFile_save_path(rs.getString("file_save_path"));
				bean.setAttention_item(rs.getString("attention_item"));
				bean.setCreater(rs.getString("creater"));
				bean.setBill_status(rs.getString("bill_status"));
				bean.setBill_accept_testroom(rs.getString("testroom"));
				bean.setBill_receiver_no(rs.getString("bill_receiver_no"));
				bean.setBill_receiver_name(rs.getString("bill_receiver_name"));
				bean.setIs_send_email(rs.getString("is_send_email"));
				bean.setMemo(rs.getString("memo"));
				bean.setBill_accept_date(rs.getString("bill_accept_date"));
				bean.setBill_accept_status(rs.getString("bill_accept_status"));
				result.add(bean);
			}
		} catch (SQLException e) {
			errorFlag = "1";
			e.printStackTrace();
		}
		closeResources();
		
		return result;
	}
	
	// 操作（接收或拒單）
	public String operate(ApplyBillBean bean, String userId) {
		String flag = "1";// 成功1，失敗0
		initResources();

		try {
			String sql = "insert into PARTS_MEASURE_ACCEPT_BILL t \n"
						+"  (t.bill_no,                           \n"
						+"   t.testroom,                          \n"
						+"   t.bill_receiver_no,                  \n"
						+"   t.bill_receiver_name,                \n"
						+"   t.is_send_email,                     \n"
						+"   t.memo,                              \n"
						+"   t.bill_accept_status,                \n"
						+"   t.creater)                			  \n"
						+"values                                  \n"
						+"  (?, ?, ?, ?, ?, ?, ?, ?)              \n";
			
			ps = conn.prepareStatement(sql);
			ps.setString(1, bean.getBill_no());
			ps.setString(2, bean.getBill_accept_testroom());
			ps.setString(3, bean.getBill_receiver_no());
			ps.setString(4, bean.getBill_receiver_name());
			ps.setString(5, bean.getIs_send_email());
			ps.setString(6, bean.getMemo());
			ps.setString(7, bean.getBill_accept_status());
			ps.setString(8, userId);
			ps.executeUpdate();
		} catch (SQLException e) {
			flag = "0";
			errorFlag = "1";
			e.printStackTrace();
		}
		
		try {
			String accept_status = bean.getBill_accept_status();
			String status = "Y".equals(accept_status) ? "S3" : "S0";// 接單"S3",拒單"S0"
			
			String sql = "update PARTS_MEASURE_APPLY_BILL t "
					+ "set t.bill_status = ? where t.bill_no = ? and t.is_use = 'Y'";
			ps = conn.prepareStatement(sql);
			ps.setString(1, status);
			ps.setString(2, bean.getBill_no());
			ps.executeUpdate();
		} catch (SQLException e) {
			flag = "0";
			errorFlag = "1";
			e.printStackTrace();
		}
		closeResources();
		
		return flag;
	}
	
	public MeasureBillAcceptDao() {
		super();
	}
	
	
}


