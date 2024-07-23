package com.powernode.mybatis.test;

import com.powernode.mybatis.mapper.CarMapper;
import com.powernode.mybatis.pojo.Car;
import com.powernode.mybatis.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CarMapperTest {

    @Test
    public void testInsertBatch() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        Car car1 = new Car(null, "1200", "帕薩特1", 30.0, "2020-11-11", "燃油車");
        Car car2 = new Car(null,"1200", "帕薩特2", 30.0, "2020-11-11", "燃油車");
        Car car3 = new Car(null, "1200", "帕薩特3", 30.0, "2020-11-11", "燃油車");
        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);
        cars.add(car3);
        mapper.insertBatch(cars);
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void testDeleteByIds() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        Integer[] ids = {61, 62, 63};
        int count = mapper.deleteByIds(ids);
        System.out.println(count);
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void testSelectByChoose() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        // 三個條件都不為空
        // List<Car> cars = mapper.selectByChoose("豐田霸道", 1.0, "新能源");
        // 第一個條件為空
        // List<Car> cars = mapper.selectByChoose("", 1.0, "新能源");
        // 前兩的條件都為空
        // List<Car> cars = mapper.selectByChoose("", null, "新能源");
        // 全部為空
        List<Car> cars = mapper.selectByChoose("", null, "");
        cars.forEach(car -> System.out.println(car));
        sqlSession.close();
    }

    @Test
    public void testUpdateBySet() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        Car car = new Car(63, null, null, null, null, "燃油車1");
        Integer count = mapper.updateBySet(car);
        System.out.println(count);
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void testUpdateById() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        Car car = new Car(63, null, null, null, null, "燃油車");
        Integer count = mapper.updateById(car);
        System.out.println(count);
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void testSelectByMultiConditionWithTrim() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);

        // 假設三個條件都不為空
        // List<Car> cars = mapper.selectByMultiConditionWithTrim("比亞迪", 2.0, "新能源");

        // 假設三個條件都為空
        // List<Car> cars = mapper.selectByMultiConditionWithTrim("", null, "");

        // 假設後面兩個的條件不為空，第一個條件為空
        // List<Car> cars = mapper.selectByMultiConditionWithTrim("", 2.0, "新能源");

        // 假設第一個條件為空，後面兩個條件為空
        List<Car> cars = mapper.selectByMultiConditionWithTrim("比亞迪", null, "");

        cars.forEach(car -> System.out.println(car));
        sqlSession.close();
    }

    @Test
    public void testSelectByMultiConditionWithWhere() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);

        // 假設三個條件都不為空
        // List<Car> cars = mapper.selectByMultiConditionWithWhere("比亞迪", 2.0, "新能源");

        // 假設三個條件都為空
        // List<Car> cars = mapper.selectByMultiConditionWithWhere("", null, "");

        // 假設後面兩個的條件不為空，第一個條件為空
        // List<Car> cars = mapper.selectByMultiConditionWithWhere("", 2.0, "新能源");

        // 假設第一個條件為空，後面兩個條件為空
        List<Car> cars = mapper.selectByMultiConditionWithWhere("比亞迪", null, "");

        cars.forEach(car -> System.out.println(car));
        sqlSession.close();
    }

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
