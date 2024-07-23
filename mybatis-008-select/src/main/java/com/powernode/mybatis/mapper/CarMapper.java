package com.powernode.mybatis.mapper;

import com.powernode.mybatis.pojo.Car;

import java.util.List;
import java.util.Map;

public interface CarMapper {

    /**
     * 查詢所有的 Car 信息，返回一個存放 Map 集合的 List 集合。
     * @return
     */
    List<Map<String, Object>> selectAllRetListMap();

    /**
     * 根據 id 獲取汽車信息，將汽車信息放到 Map 集合中
     *  id | car_num |  brand   | guide_price | produce_time | car_type
     * ----+---------+----------+-------------+--------------+----------
     *   2 | 1001    | 豐田霸道 |       30.00 | 2000-10-11   | 燃油車
     *  Map<String, Object>
     *      k             v
     *   --------------------
     *     "id"           2
     *     "car_num"      1001
     *     "brand"        豐田霸道
     *     ......
     *
     * @return Map
     */
    Map<String, Object> selectByIdRetMap(Integer id);

    /**
     * 根據 id 查詢 car，這個 id 是主鍵，這個結果是一條，不可能是多條資料，那麼這種情況可以使用 List 集合接收嗎？
     * @param id 主鍵
     * @return List 集合
     */
    List<Car> selectById2(Integer id);

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
