package com.powernode.mybatis.mapper;

import com.powernode.mybatis.pojo.Student;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface StudentMapper {

    /**
     * 保存學生訊息，通過 POJO 參數，Student 是單個參數，但是不是簡單類型
     * @param student 學生
     * @return int
     */
    int insertStudentByPOJO(Student student);

    /**
     * 保存學生訊息，通過 Map 參數。以下是單個參數，但是參數的類型不是簡單類型。是 Map 集合。
     * @param map Map 集合
     * @return int
     */
    int insertStudentByMap(Map<String, Object> map);

    /**
     * 當介面中的方法的的參數只有一個（單個參數），並且參數的資料類型都是簡單類型。
     * 根據 id 查詢、name 查詢、birth 查詢、sex 查詢
     */
    List<Student> selectById(Integer id);
    List<Student> selectByName(String name);
    List<Student> selectByBirth(Date birth);
    List<Student> selectBySex(Character sex);
}
