package com.powernode.mybatis.test;

import com.powernode.mybatis.mapper.CarMapper;
import com.powernode.mybatis.pojo.Car;
import com.powernode.mybatis.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

public class CarMapperTest {

    @Test
    public void testSelectById() {
        SqlSession sqlSession = SqlSessionUtil.openSession();

        CarMapper mapper1 = sqlSession.getMapper(CarMapper.class);
        Car car1 =  mapper1.selectById(1);
        System.out.println(car1);

        CarMapper mapper2 = sqlSession.getMapper(CarMapper.class);
        Car car2 =  mapper2.selectById(1);
        System.out.println(car2);

        sqlSession.close();
    }
}
