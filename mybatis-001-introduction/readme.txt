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
＊ 第六步：編寫 MyBatis 程式。

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