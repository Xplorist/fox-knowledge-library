package com.partsMeasurePage.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *	properties文件工具類
 * @author C3005579
 * @date 2018年9月4日 下午1:18:00 
 */
public class PropertiesUtil {
	public static String getPropertyValue(String fileName, String propertyKey) {
		String propertyValue = ""; 
		try {
			InputStream inputStream = Class.forName("com.partsMeasurePage.util.PropertiesUtil").getResourceAsStream(fileName);
			Properties p = new Properties();
			p.load(inputStream);
			propertyValue = p.getProperty(propertyKey);
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		
		return propertyValue;
	}
}


