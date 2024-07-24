package org.god.ibatis.core;

import java.util.Map;

/**
 * SqlSessionFactory 物件：
 *      (1) 一個資料庫對應一個 SqlSessionFactory 物件。
 *      (2) 通過 SqlSessionFactory 物件可以獲取 SqlSession 物件。（開啟會話）
 *      (3) 一個 SqlSessionFactory 物件可以開啟多個 SqlSession 會話。
 * @author Roger
 * @version 1.0
 * @since 1.0
 */
public class SqlSessionFactory {
    // 思考：SqlSessionFactory 中應該要定義哪些屬性？

    /**
     * 交易管理器屬性。
     * 交易管理器是可以靈活切換的。
     * SqlSessionFactory 類中的交易管理器應該是物件導向編程。
     * SqlSessionFactory 類中應該有一個交易管理器介面。
     */
    private Transaction transaction;

    /**
     * 存放 sql 語句的 Map 集合。
     * key 是 sqlId。
     * value 是對應的 SQL 標籤的信息物件。
     */
    private Map<String, MappedStatement> mappedStatementMaps;

    public SqlSessionFactory() {
    }

    public SqlSessionFactory(Transaction transaction, Map<String, MappedStatement> mappedStatementMaps) {
        this.transaction = transaction;
        this.mappedStatementMaps = mappedStatementMaps;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public Map<String, MappedStatement> getMappedStatementMaps() {
        return mappedStatementMaps;
    }

    public void setMappedStatementMaps(Map<String, MappedStatement> mappedStatementMaps) {
        this.mappedStatementMaps = mappedStatementMaps;
    }

    @Override
    public String toString() {
        return "SqlSessionFactory{" +
                "transaction=" + transaction +
                ", mappedStatementMaps=" + mappedStatementMaps +
                '}';
    }
}
