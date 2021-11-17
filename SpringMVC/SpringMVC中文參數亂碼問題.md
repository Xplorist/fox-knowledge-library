### SpringMVC中文參數亂碼問題

****

> 參考網址：
> 
> * [https://blog.csdn.net/a568078283/article/details/48523767](https://blog.csdn.net/a568078283/article/details/48523767)

****

完整代碼：

```java
/**
 * (文件描述)
 * @project_name ePDMWeb IPEG工管系統
 * @package_name com.foxconn.SpringMVCx.file.controller
 * @file_name FileController.java
 * @Copyright 資訊2011
 * @since 2020年1月9日
 */
package com.foxconn.SpringMVCx.file.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * (類型說明)
 * @author mcebgadmin calrey
 * @type FileController
 * @since 2020年1月9日
 */
@Controller
@RequestMapping("/file")
public class FileController {
	
	@RequestMapping(value = "/download.x", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
	public void download(@RequestParam String file_origin_name, @RequestParam String file_save_name, @RequestParam String file_save_path, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 參數非空校驗
		if (file_origin_name == null || "".equals(file_origin_name)
			|| file_save_name == null || "".equals(file_save_name)
			|| file_save_path == null || "".equals(file_save_path)) {
			throw new RuntimeException("下載文件的參數不能為空");
		}
		// 解決中文亂碼問題
		file_origin_name = new String(file_origin_name.getBytes("ISO-8859-1"), "UTF-8");
		file_save_name = new String(file_save_name.getBytes("ISO-8859-1"), "UTF-8");
		file_save_path = new String(file_save_path.getBytes("ISO-8859-1"), "UTF-8");
		
		InputStream inputStream = new FileInputStream(file_save_path + File.separator + file_save_name);
    	//response.setContentType("application/msexcel;charset=UTF-8");
    	response.addHeader("Content-Disposition", "attachment; filename=" + new String(file_origin_name.getBytes("UTF-8"), "ISO8859-1"));
    	OutputStream outputStream = response.getOutputStream(); 
		
		byte[] buf = new byte[1024];
		int length = 0;
		while ((length = inputStream.read(buf)) != -1) {
			outputStream.write(buf, 0, length);
		}
		
		inputStream.close();
		outputStream.flush();
		outputStream.close();
	}
}

```



****

核心代碼：

```
		// 解決中文亂碼問題
		file_origin_name = new String(file_origin_name.getBytes("ISO-8859-1"), "UTF-8");
		file_save_name = new String(file_save_name.getBytes("ISO-8859-1"), "UTF-8");
		file_save_path = new String(file_save_path.getBytes("ISO-8859-1"), "UTF-8");
```

****

.
