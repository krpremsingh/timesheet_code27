package com.awcsoftware.mybatis;

import java.io.IOException;
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
		int content;
		try {
			System.out.println("***********************Pratik**************************");
		while ((content = inputStream.read()) != -1) {
			System.out.print((char)content);
		}
		System.out.println("***********************Pratik**************************");
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
	}

	public static SqlSessionFactory getSessionFactory() {
		return sqlSessionFactory;
	}
	
	public static SqlSession openSession () {
		return getSessionFactory().openSession();
	}
}
