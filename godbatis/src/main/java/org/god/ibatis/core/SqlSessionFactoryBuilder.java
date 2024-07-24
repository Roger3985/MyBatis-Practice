package org.god.ibatis.core;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.Map;

/**
 * SqlSessionFactory 建構器物件
 * 通過 SqlSessionFactoryBuilder 的 build 方法解析
 * godbatis-config.xml 文件，然後創建 SqlSessionFactory 物件
 * @author Roger
 * @version 1.0
 * @since 1.0
 */
public class SqlSessionFactoryBuilder {

    /**
     * 無參數建構方法。
     */
    public SqlSessionFactoryBuilder() {}

    /**
     * 解析 godbatis-config.xml 文件，來建構 SqlSessionFactory 物件。
     * @param in 指向 godbatis-config.xml 文件的一個輸入流。
     * @return SqlSessionFactory 物件。
     */
    public SqlSessionFactory build(InputStream in) {
        SqlSessionFactory factory = null;
        try {
            // 解析 godbatis-config.xml 文件
            SAXReader reader = new SAXReader();
            Document document = reader.read(in);
            Element environments = (Element) document.selectSingleNode("/configuration/environments");
            String defaultId = environments.attributeValue("default");
            Element environment = (Element) document.selectSingleNode("/configuration/environments/environment[@id='" + defaultId + "']");
            Element transactionElt = environment.element("transactionManager");
            Element dataSourceElt = environment.element("dataSource");
            // 獲取資料源物件
            DataSource dataSource = getDataSource(dataSourceElt);
            // 獲取交易管理器
            Transaction transaction = getTransaction(transactionElt, dataSource);
            // 獲取 mappedStatements
            Map<String, MappedStatement> mappedStatementMap = null;
            // 解析完成之後，構建 SqlSessionFactory 物件。
            factory = new SqlSessionFactory(transaction, mappedStatementMap);
            return factory;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return factory;
    }

    /**
     * 獲取交易管理器的。
     * @param transactionElt 交易管理器標籤元素。
     * @param dataSource 資料源物件。
     * @return 交易管理器物件。
     */
    private Transaction getTransaction(Element transactionElt, DataSource dataSource) {
        return null;
    }

    /**
     * 獲取資料源物件。
     * @param dataSourceElt 資料源標籤元素。
     * @return 資料源物件。
     */
    private DataSource getDataSource(Element dataSourceElt) {
        return null;
    }
}
