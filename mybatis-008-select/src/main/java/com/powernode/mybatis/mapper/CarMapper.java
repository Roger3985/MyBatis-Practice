package com.powernode.mybatis.mapper;

import com.powernode.mybatis.pojo.Car;

public interface CarMapper {

    /**
     * 根據 id 查詢 Car 信息
     * @param id 主鍵
     * @return Car 信息
     */
    Car selectById(Integer id);
}
