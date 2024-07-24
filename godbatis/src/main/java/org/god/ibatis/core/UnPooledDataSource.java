package org.god.ibatis.core;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

/**
 * 資料源的實現類：UNPOOLED（重點實現這種方式）
 * 不使用連線池，每一次都新建 Connection 物件。
 * @author Roger
 * @version 1.0
 * @since 1.0
 */
public class UnPooledDataSource implements javax.sql.DataSource {

    private String url;
    private String username;
    private String password;

    /**
     * 創建一個資料源物件。
     * @param driver 註冊驅動。
     * @param url 註冊路徑。
     * @param username 使用者名稱。
     * @param password 使用者密碼。
     */
    public UnPooledDataSource(String driver, String url, String username, String password) {
        try {
            // 直接註冊驅動就可以了
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(url, username, password);
        return connection;
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
