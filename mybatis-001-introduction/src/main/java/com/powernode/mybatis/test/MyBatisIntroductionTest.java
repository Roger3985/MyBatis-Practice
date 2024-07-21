package com.powernode.mybatis.test;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;

public class MyBatisIntroductionTest {
    public static void main(String[] args) throws Exception {

        // 獲取 SqlSessionFactoryBuilder
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();

        // 獲取 SqlSessionFactory
        InputStream is = Resources.getResourceAsStream("mybatis-config.xml"); // Resources.getResourcesAsStream 默認就是從類的根路徑下開始查找資源。
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(is); // 一般情況下都是一個資料庫對應一個 SqlSessionFactory 物件。

        // 獲取 SqlSession 物件
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 執行 SQL 語句
        int count = sqlSession.insert("insertCar"); // 返回值是影響資料庫當中的記錄條數

        System.out.println("How many to insert: " + count);

        // sqlSession 物件是不支持自動提交的，需要手動提交
        sqlSession.commit();
    }
}
