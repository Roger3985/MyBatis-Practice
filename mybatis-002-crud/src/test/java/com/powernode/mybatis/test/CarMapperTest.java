package com.powernode.mybatis.test;

import com.powernode.mybatis.pojo.Car;
import com.powernode.mybatis.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarMapperTest {

    @Test
    public void testNamespace() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        // 正確的 namespace 寫法 是指 namespace.id
        List<Car> cars = sqlSession.selectList("ajflaj.selectAll");
        cars.forEach(car -> System.out.println(car));
        sqlSession.close();
    }

    @Test
    public void testSelectAll() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        // 執行 SQL 語句
        // List<Object> cars = sqlSession.selectList("selectAll");
        List<Car> cars = sqlSession.selectList("selectAll");
        // 遍歷
        cars.forEach(car -> System.out.println(car));
        sqlSession.close();
    }

    @Test
    public void testSelectById() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        // 執行 DQL 語句。查詢。根據 id 查詢。返回結果一定是一條
        // mybatis 底層執行了 select 語句之後，一定會返回一個結果集物件：ResultSet
        // JDBC 中叫做 ResultSet，接下來就是 mybatis 應該從 ResultSet 中取出資料，封裝 java 物件。
        Object car = sqlSession.selectOne("selectById", 3);
        System.out.println(car);
        sqlSession.close();
    }

    @Test
    public void testUpdateById() {
        SqlSession sqlSession = SqlSessionUtil.openSession();

        // 準備資料
        Car car = new Car(4, "9999", "凱美瑞", 30.3, "1999-11-10", "燃油車");

        // 執行 SQL 語句
        int count = sqlSession.update("updateById", car);
        System.out.println(count);

        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void testDeleteById() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        // 執行 SQL 語句
        int count = sqlSession.delete("deleteById", 24);
        System.out.println(count);
        sqlSession.commit();
        sqlSession.close();
    }

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
