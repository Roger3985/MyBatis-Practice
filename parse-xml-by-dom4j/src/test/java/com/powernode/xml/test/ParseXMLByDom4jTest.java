package com.powernode.xml.test;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

public class ParseXMLByDom4jTest {

    @Test
    public void testParseSqlMapperXML() throws DocumentException {
        SAXReader reader = new SAXReader();
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("CarMapper.xml");
        Document document = reader.read(is);
        // 獲取 namespace
        String xpath = "/mapper";
        Element mapper = (Element) document.selectSingleNode(xpath);
        String namespace = mapper.attributeValue("namespace");
        // System.out.println(namespace);
        // 獲取 mapper 節點下所有的子節點
        List<Element> elements = mapper.elements();
        // 遍歷
        elements.forEach(element -> {
            // 獲取 sqlId
            String id = element.attributeValue("id");
            System.out.println(id);
            // 獲取 resultType
            String resultType = element.attributeValue("resultType"); // 沒有這個屬性的話，會自動返回 "null"。
            System.out.println(resultType);
            // 獲取標籤中的 sql 語句（表示獲取標籤中的文本內容，而且去除前後空白）
            String sql = element.getTextTrim();
            System.out.println(sql);
            // mybatis 封裝了 jdbc，早晚要執行帶有 ? 的 sql 語句。
            // 轉換
            String newSql = sql.replaceAll("#\\{[0-9A-Za-z_$}]*", "?"); // #{屬性名}
            System.out.println(newSql);
        });
    }

    @Test
    public void testParseMyBatisConfigXML() throws DocumentException {
        // 創建 SAXReader 物件
        SAXReader reader = new SAXReader();
        // 獲取輸入流
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("mybatis-config.xml");
        // 讀 XML 文件，返回 document 物件，document 物件是文檔物件，代表了整個 XML 文件。
        Document document = reader.read(is);
        // 獲取文檔中的根標籤
        // Element rootElt = document.getRootElement();
        // String rootEltName = rootElt.getName();
        // System.out.println(rootEltName);

        // 獲取 default 默認的環境 id
        // xpath 是做標籤路徑匹配的。能夠讓我們快速定位 XML 文件中的元素。
        // 以下的 xpath 代表了：從根下開始找 configuration 標籤，然後找 configuration 標籤下的子標籤 environments
        String xpath = "/configuration/environments";
        Element environments = (Element) document.selectSingleNode(xpath); // Element 是 Node 類的子類，方法更多，使用更便捷。
        // 獲取屬性的值
        String defaultEnvironmentId = environments.attributeValue("default");
        // System.out.println(defaultEnvironmentId);
        // 獲取具體的環境 environment
        xpath = "/configuration/environments/environment[@id='"+defaultEnvironmentId+"']";
        // System.out.println(xpath);
        Element environment = (Element) document.selectSingleNode(xpath);
        // 獲取 environment 節點下的 transactionManager 節點 (Element 的 element() 方法用來獲取孩子節點）
        Element transactionManager = environment.element("transactionManager");
        String transactionType = transactionManager.attributeValue("type");
        System.out.println("交易管理器的類型：" + transactionType);
        // 獲取 dataSource 節點
        Element dataSource = environment.element("dataSource");
        String dataSourceType = dataSource.attributeValue("type");
        System.out.println("資料源類型：" + dataSourceType);
        // 獲取 dataSource 節點下的所有子節點
        List<Element> propertyElts = dataSource.elements();
        // 遍歷
        propertyElts.forEach(propertyElt -> {
            String name = propertyElt.attributeValue("name");
            String value = propertyElt.attributeValue("value");
            System.out.println(name + ":" + value);
        });
        // 獲取所有的 mappers 標籤
        // 不想從根下開始獲取，你想從任意位置開始，獲取所有的某個標籤，xpath 該這樣寫
        xpath = "//mapper";
        List<Node> mappers = document.selectNodes(xpath);
        // 遍歷
        mappers.forEach(mapper -> {
            Element mapperElt = (Element) mapper;
            String resource = mapperElt.attributeValue("resource");
            System.out.println(resource);
        });
    }
}
