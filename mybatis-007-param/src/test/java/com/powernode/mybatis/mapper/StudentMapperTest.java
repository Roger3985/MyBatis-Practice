package com.powernode.mybatis.mapper;

import com.powernode.mybatis.pojo.Student;
import com.powernode.mybatis.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 代理模式：
 *      1. 代理物件 鏈家
 *      2. 代理方法 找方法
 *      3. 目標物件 我
 *      4. 目標方法 找房子
 */
public class StudentMapperTest {

    @Test
    public void testSelectByNameAndSex2() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        // mapper 實際上指向了代理物件
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        // mapper 是代理物件
        // selectByNameAndSex2 是代理方法
        List<Student> students = mapper.selectByNameAndSex2("張飛", '男');
        students.forEach(student -> System.out.println(student));
        sqlSession.close();
    }

    @Test
    public void testSelectByNameAndSex() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        List<Student> students = mapper.selectByNameAndSex("張飛", '男');
        students.forEach(student -> System.out.println(student));
        sqlSession.close();
    }


    @Test
    public void testInsertStudentByPOJO() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);

        // POJO 物件
        Student student = new Student();
        student.setName("張飛");
        student.setAge(50);
        student.setSex('女');
        student.setBirth(new Date());
        student.setHeight(10.0);

        // 插入 POJO
        int count = mapper.insertStudentByPOJO(student);
        System.out.println(count);
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void testInsertStudentByMap() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        Map<String, Object> map = new HashMap<>();
        map.put("姓名", "趙六");
        map.put("年齡", 20);
        map.put("身高", 1.81);
        map.put("性別", '男');
        map.put("生日", new Date());
        int count = mapper.insertStudentByMap(map);
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void testSelectById() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        List<Student> students = mapper.selectById(1);
        students.forEach(student -> System.out.println(student));
        sqlSession.close();
    }

    @Test
    public void testSelectByName() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        List<Student> students = mapper.selectByName("Roger");
        students.forEach(student -> System.out.println(student));
        sqlSession.close();
    }

    // java.util.Date java.sql.Date 他們都是簡單類型。
    @Test
    public void testSelectByBirth() throws Exception {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date birth = sdf.parse("2022-11-11");
        List<Student> students = mapper.selectByBirth(birth);
        students.forEach(student -> System.out.println(student));
        sqlSession.close();
    }

    @Test
    public void testSelectBySex() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        Character sex = Character.valueOf('男');
        List<Student> students = mapper.selectBySex(sex);
        students.forEach(student -> System.out.println(student));
        sqlSession.close();
    }


}
