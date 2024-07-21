package com.powernode.mybatis.test;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;

public class CarMapperTest {

    @Test
    public void testInsertCar() {
        // 編寫 mybatis 程序
        SqlSession sqlSession = null;
        try {
            SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
            SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"));
            sqlSession = sqlSessionFactory.openSession();
            // 執行 SQL 語句，處理相關的業務
            int count = sqlSession.insert("insertCar");
            System.out.println(count);
            // 執行到這裡，沒有發生任何的異常，提交交易，終止交易。
            sqlSession.commit();
        } catch (IOException e) {
            // 最好回滾交易
            if (sqlSession != null) {
                sqlSession.rollback();
            }
            e.printStackTrace();
        } finally {
            // 關閉會話（釋放資源）
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }
}
