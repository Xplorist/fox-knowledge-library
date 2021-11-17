package com.foxconn.SpringMVCx.EBOM.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.foxconn.SpringMVCx.EBOM.service.EBOM_Service;

/**
  *
  * @author C3005579
  * @date 2019年5月22日 上午10:43:07 
  */
@Controller
@RequestMapping("/ebom_excel")
public class ExcelDownloadController {
	@Resource
	private EBOM_Service ebomService;
	
	@RequestMapping("/download.x")
    public void downloadExcel(HttpServletRequest request, HttpServletResponse response) {
		String pkid = request.getParameter("pkid");
		
        try {
        	Map<String, Object> map = ebomService.generateExcel(pkid, request);
        	HSSFWorkbook workbook = (HSSFWorkbook)map.get("workbook");
        	String title = (String)map.get("title");
        	
        	response.setContentType("application/msexcel;charset=UTF-8");
        	response.addHeader("Content-Disposition", "attachment; filename=" + new String((title + "BOM.xls").getBytes("utf-8"), "ISO8859-1"));
	    	OutputStream out = response.getOutputStream();  
	    	
	    	workbook.write(out);  
	        out.flush();  
	        out.close();  
	        workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
