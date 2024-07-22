package com.powernode.mybatis.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;

/**
 * MyBatis 工具類
 * @author Roger
 * @version 1.0
 * @since 1.0
 */
public class SqlSessionUtil {

    /*
        (1) 工具類的建構方法一般都是私有化的。
        (2) 工具類中的所有方法都是靜態的，直接採用類名即可，不需要 new 物件
        (3) 為了防止 new 物件，建構方法私有化。
     */
    private SqlSessionUtil() {}

    private static SqlSessionFactory sqlSessionFactory;

    /*
        類加載時執行
        SqlSessionUtil 工具類在進行第一次加載的時後，解析 mybatis-config.xml 文件。創建 SqlSessionFactory 物件
     */
    static {
        try {
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("mybatis-config.xml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /* public static SqlSession openSession() {
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        // SqlSessionFactory 物件：一個 SqlSessionFactory 對應一個 environment，一個 environment 通常是一個資料庫
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"));
        SqlSession sqlSession = sqlSessionFactory.openSession();
        return sqlSession;
    } */

    /**
     * 獲取會話物件
     * @return 會話物件
     */
    public static SqlSession openSession() {
        return sqlSessionFactory.openSession();
    }
}
