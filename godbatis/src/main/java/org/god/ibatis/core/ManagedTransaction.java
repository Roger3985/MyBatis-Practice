package org.god.ibatis.core;

import java.sql.Connection;

/**
 * MANAGED 交易管理器。(godbatis 對這個類就不再實現了，主要實現 JdbcTransaction 然後此以此類推。)
 * @author Roger
 * @version 1.0
 * @since 1.0
 */
public class ManagedTransaction implements Transaction {

    @Override
    public void commit() {

    }

    @Override
    public void rollback() {

    }

    @Override
    public void close() {

    }

    @Override
    public void openConnection() {

    }

    @Override
    public Connection getConnection() {
        return null;
    }
}
