package com.powernode.mybatis.mapper;

import com.powernode.mybatis.pojo.Car;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CarMapper {

    /**
     * 測試二級緩存
     * @param id 汽車主鍵。
     * @return Car 汽車信息。
     */
    Car selectById2(Integer id);

    /**
     * 保存班級信息。
     * @param cid 班級編號。
     * @param cname 班級名稱。
     * @return 新增的數量。
     */
    int insertClazz(@Param("cid") Integer cid, @Param("cname") String cname);

    /**
     * 根據 id 獲取 Car 信息。
     * @param id Car 主鍵。
     * @return Car 汽車信息。
     */
    Car selectById(Integer id);
}
