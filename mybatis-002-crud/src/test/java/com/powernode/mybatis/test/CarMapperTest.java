package com.powernode.mybatis.test;

import com.powernode.mybatis.pojo.Car;
import com.powernode.mybatis.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class CarMapperTest {

    @Test
    public void testInsertCarByPOJO() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        // 封裝資料
        Car car = new Car(null, "3333", "比亞迪秦", 30.0, "2020-11-11", "新能源");
        // 執行 SQL
        int count = sqlSession.insert("insertCar", car); // ORM
        System.out.println(count);
        sqlSession.commit();
        sqlSession.close();
    }


    @Test
    public void testInsertCar() {
        SqlSession sqlSession = SqlSessionUtil.openSession();

        // 前端傳過來資料
        // 這個物件我們先使用 Map 集合進行資料的封裝
        Map<String, Object> map = new HashMap<>();
        map.put("carNum", "1111");
        map.put("brand", "比亞迪汗");
        map.put("guidePrice", 10.0);
        map.put("produceTime", "2020-11-11");
        map.put("carType", "電車");

        // 執行 SQL 語句
        /*
            insert 方法的參數：
            (1) 第一個參數： sqlId，從 CarMapper.xml 文件中複製
            (2) 第二個參數： 封裝資料的物件
         */
        int count = sqlSession.insert("insertCar", map);
        System.out.println(count);

        sqlSession.commit();
        sqlSession.close();

    }
}
