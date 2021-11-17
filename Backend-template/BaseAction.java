package com.partsMeasurePage.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import net.sf.json.JSONObject;

/**
 *	基礎action，作為具體業務action的父類，封裝常用方法便於複用
 * @author C3005579
 * @date 2018年9月8日 下午3:24:33 
 */
public class BaseAction extends ActionSupport {
	protected static final long serialVersionUID = 1L;
	protected JSONObject jsonObject = new JSONObject();
	protected String result;// 成功與否標誌,成功為1，失敗為0
	
	// 返回json數據
	public void sendJson(JSONObject jsonObject) {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		out.write(jsonObject.toString());
		out.flush();
		out.close();
	}
	
	// 獲取登陸人工號
	public String getUserId() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		String userid = (String) session.getAttribute("userid");
		
		return userid;
	}
	
	public BaseAction() {
		super();
	}
	public JSONObject getJsonObject() {
		return jsonObject;
	}
	public void setJsonObject(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}


