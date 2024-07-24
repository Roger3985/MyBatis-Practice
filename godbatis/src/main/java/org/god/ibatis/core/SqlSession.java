package org.god.ibatis.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 專門負責執行 SQL 語句的會話物件。
 * @author Roger
 * @version 1.0
 * @since 1.0
 */
public class SqlSession {

    private SqlSessionFactory factory;

    public SqlSession(SqlSessionFactory factory) {
        this.factory = factory;
    }

    /**
     * 執行 insert 語句，向資料庫表當中插入紀錄。
     * @param sqlId sql 語句的 id。
     * @param pojo 插入的資料。
     * @return 插入的數量。
     */
    public int insert(String sqlId, Object pojo) {
        int count = 0;
        try {
            // JDBC 程式碼，執行 insert 語句，完成插入操作。
            Connection connection = factory.getTransaction().getConnection();
            // insert into user values(#{id}. #{name}, #{age})
            String godbatisSql = factory.getMappedStatementMaps().get(sqlId).getSql();
            // insert into user values(?, ?, ?) varchar
            String sql = godbatisSql.replaceAll("#\\{[a-zA-Z0-9_$]*}", "?");
            PreparedStatement ps = connection.prepareStatement(sql);
            // 給 ? 占位符傳值
            // 難度是什麼：
            // 第一：你不知道有多少個 ? (占位符)
            // 第二：你不知道該將 pojo 物件中的哪個屬性賦值給哪個 ?
            // ps.String(第幾個問號，傳什麼值) // 這裡都是 setString，所以資料庫表中的字段類型要求都是 varchar 才行，這是 godbatis 比較失敗的地方。
            int fromIndex = 0;
            int index = 1; // 問號的下標
            while (true) {
                int jingIndex = godbatisSql.indexOf("#", fromIndex);
                if (jingIndex < 0) {
                    break;
                }
                int youkuoHaoIndex = godbatisSql.indexOf("}", fromIndex);
                String propertyName = godbatisSql.substring(jingIndex + 2, youkuoHaoIndex).trim();
                fromIndex = youkuoHaoIndex + 1;
                // 有屬性名 id，怎麼獲取 id 的屬性值呢？調用 getId() 方法
                String getMethodName  = "get" + propertyName.toUpperCase().charAt(0) + propertyName.substring(1);
                Method getMethod = null;
                try {
                    getMethod = pojo.getClass().getDeclaredMethod(getMethodName);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                Object propertyValue = null;
                try {
                    propertyValue = getMethod.invoke(pojo);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                ps.setString(index, propertyValue.toString());
                index++;
            }

            // 執行 SQL 語句
            count = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    // selectOne


    // 局部測試
    public static void main(String[] args) {
        String sql = "insert into user values(#{id}, #{name}, #{age})";
        int fromIndex = 0;
        int index = 1; // 問號的下標
        while (true) {
            int jingIndex = sql.indexOf("#", fromIndex);
            if (jingIndex < 0) {
                break;
            }
            System.out.println(index);
            index++;
            int youkuoHaoIndex = sql.indexOf("}", fromIndex);
            String propertyName = sql.substring(jingIndex + 2, youkuoHaoIndex).trim();
            System.out.println(propertyName);
            fromIndex = youkuoHaoIndex + 1;
        }
    }


    /**
     * 提交交易。
     */
    public void commit() {
        factory.getTransaction().commit();
    }

    /**
     * 回滾交易。
     */
    public void rollback() {
        factory.getTransaction().rollback();
    }

    /**
     * 關閉交易。
     */
    public void close() {
        factory.getTransaction().close();
    }
}
