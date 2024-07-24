package com.powernode.mybatis.mapper;

import com.powernode.mybatis.pojo.Car;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CarMapper {

    /**
     * 查詢所有的 Car，通過分頁查詢插件 PageHelper 完成。
     * @return List Car 集合。
     */
    List<Car> selectAll();

    /**
     * 分頁查詢。
     * @param startIndex 起始下標。
     * @param pageSize 每頁顯示的紀錄條數。
     * @return 分頁中查詢出來的 Car 的詳細記錄。
     */
    List<Car> selectByPage(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize);
}
