package com.powernode.mybatis.mapper;

import com.powernode.mybatis.pojo.Car;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface CarMapper {

    /**
     * 根據 id 獲取 Car 信息。
     * @param id Car 主鍵。
     * @return Car 汽車信息。
     */
    Car selectById(Integer id);
}
