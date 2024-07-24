package org.god.ibatis.core;

import java.sql.Connection;

/**
 * 交易管理器介面。
 * 所有的交易管理器都應該遵循該規範。
 * JDBC 交易管理器，MANAGED 交易管理器等都應該實現這個介面。
 * Transaction 交易管理器：提供管理交易方法。
 * @author Roger
 * @version 1.0
 * @since 1.0
 */
public interface Transaction {
    /**
     * 提交交易
     */
    void commit();

    /**
     * 回滾交易
     */
    void rollback();

    /**
     * 關閉交易
     */
    void close();

    /**
     * 還需要其他方法嗎？...後續再說。...
     */

    /**
     * 真正的開啟資料庫連接。
     */
    void openConnection();

    /**
     * 獲取資料庫連接物件
     */
    Connection getConnection();
}
