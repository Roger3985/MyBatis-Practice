package com.powernode.mybatis.mapper;

import com.powernode.mybatis.pojo.Clazz;

public interface ClazzMapper {

    /**
     * 分步查詢第二步：根據 id 獲取班級信息。
     * @param cid clazz table 主鍵。
     * @return Clazz 班級
     */
    Clazz selectByIdStep2(Integer cid);
}
