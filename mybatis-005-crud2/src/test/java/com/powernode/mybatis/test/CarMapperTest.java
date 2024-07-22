package com.powernode.mybatis.test;

import com.powernode.mybatis.mapper.CarMapper;
import com.powernode.mybatis.pojo.Car;
import com.powernode.mybatis.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class CarMapperTest {

    @Test
    public void testInsert() {
        try (SqlSession sqlSession = SqlSessionUtil.openSession()) {
            CarMapper mapper = sqlSession.getMapper(CarMapper.class);
            Car car = new Car(null, "8654", "凱美瑞", 3.0, "2000-11-11", "新能源");
            int count = mapper.insert(car);
            System.out.println("Rows affected: " + count);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDeleteById() {
        try (SqlSession sqlSession = SqlSessionUtil.openSession()) {
            CarMapper mapper = sqlSession.getMapper(CarMapper.class);
            int count = mapper.deleteById(1);
            System.out.println("Rows deleted: " + count);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUpdate() {
        try (SqlSession sqlSession = SqlSessionUtil.openSession()) {
            CarMapper mapper = sqlSession.getMapper(CarMapper.class);
            Car car = new Car(2, "8655", "凱美瑞1", 3.0, "2000-11-11", "新能源");
            int count = mapper.update(car);
            System.out.println("Rows updated: " + count);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSelectById() {
        try (SqlSession sqlSession = SqlSessionUtil.openSession()) {
            CarMapper mapper = sqlSession.getMapper(CarMapper.class);
            Car car = mapper.selectById(2);
            System.out.println("Rows name: " + car.getBrand());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSelectAll() {
        try (SqlSession sqlSession = SqlSessionUtil.openSession()) {
            CarMapper mapper = sqlSession.getMapper(CarMapper.class);
            List<Car> cars = mapper.selectAll();
            cars.forEach(car -> System.out.println(car.getBrand()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
