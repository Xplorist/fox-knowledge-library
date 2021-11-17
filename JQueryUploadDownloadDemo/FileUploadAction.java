package com.partsMeasurePage.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.partsMeasurePage.util.PropertiesUtil;

import net.sf.json.JSONObject;

/**
 *	文件上傳action
 * @author C3005579
 * @date 2018年9月4日 上午10:08:17 
 */
public class FileUploadAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private JSONObject jsonObject = new JSONObject();
	private String result; // 1成功，0失敗
	private File file;
	private String file_origin_name;// 文件原始名稱
	private String file_save_name;// 文件保存名稱
	private String file_save_path;// 文件保存路徑
	
	// 返回json數據
	public void sendJson(JSONObject jsonObject) {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		//response.setContentType("text/html;charset=UTF-8");// jQuery返回json時需要注釋掉
		
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
	
	// 文件上傳
	public void fileUpload() {
		// 從配置文件中獲取文件上傳的保存路徑
		file_save_path = PropertiesUtil.getPropertyValue("FileUploadPath.properties", "path");
		
		try {
			File filepathS = new File(file_save_path);
			if (!filepathS.exists()) {// 判断文件路径是否存在，不存在就创建
				filepathS.mkdir();
			}
			FileInputStream inputStream = new FileInputStream(file);
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");// 设置日期格式
			file_save_name = "(" + df.format(new Date()).toString() + ")" + file_origin_name;
			FileOutputStream outputStream = new FileOutputStream(file_save_path + "\\" + file_save_name);
			byte[] buf = new byte[1024];
			int length = 0;
			while ((length = inputStream.read(buf)) != -1) {
				outputStream.write(buf, 0, length);
			}
			inputStream.close();
			outputStream.flush();
			outputStream.close();
			result = "1";
		} catch (Exception e) {
			result = "0";
			e.printStackTrace();
		}

		jsonObject.put("result", result); // 0失敗，1成功
		jsonObject.put("file_origin_name", file_origin_name);// 文件原始名稱
		jsonObject.put("file_save_name", file_save_name);// 文件保存名稱
		jsonObject.put("file_save_path", file_save_path);// 文件保存路徑
		sendJson(jsonObject);
	}
	
	// 文件刪除
	public void fileDelete() {
		File delFile = new File(file_save_path + "/" + file_save_name);
		
		try {
			if(delFile.exists()) {
				delFile.delete();
			}
			result = "1";
		} catch (Exception e) {
			result = "0";
			e.printStackTrace();
		}
		
		jsonObject.put("result", result);
		sendJson(jsonObject);
	}

	public FileUploadAction() {
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

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFile_origin_name() {
		return file_origin_name;
	}

	public void setFile_origin_name(String file_origin_name) {
		this.file_origin_name = file_origin_name;
	}

	public String getFile_save_name() {
		return file_save_name;
	}

	public void setFile_save_name(String file_save_name) {
		this.file_save_name = file_save_name;
	}

	public String getFile_save_path() {
		return file_save_path;
	}

	public void setFile_save_path(String file_save_path) {
		this.file_save_path = file_save_path;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}


