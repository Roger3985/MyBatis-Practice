package com.powernode.mybatis.test;

import com.powernode.mybatis.mapper.ClazzMapper;
import com.powernode.mybatis.pojo.Clazz;
import com.powernode.mybatis.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

public class ClazzMapperTest {

    @Test
    public void testSelectByStep1() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        ClazzMapper mapper = sqlSession.getMapper(ClazzMapper.class);
        Clazz clazz = mapper.selectByStep1(1000);
        // System.out.println(clazz);

        // 只訪問班級名字。
        System.out.println(clazz.getCname());

        // 只有用到的時候才會去執行第二步 SQL（因為目前已經利用 mybatis-config.xml 配置檔案開啟了全局延遲查詢。）
        // System.out.println(clazz.getStus());

        sqlSession.close();
    }

    @Test
    public void testSelectByCollection() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        ClazzMapper mapper = sqlSession.getMapper(ClazzMapper.class);
        Clazz clazz = mapper.selectByCollection(1000);
        System.out.println(clazz);
        sqlSession.close();
    }
}
