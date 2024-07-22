package com.powernode.mybatis.mapper;

import com.powernode.mybatis.pojo.Car;

import java.util.List;

public interface CarMapper {

    /**
     * 批量刪除根據 id
     */
    int deleteBatch(String ids);

    /**
     * 根據汽車類型獲取汽車訊息。
     * @param carType 汽車類型
     * @return 收集到的汽車
     */
    List<Car> selectByCarType(String carType);
}
