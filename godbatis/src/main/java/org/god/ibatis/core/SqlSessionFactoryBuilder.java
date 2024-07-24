package org.god.ibatis.core;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.god.ibatis.utils.Resources;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
            List<String> sqlMapperXMLPathList = new ArrayList<>();
            List<Node> nodes = document.selectNodes("//mapper"); // 獲取整個配置文件中所有的 mapper 標籤。
            nodes.forEach(node -> {
                Element mapper = (Element) node;
                String resource = mapper.attributeValue("resource");
                sqlMapperXMLPathList.add(resource);
            });
            // 獲取資料源物件
            DataSource dataSource = getDataSource(dataSourceElt);
            // 獲取交易管理器
            Transaction transaction = getTransaction(transactionElt, dataSource);
            // 獲取 mappedStatements
            Map<String, MappedStatement> mappedStatementMap = getMappedStatements(sqlMapperXMLPathList);
            // 解析完成之後，構建 SqlSessionFactory 物件。
            factory = new SqlSessionFactory(transaction, mappedStatementMap);
            return factory;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return factory;
    }

    /**
     * 解析所有的 SqlMapper.xml 文件，然後構建 Map 集合。
     * @param sqlMapperXMLPathList SqlMappers。
     * @return Map 集合。
     */
    private Map<String, MappedStatement> getMappedStatements(List<String> sqlMapperXMLPathList) {
        Map<String, MappedStatement> mappedStatements = new HashMap<>();
        sqlMapperXMLPathList.forEach(sqlMapperXMLPath -> {
            try {
                SAXReader reader = new SAXReader();
                Document document = reader.read(Resources.getResourceAsStream(sqlMapperXMLPath));
                Element mapper = (Element) document.selectSingleNode("mapper");
                String namespace = mapper.attributeValue("namespace");
                List<Element> elements = mapper.elements();
                elements.forEach(element -> {
                    String id = element.attributeValue("id");
                    // 這裡進行了 namespace 和 id 的拼接，生成最終的 sqlId
                    String sqlId = namespace + "." + id;

                    String resultType = element.attributeValue("resultType");
                    String sql = element.getTextTrim();
                    MappedStatement mappedStatement = new MappedStatement(sql, resultType);

                    mappedStatements.put(sqlId, mappedStatement);
                });
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
        return mappedStatements;
    }


    /**
     * 獲取交易管理器的。
     * @param transactionElt 交易管理器標籤元素。
     * @param dataSource 資料源物件。
     * @return 交易管理器物件。
     */
    private Transaction getTransaction(Element transactionElt, DataSource dataSource) {
        Transaction transaction = null;
        String type = transactionElt.attributeValue("type").trim().toUpperCase();
        // JDBC
        if (Const.JDBC_TRANSACTION.equals(type)) {
            transaction = new JdbcTransaction(dataSource, false); // 默認是開啟交易的，將來要手動提交的。
        }
        if (Const.MANAGED_TRANSACTION.equals(type)) {
            transaction = new ManagedTransaction();
        }
        return transaction;
    }

    /**
     * 獲取資料源物件。
     * @param dataSourceElt 資料源標籤元素。
     * @return 資料源物件。
     */
    private DataSource getDataSource(Element dataSourceElt) {
        Map<String, String> map = new HashMap<>();
        // 獲取所有的 property
        List<Element> propertyElts = dataSourceElt.elements("property");
        propertyElts.forEach(propertyElt -> {
            String name = propertyElt.attributeValue("name");
            String value = propertyElt.attributeValue("value");
            map.put(name, value);
        });

        DataSource dataSource = null;
        // UNPOOLED POOLED JNDI
        String type = dataSourceElt.attributeValue("type").trim().toUpperCase();
        if (Const.UN_POOLED_DATASOURCE.equals(type)) {
            dataSource = new UnPooledDataSource(map.get("driver"), map.get("url"), map.get("username"), map.get("password"));
        }
        if (Const.POOLED_DATASOURCE.equals(type)) {
            dataSource = new PooledDataSource();
        }
        if (Const.JNDI_DATASOURCE.equals(type)) {
            dataSource = new JNDIDataSource();
        }
        return dataSource;
    }
}
