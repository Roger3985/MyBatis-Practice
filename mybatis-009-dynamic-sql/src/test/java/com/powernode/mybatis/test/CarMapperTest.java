package com.powernode.mybatis.test;

import com.powernode.mybatis.mapper.CarMapper;
import com.powernode.mybatis.pojo.Car;
import com.powernode.mybatis.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class CarMapperTest {

    @Test
    public void testSelectByMultiCondition() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);

        // 假設三個條件都不為空
        // List<Car> cars = mapper.selectByMultiCondition("比亞迪", 2.0, "新能源");

        // 假設三個條件都為空
        // List<Car> cars = mapper.selectByMultiCondition("", null, "");

        // 假設後面兩個的條件不為空，第一個條件為空
        // List<Car> cars = mapper.selectByMultiCondition("", 2.0, "新能源");

        // 假設第一個條件為空，後面兩個條件為空
        List<Car> cars = mapper.selectByMultiCondition("比亞迪", null, "");

        cars.forEach(car -> System.out.println(car));
        sqlSession.close();
    }
}
