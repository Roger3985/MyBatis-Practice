package com.powernode.mybatis.test;

import com.powernode.mybatis.mapper.CarMapper;
import com.powernode.mybatis.pojo.Car;
import com.powernode.mybatis.utils.SqlSessionUtil;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

public class CarMapperTest {

    @Test
    public void testSelectById2() throws Exception {
        // 這裡只有一個 SqlSessionFactory 物件。二級緩存對應的就是 SqlSessionFactory。
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("mybatis-config.xml"));
        SqlSession sqlSession1 = sqlSessionFactory.openSession();
        SqlSession sqlSession2 = sqlSessionFactory.openSession();
        CarMapper mapper1 = sqlSession1.getMapper(CarMapper.class);
        CarMapper mapper2 = sqlSession2.getMapper(CarMapper.class);

        // 這行程式碼執行結束之後，實際上資料是緩存到一級緩存當中了。(存到 sqlSession1 中，此是一級緩存。)
        Car car1 = mapper1.selectById2(1);
        System.out.println(car1);

        // 如果這裡不關閉 SqlSession1 物件的話，二級緩存中還是沒有資料的。

        // (測試）程式執行到這裡得時候，會將 sqlSession1 這個一級緩存中的資料寫入到二級緩存當中。
        // sqlSession1.close();

        // 這行程式碼執行結束之後，實際上的資料會緩存到一級緩存當中。(存到 sqlSession2 中，此是一級緩存。)
        Car car2 = mapper2.selectById2(1);
        System.out.println(car2);

        // 程式執行到這裡得時候，會將 sqlSession1 這個一級緩存中的資料寫入到二級緩存當中。
        sqlSession1.close();
        // 程式執行到這裡得時候，會將 sqlSession2 這個一級緩存中的資料寫入到二級緩存當中。
        sqlSession2.close();

    }

    /*
        思考：什麼時候不走緩存？
        Ans:
        (1) SqlSession 物件不是同一個，肯定不走緩存。
        (2) 查詢條件不一樣，肯定不走緩存。

        思考：什麼時候一級緩存失效？
        第一次 DQL 和 第二次 DQL 之間你做了以下兩件事中的任何一件，都會讓一級緩存清空：
        (1) 執行了 sqlSession 的 clearCache() 方法，這是手動清空緩存。
        (2) 執行了 INSERT 或 DELETE 或 UPDATE 語句。 不管你是操控哪張表的，都會清空一級緩存。
     */
    @Test
    public void testSelectById() {
        SqlSession sqlSession = SqlSessionUtil.openSession();

        CarMapper mapper1 = sqlSession.getMapper(CarMapper.class);
        Car car1 =  mapper1.selectById(1);
        System.out.println(car1);

        // 手動清空一級緩存
        sqlSession.clearCache();

        // 在這裡執行了 INSERT DELETE UPDATE 中的任意一個語句。並且和表沒有關係。
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        mapper.insertClazz(2000, "高三三班");

        CarMapper mapper2 = sqlSession.getMapper(CarMapper.class);
        Car car2 =  mapper2.selectById(1);
        System.out.println(car2);

        sqlSession.commit();
        sqlSession.close();
    }
}
