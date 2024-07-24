package org.god.ibatis.core;

/**
 * 普通的 java 類。POJO，封裝了一個 SQL 標籤。
 * 一個 MappedStatement 物件對應一個 SQL 標籤。
 * 一個 SQL 標籤中的所有信息封裝到 MappedStatement 物件當中。
 * 物件導向編程思想。
 * @author Roger
 * @version 1.0
 * @since 1.0
 */
public class MappedStatement {
    /**
     * sql 語句。
     */
    private String sql;

    /**
     * 要封裝的結果集類型。有的時候 resultType 是 null。
     * 比如：insert delete update 語句的時候 resultType 是 null。
     * 只有當 sql 語句是 select 語句的時候才有值。
     */
    private String resultType;

    public MappedStatement() {
    }

    public MappedStatement(String sql, String resultType) {
        this.sql = sql;
        this.resultType = resultType;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    @Override
    public String toString() {
        return "MappedStatement{" +
                "sql='" + sql + '\'' +
                ", resultType='" + resultType + '\'' +
                '}';
    }
}
