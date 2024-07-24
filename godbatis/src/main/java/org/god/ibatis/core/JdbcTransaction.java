package org.god.ibatis.core;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * JDBC 交易管理器。(godbatis 框架目前只對 JdbcTransaction 進行實現。)
 * @author Roger
 * @version 1.0
 * @since 1.0
 */
public class JdbcTransaction implements Transaction {

    /**
     * 資料源屬性
     * 經典的設計：物件導向編程。
     */
    private DataSource dataSource;

    /**
     * 自動提交標誌。
     * true 表示自動提交。
     * false 表示不採用自動提交。
     */
    private boolean autoCommit;

    /**
     * 連接物件。
     */
    private Connection connection;

    @Override
    public Connection getConnection() {
        return connection;
    }

    /**
     * 創建交易管理器物件。
     * @param dataSource 資料源。
     * @param autoCommit 自動提交。
     */
    public JdbcTransaction(DataSource dataSource, boolean autoCommit) {
        this.dataSource = dataSource;
        this.autoCommit = autoCommit;
    }

    @Override
    public void commit() {
        try {
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void rollback() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void openConnection() {
        if (connection == null) {
            try {
                connection = dataSource.getConnection();
                // 開啟交易
                connection.setAutoCommit(autoCommit);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
