package org.god.ibatis.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;

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

    /**
     * 執行查詢語句，返回一個物件，該方法只適合返回一條紀錄的 SQL 語句。
     * @param sqlId sql 語句的 id。
     * @param param 參數。
     * @return Object 物件。
     */
    public Object selectOne(String sqlId, Object param) {
        Object obj = null;
        try {
            Connection connection = factory.getTransaction().getConnection();
            MappedStatement mappedStatement = factory.getMappedStatementMaps().get(sqlId);
            // 這是要查詢的 DQL 語句。
            // select * from where id = #{id}
            String godbatisSql = mappedStatement.getSql();
            String sql = godbatisSql.replaceAll("#\\{[a-zA-Z0-9_$]*}", "?");
            PreparedStatement ps = connection.prepareStatement(sql);
            // 給占位符傳值
            ps.setString(1, param.toString());
            // 查詢返回結果集
            ResultSet rs = ps.executeQuery();
            // 要封裝的結果類型。
            String resultType = mappedStatement.getResultType(); // org.god.ibatis.pojo.User
            // 從結果集中取資料，封裝 java 物件
            if (rs.next()) {
                // 獲取 resultType 的 Class
                Class<?> resultTypeClass = Class.forName(resultType);
                // 調用無參數建構方法創建物件
                obj = resultTypeClass.newInstance(); // Object obj = new User();
                // 給 User 類的 id, name, age 屬性賦值
                // 給 obj 物件的哪個屬性賦哪個值
                /*
                    postgres> select * from t_user where id = 1;
                     id |   name   | age
                    ----+----------+-----
                      1 | zhangsan | 20
                    解決結果的關鍵：將查詢結果的列名作為屬性名。
                    列名是 id，那麼屬性名就是 id。
                    列名是 name，那麼屬性名就是 name。
                 */
                ResultSetMetaData rsmd = rs.getMetaData();
                int columnCount = rsmd.getColumnCount();
                for (int i = 0; i < columnCount; i++) {
                    String propertyName = rsmd.getColumnName(i + 1);
                    // 拼接方法名
                    String setMethodName = "set" + propertyName.toUpperCase().charAt(0) + propertyName.substring(1);
                    // 獲取 set 方法
                    Method setMethod = resultTypeClass.getDeclaredMethod(setMethodName, String.class);
                    // 調用 set 方法給物件 obj 屬性賦值
                    setMethod.invoke(obj, rs.getString(propertyName));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }


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
