package com.powernode.mybatis.test;

import com.powernode.mybatis.mapper.StudentMapper;
import com.powernode.mybatis.pojo.Student;
import com.powernode.mybatis.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

public class StudentMapperTest {

    @Test
    public void testSelectByStep1() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        Student student = mapper.selectByIdStep1(1);
        // System.out.println(student);

        // 只需要看學生的名字
        System.out.println(student.getSname());

        // 程式執行到這裡了，我想要看看班級的名字
        // System.out.println(student.getClazz().getCname());

        sqlSession.close();
    }

    @Test
    public void testSelectByIdAssociation() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        Student student = mapper.selectByIdAssociation(1);
        System.out.println(student);
        sqlSession.close();
    }

    @Test
    public void testSelectById() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        Student student = mapper.selectById(1);
        System.out.println(student);
        sqlSession.close();
    }
}
