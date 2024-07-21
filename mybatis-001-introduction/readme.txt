開發我的第一個 MyBatis 程式
1. resources 目錄
   -> 放在這個目錄當中的，一般都是資源文件，配置文件。
   -> 直接放到 resources 目錄下的資源，等同於放到了類的根路徑。


2. 開發步驟
＊ 第一步：打包方式 jar

＊ 第二步：引入依賴
    - mybatis 依賴
    <!-- mybatis 依賴 -->
    <dependency>
        <groupId>org.mybatis</groupId>
        <artifactId>mybatis</artifactId>
        <version>3.5.16</version>
    </dependency>

    - postgres(or mysql) 驅動依賴
    <!-- postgres(driver) 依賴 -->
    <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
        <version>42.7.3</version>
    </dependency>
    OR
    <!-- mysql(driver) 依賴 -->
    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
        <version>9.0.0</version>
    </dependency>

* 第三步： 編寫 mybatis 核心配置文件： mybatis-config.xml
    -> 注意：
            第一：這個文件名不是必須叫做 mybatis-config.xml，可以用其他的名字。只是大家都是採用這個名字。
            第二：這個文件的位置也不是固定的，可以隨意，但一搬情況下，都會放到類的根路徑下。
    -> mybatis-config.xml 文件中的配置文件訊息不理解沒關係，先把連接資料庫的訊息修改如下即可。
       其他的別動。

＊ 第四步： 編寫 XxxxMapper.xml 文件
   -> 在這個配置文件當中編寫 SQL 語句。
   -> 注意：這個文件名也不是固定的，放的位子也不是固定的，叫做： CarMapper.xml
           我們把他暫時先放到類的根路徑下。

＊ 第五步：在 mybatis-config.xml 文件中指定 XxxxMapper.xml 文件的路徑。
   <mapper resource="CarMapper.xml"/>
   注意：resource 屬性會自動從類的根路徑下開始查找資源。

＊ 第六步：編寫 MyBatis 程式。（使用 mybatis 的類庫，編寫 mybatis 程式，連接資料庫，做基礎 CRUD。
   -> 在 MyBatis 當中，負責執行 SQL 語句的那個物件叫做什麼？
      SqlSession：
      (1) SqlSession 是專門用來執行 SQL 語句的，是一個 Java 程式和資料庫之間的一次會話。
      (2) 要想獲取 SqlSession 物件，需要先獲取 SqlSessionFactory 物件，通過 SqlSessionFactory 工廠來生產 SqlSession 物件。
   -> 怎麼獲取 SqlSessionFactory 物件呢？
      (1) 需要先獲取 SqlSessionFactoryBuilder 物件。
      (2) 通過 SqlSessionFactoryBuilder 物件的 build 方法，來獲取一個 SqlSessionFactory 物件。
   -> 總結：mybatis 的核心物件包括
           (1) SqlSessionFactoryBuilder
           (2) SqlSessionFactory
           (3) SqlSession
           流程如下： SqlSessionFactoryBuilder -> SqlSessionFactory -> SqlSession


3. 從 XML 中構建 SqlSessionFactory
   通過官方的這句話，我可以想到什麼呢？
        第一：在 MyBatis 中一定有一個很重要的物件，這個物件是： SqlSessionFactory 物件
        第二：SqlSessionFactory 物件的創建需要 XML
   XML 是什麼？
        它一定是一個配置文件。


4. mybatis 中有兩個主要的配置文件：
    其中一個是： mybatis-config.xml，這是核心配置文件，主要配置連接資料庫的訊息等。（ 一個 ）
    另一個是： XxxxMapper.xml 這個文件主要是專門用來編寫 SQL 語句的配置文件。（ 一個表一個 ）
        -> t_user 表，一般會對應一個 UserMapper.xml
        -> t_student 表，一般會對應一個 StudentMapper.xml


5. 關於第一個程式的小細節
   * mybatis 中的 sql 語句的結尾 ";" 可以省略。
   * Resources.getResourceAsStream("mybatis-config.xml")
     -> 小技巧： 以後凡是遇到 resource 這個單詞，大部分情況下，這種加載資源的方式都是從類的根路徑下開始加載。（開始查找）
        優點： 採用這種方式，從類路徑當中加載資源，項目的移植性很強，項目從 windows 移植到 linux or mac，程式碼不需要修改，因為這個資源文件一直都在類路徑當中。
   * InputStream is = new FileInputStream("d:\\mybatis-config.xml");
     -> 採用這種方式也可以。
        缺點：可移植性太差，程式不夠健壯，可能會移植到其他的作業系統當中，，導致以上路徑無效，還需要修改 java 程式碼中的路徑。這樣違背了 OCP 原則。
        （在不同的作業系統中，需要改程式碼，導致違反 OCP 原則）
   * 已經驗證了：
     (1) mybatis 核心配置文件的名字，不一定是： mybatis-config.xml。可以是其它的名字。
     (2) mybatis 核心的配置文件存放的路徑，也不一定是在類的根路徑下。可以放到其它的位置，但為了項目的移植性，健壯性，最好將這個配置文件放到類路徑下面。
   * InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("mybatis-config.xml");
     (1) ClassLoader.getSystemClassLoader() 獲取系統的類加載器。
     (2) 系統類加載器有一個方法叫做： getResourceAsStream，它就是從類路徑當中加載資源的。
     (3) 通過 source code 分析
         -> InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
            底層的 source code 就是：
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("mybatis-config.xml");
   * CarMapper.xml 文件的名字是固定的嗎？ CarMapper.xml 文件的路徑是固定的嗎？
     -> 都不是固定的。
        <mapper resource="CarMapper.xml"/> 這種方式是從類路徑當中加載資源。
        <mapper url="file:///d:/CarMapper.xml"/> 這種方式是從絕對路徑當中加載資源。
