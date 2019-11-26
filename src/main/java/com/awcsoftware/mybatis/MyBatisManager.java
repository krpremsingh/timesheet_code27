package com.awcsoftware.mybatis;

import java.io.InputStream;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;

public class MyBatisManager {
	static Logger log = Logger.getLogger(MyBatisManager.class.getName());
	protected static SqlSessionFactory sqlSessionFactory;

	static {
		log.info("read mybatis config");
		InputStream inputStream = MyBatisManager.class.getClassLoader().getResourceAsStream("mybatis-config.xml");
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		log.info("end static block");
	}

	public static SqlSessionFactory getSessionFactory() {
		return sqlSessionFactory;
	}

	public static SqlSession openSession() {
		return getSessionFactory().openSession();
	}
}
