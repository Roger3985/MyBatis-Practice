package com.powernode.mybatis.mapper;

import com.powernode.mybatis.pojo.Car;
import org.apache.ibatis.annotations.*;


@Mapper
public interface CarMapper {

    @Insert("INSERT INTO car(car_num, brand, guide_price, produce_time, car_type) " +
            "VALUES (#{carNum}, #{brand}, #{guidePrice}, #{produceTime}, #{carType})")
    int insert(Car car);

    @Delete("DELETE FROM car " +
            "WHERE id = #{id}")
    int deleteById(Integer id);

    @Update("UPDATE car " +
            "SET car_num=#{carNum}, brand=#{brand}, guide_price=#{guidePrice}, produce_time=#{produceTime}, car_type=#{carType}" +
            "WHERE id = #{id}")
    int update(Car car);

    @Select("SELECT * " +
            "FROM car " +
            "WHERE id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "carNum", column = "car_num"),
            @Result(property = "brand", column = "brand"),
            @Result(property = "guidePrice", column = "guide_price"),
            @Result(property = "produceTime", column = "produce_time"),
            @Result(property = "carType", column = "car_type")
    })
    Car selectById(Integer id);
}
