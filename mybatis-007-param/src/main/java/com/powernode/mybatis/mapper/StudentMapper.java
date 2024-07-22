package com.powernode.mybatis.mapper;

import com.powernode.mybatis.pojo.Student;

import java.util.Date;
import java.util.List;

public interface StudentMapper {
    /**
     * 當介面中的方法的的參數只有一個（單個參數），並且參數的資料類型都是簡單類型。
     * 根據 id 查詢、name 查詢、birth 查詢、sex 查詢
     */
    List<Student> selectById(Integer id);
    List<Student> selectByName(String name);
    List<Student> selectByBirth(Date birth);
    List<Student> selectBySex(Character sex);
}
