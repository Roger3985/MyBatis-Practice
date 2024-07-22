package com.powernode.mybatis.mapper;



import com.powernode.mybatis.pojo.Car;

import java.util.List;

public interface CarMapper {

    /**
     * 新增 Car
     */
    int insert(Car car);

    /**
     * 根據 id 刪除汽車訊息
     */
    int deleteById(Integer id);

    /**
     * 修改汽車訊息
     */
    int update(Car car);

    /**
     * 根據 id 查詢汽車訊息
     */
    Car selectById(Integer id);

    /**
     * 獲取所有的汽車訊息。
     */
    List<Car> selectAll();
}
