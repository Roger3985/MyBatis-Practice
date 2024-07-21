package com.powernode.mybatis.test;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;

public class ConfigurationTest {

    @Test
    public void testDataSource() throws IOException {
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        // sqlSessionFactory 物件：一個資料一個。不要頻繁創建物件。
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"));
        // 通過 sqlSessionFactory 物件可以開啟多個會話。
//        // 會話 1
//        SqlSession sqlSession1 = sqlSessionFactory.openSession();
//        sqlSession1.insert("insertCar");
//        sqlSession1.commit();
//        sqlSession1.close(); // 因為要測試連接池的效果：關閉是需要的，要不然測不出來。
//
//        // 會話 2
//        SqlSession sqlSession2 = sqlSessionFactory.openSession();
//        sqlSession2.insert("insertCar");
//        sqlSession2.commit();
//        sqlSession2.close();

        for (int i = 0; i < 4; i++) {
            SqlSession sqlSession = sqlSessionFactory.openSession();
            sqlSession.insert("insertCar");
            // 不要關閉
        }
    }

    @Test
    public void testEnvironment() throws IOException {
        // 獲取 SqlSessionFactory 物件 (採用默認的方式獲取)
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();

        // 這種方式就是獲取的默認環境
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"));
        SqlSession sqlSession = sqlSessionFactory.openSession();
        int count = sqlSession.insert("car.insertCar");
        sqlSession.commit();
        sqlSession.close();

        // 這種方式就是通過環境 id 來使用指定的環境
        SqlSessionFactory sqlSessionFactory1 = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"), "powernodeDB");
        SqlSession sqlSession1 = sqlSessionFactory1.openSession();
        // 執行 SQL 語句
        sqlSession1.insert("car.insertCar");
        sqlSession1.commit();
        sqlSession1.close();
    }
}
