package com.powernode.mybatis.test;

import com.powernode.mybatis.mapper.CarMapper;
import com.powernode.mybatis.pojo.Car;
import com.powernode.mybatis.pojo.CarExample;
import com.powernode.mybatis.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

public class CarMapperTest {

    /**
     * CarExample 類負責封裝查詢條件的。
     */
    @Test
    public void testSelect() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        // 執行查詢
        // 1. 查詢一個
        Car car = mapper.selectByPrimaryKey(1);
        System.out.println(car);
        // 2. 查詢所有（selectByExample，根據條件查詢，如果條件是 null 表示沒有條件。）
        List<Car> cars = mapper.selectByExample(null);
        cars.forEach(car1 -> System.out.println(car1));
        System.out.println("============================================");
        // 3. 按照條件進行查詢
        // QBC 風格：通過 Query By Criteria 一種查詢方式：比較物件導向，看不到 sql 語句。
        // 封裝條件，通過 CarExample 物件來封裝查詢條件
        CarExample carExample = new CarExample();
        // 調用 carExample.createCriteria() 方法來創造查詢條件
        carExample.createCriteria()
                    .andBrandLike("帕薩特")
                    .andGuidePriceGreaterThan(new BigDecimal(20.0));
        // 添加 or
        carExample.or().andCarTypeEqualTo("燃油車");
        // 執行查詢
        List<Car> cars2 = mapper.selectByExample(carExample);
        cars2.forEach(car2 -> System.out.println(car2));

        sqlSession.close();
    }
}
