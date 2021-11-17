package com.partsMeasurePage.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * SqlSessionFactory工具類
 * @author C3005579
 * @date 2018年9月8日 下午2:28:02 
 */
public class SqlSessionFactoryUtil {
	private static SqlSessionFactory sqlSessionFactory = null;
	private static final Class<SqlSessionFactoryUtil> CLASS_LOCK = SqlSessionFactoryUtil.class;
	
	private SqlSessionFactoryUtil() {
		super();
	}
	
	private static SqlSessionFactory initSqlSessionFactory() {
		try {
			String resource = "com/partsMeasurePage/config/myBatis-config.xml";
			InputStream inputStream = Resources.getResourceAsStream(resource);
			synchronized (CLASS_LOCK) {// 線程鎖
				if(sqlSessionFactory == null) {
					sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return sqlSessionFactory;
	}
	
	public static SqlSession openSqlSession() {
		if(sqlSessionFactory == null) {
			initSqlSessionFactory();
		}
		
		return sqlSessionFactory.openSession();
	}
}


