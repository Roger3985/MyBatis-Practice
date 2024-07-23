package com.powernode.mybatis.mapper;

import com.powernode.mybatis.pojo.Car;

import java.util.List;

public interface CarMapper {

    /**
     * 根據品牌進行模糊查詢。
     * 模糊查詢的結果可能會有多個，但是我採用一個 POJO 物件來接收會有問題嗎？
     * @param brand 品牌
     * @return Car
     */
    Car selectByBrandLike(String brand);

    /**
     * 獲取所有的 Car
     * @return List
     */
    List<Car> selectAll();

    /**
     * 根據 id 查詢 Car 信息
     * @param id 主鍵
     * @return Car 信息
     */
    Car selectById(Integer id);
}
