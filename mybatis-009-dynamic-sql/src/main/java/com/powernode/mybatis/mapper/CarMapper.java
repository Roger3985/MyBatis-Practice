package com.powernode.mybatis.mapper;

import com.powernode.mybatis.pojo.Car;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CarMapper {

    /**
     * 批量插入，一次插入多條信息
     * @param cars 多條 car 信息
     * @return int
     */
    int insertBatch(@Param("cars") List<Car> cars);

    /**
     * 批量刪除：foreach 標籤
     * @param ids 複數 id
     * @return int
     */
    int deleteByIds(@Param("ids") Integer[] ids);

    /**
     * 使用 choose when otherwise 標籤。
     * @param brand 品排
     * @param guidePrice 指導價
     * @param carType 汽車類型
     * @return List
     */
    List<Car> selectByChoose(@Param("brand") String brand, @Param("guidePrice") Double guidePrice, @Param("carType") String carType);

    /**
     * 更新 set 標籤
     * @param car 汽車信息
     * @return int
     */
    int updateBySet(Car car);

    /**
     * 更新 Car
     * @param car 汽車的信息
     * @return int
     */
    int updateById(Car car);

    /**
     * 使用 trim 標籤。
     * @param brand 品排
     * @param guidePrice 指導價
     * @param carType 汽車類型
     * @return List
     */
    List<Car> selectByMultiConditionWithTrim(@Param("brand") String brand, @Param("guidePrice") Double guidePrice, @Param("carType") String carType);

    /**
     * 使用 where 標籤，讓 where 子句更加智能
     * @param brand 品排
     * @param guidePrice 指導價
     * @param carType 汽車類型
     * @return List
     */
    List<Car> selectByMultiConditionWithWhere(@Param("brand") String brand, @Param("guidePrice") Double guidePrice, @Param("carType") String carType);

    /**
     * 多條件查詢
     * @param brand 品排
     * @param guidePrice 指導價
     * @param carType 汽車類型
     * @return List
     */
    List<Car> selectByMultiCondition(@Param("brand") String brand, @Param("guidePrice") Double guidePrice, @Param("carType") String carType);

}
