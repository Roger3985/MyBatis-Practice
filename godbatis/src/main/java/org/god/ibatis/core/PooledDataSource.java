package org.god.ibatis.core;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

/**
 * 資料源的實現類：POOLED
 * 使用 godbatis 框架內置的資料庫連線池來獲取 Connection 物件。（這個就不實現了）
 * @author Roger
 * @version 1.0
 * @since 1.0
 */
public class PooledDataSource implements javax.sql.DataSource {
    @Override
    public Connection getConnection() throws SQLException {
        // 這個連線池 godbatis 框架可以自己寫一個連線池。
        // 從資料庫連線池當中獲取 Connection 物件的。（這個資料庫連線池是我 godbatis 框架內部封裝好的。）
        return null;
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return null;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {

    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }
}
