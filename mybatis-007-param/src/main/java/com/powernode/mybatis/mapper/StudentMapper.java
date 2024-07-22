package com.powernode.mybatis.mapper;

import com.powernode.mybatis.pojo.Student;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface StudentMapper {

    /**
     * Param 註解
     * mybatis 框架底層的實現原理
     * @param name 姓名
     * @param sex  性別
     * @return List
     */
    List<Student> selectByNameAndSex2(@Param("name") String name, @Param("sex") Character sex);

    /*
        注意：低版本的 mybatis 中，使用的是: #{0} and #{1},  以及 #{2}...
        高版本的 mybatis WHERE name=#{selected};

     */


    /**
     * 這是多參數。
     * 根據 name 和 sex 查詢 Student 訊息。
     * 如果是多個參數的話，mybatis 框架是怎麼做的呢？
     *      mybatis 框架為自動創建一個 Map 集合。並且 Map 集合是以這種方式儲存參數的。
     *          map.put("arg0", name);
     *          map.put("arg1", sex);
     *          map.put("param1", name);
     *          map.put("param2", sex);
     *
     * @param name 姓名
     * @param sex 性別
     * @return int
     */
    List<Student> selectByNameAndSex(String name, Character sex);

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
