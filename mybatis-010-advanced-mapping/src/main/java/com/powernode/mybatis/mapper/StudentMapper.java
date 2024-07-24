package com.powernode.mybatis.mapper;

import com.powernode.mybatis.pojo.Student;

import java.util.List;

public interface StudentMapper {

    /**
     * 根據班級編號查詢學生信息。
     * @param cid 班級編號。
     * @return List 的學生集合。
     */
    List<Student> selectByCidStep2(Integer cid);

    /**
     * 分步查詢的第一步：先根據學生的 sid 查詢學生的信息。
     * @param id sid 主鍵。
     * @return student
     */
    Student selectByIdStep1(Integer id);

    /**
     * 一條 SQL 語句，association
     * @param id 主鍵
     * @return student
     */
    Student selectByIdAssociation(Integer id);

    /**
     * 根據 id 獲取學生信息。同時獲取學生關聯的班級信息。
     * @param id 學生的 id。
     * @return 學生物件，但是學生物件當中含有班級物件。
     */
    Student selectById(Integer id);
}
