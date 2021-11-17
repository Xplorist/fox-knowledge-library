package com.partsMeasurePage.dao;

import org.apache.ibatis.session.SqlSession;

import com.partsMeasurePage.util.SqlSessionFactoryUtil;

/**
 *	基礎dao，作為具體業務dao的父類，封裝常用方法便於複用
 * @author C3005579
 * @date 2018年9月8日 下午3:57:25 
 */
public class BaseDao {
	protected SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
	
	public void initResoureces() {
		sqlSession = SqlSessionFactoryUtil.openSqlSession();
	}
	
	public void closeResources() {
		if(sqlSession != null) {
			sqlSession.close();
		}
	}

	public BaseDao() {
		super();
	}
}


