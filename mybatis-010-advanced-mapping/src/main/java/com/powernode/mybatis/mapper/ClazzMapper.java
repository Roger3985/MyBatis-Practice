package com.powernode.mybatis.mapper;

import com.powernode.mybatis.pojo.Clazz;

public interface ClazzMapper {

    /**
     * 分步查詢。第一步：根據班級編號獲取班級信息。
     * @param cid 班級編號。
     * @return Clazz 班級。
     */
    Clazz selectByStep1(Integer cid);

    /**
     *  根據班級編號查詢班級信息。
     * @param cid 班級主鍵。
     * @return Clazz 班級。
     */
    Clazz selectByCollection(Integer cid);

    /**
     * 分步查詢第二步：根據 id 獲取班級信息。
     * @param cid clazz table 主鍵。
     * @return Clazz 班級。
     */
    Clazz selectByIdStep2(Integer cid);
}
