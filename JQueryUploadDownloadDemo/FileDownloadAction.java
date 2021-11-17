package com.partsMeasurePage.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;

import com.opensymphony.xwork2.ActionSupport;
import com.partsMeasurePage.util.PropertiesUtil;

/**
 *	文件下載action
 * @author C3005579
 * @date 2018年9月4日 上午10:21:01 
 */
public class FileDownloadAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private String fileName;
	private String filePath;
	
	// 文件下載
	public InputStream getDownfile() {
		InputStream inputStream = null;
		try {
			// 取得配置文件路徑
			//String filePath = PropertiesUtil.getPropertyValue("FileUploadPath.properties", "path");
			File filepathS = new File(filePath);
			if (!filepathS.exists()) {// 判断文件路径是否存在，不存在就创建
				filepathS.mkdir();
			}
			String downname = fileName;
			String name = downname.substring(downname.indexOf(")")+1, downname.length());
			fileName = URLEncoder.encode(name, "UTF-8");
			inputStream = new FileInputStream(filePath + "/" + downname);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return inputStream;
	}

	public FileDownloadAction() {
		super();
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}


