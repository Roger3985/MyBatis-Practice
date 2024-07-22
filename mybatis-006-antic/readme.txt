mybatis 小技巧
1. #{} 和 ${} 的區別

#{}
2024-07-22 13:43:14.825 [main] DEBUG c.p.mybatis.mapper.CarMapper.selectByCarType -==>  Preparing: SELECT id, car_num as carNum, brand, guide_price as guidePrice, produce_time as produceTime, car_type as carType FROM car WHERE car_type = ?
2024-07-22 13:43:14.853 [main] DEBUG c.p.mybatis.mapper.CarMapper.selectByCarType -==> Parameters: 新能源(String)
2024-07-22 13:43:14.889 [main] DEBUG c.p.mybatis.mapper.CarMapper.selectByCarType -<==      Total: 2

${}
2024-07-22 13:41:40.574 [main] DEBUG c.p.mybatis.mapper.CarMapper.selectByCarType -==>  Preparing: SELECT id, car_num as carNum, brand, guide_price as guidePrice, produce_time as produceTime, car_type as carType FROM car WHERE car_type = 新能源
2024-07-22 13:41:40.609 [main] DEBUG c.p.mybatis.mapper.CarMapper.selectByCarType -==> Parameters:

org.apache.ibatis.exceptions.PersistenceException:
### Error querying database.  Cause: org.postgresql.util.PSQLException: ERROR: column "新能源" does not exist
  建議：Perhaps you meant to reference the column "car.id".
  位置：252
### The error may exist in CarMapper.xml
### The error may involve defaultParameterMap
### The error occurred while setting parameters
### SQL: SELECT             id,             car_num as carNum,             brand,             guide_price as guidePrice,             produce_time as produceTime,             car_type as carType         FROM             car         WHERE             car_type = 新能源
### Cause: org.postgresql.util.PSQLException: ERROR: column "新能源" does not exist
  建議：Perhaps you meant to reference the column "car.id".
  位置：252

#{} 和 ${} 的區別:
   -> #{}: 底層使用 PreparedStatement。特點: 先進行 SQL 語句的編譯，然後再給 SQL 語句的占位符 ? 傳值。可以避免 SQL 注入的風險。
   -> ${}: 底層使用 Statement。特點: 先進行 SQL 語句的拼接，然後在對 SQL 語句進行編譯。存在 SQL 注入的風險。
   * 優先使用 #{}，這是原則，避免 SQL 注入的風險。
   * 除非需要傳入關鍵字(ex: asc or desc) 才使用 ${}，(如果需要 SQL 語句的關鍵字放到 SQL 語句中，只能使用 ${}，因為 #{} 是以值的形式放到 SQL 語句當中的。

2. 向 SQL 語句當中拼接表名，就需要使用 ${}
   現實業務中，可能會存在分表儲存資料的情況，因為一張表存在的話，資料量太大，查詢效率比較低。
   可能會將這些資料有規律的分表儲存，這樣在查詢的時候效率就比較高，因為掃描的資料量變少了。
   日誌表，專門存儲日誌信息的。如果 t_log 只有一張表，這張表中每天都會產生很多 log，慢慢的，這個表中數據會很多。
   怎麼解決問題？
   可以每天生成一個新表。每張表以當天日期作為名稱。例如：
   t_log_20220901
   t_log_20220902
   ...

   你怎麼知道某一天的日誌信息怎麼辦？
   假設今天是 20220901，那麼直接查 t_log_20220901 的表即可。

3. 批量刪除: 一次刪除多條紀錄。
   批量刪除的 SQL 語句有兩種寫法:
   (1) or: delete from car where id = 1 or id = 2 or id = 3;
   (2) int: delete from car where id in(1, 2, 3);

   應該採用 ${} 的方式
   * delete from car where id in(${ids});

4. 模糊查詢: like
   需求: 根據汽車品牌進行模糊查詢
   SQL: select * from car where brand like '%比亞迪%';
   第一種解決方案:
        '%${brand}'
   第二種解決方案:
        concat 函數，這是一個 mysql 資料庫當中的一個函數，專門進行字符串拚接。
        concat('%', #{}, '%'}
   第三種解決方案:
        brand like concat('%', '${brand}', '%')
   第四種解決方式:
        "%"#{brand}"%"

5. 關於 MyBatis 中別名機制:
<!-- 起別名 -->
<typeAliases>
    <!--
        type: 指定給哪個類型起別名
        alias: 指定別名
        注意: 別名不區分大小寫
        alias 屬性是可以省略的。有默認的別名。
        省略 alias 之後，別名就是類的簡名，比如: com.powernode.mybatis.pojo.Car 的別名就是 Car / car / cAR/ cAr，不區分大小寫
    -->
    <!-- <typeAlias type="com.powernode.mybatis.pojo.Car" alias="aaa"/> -->

    <!-- 將這個包下的所有的類全部自動起別名，別名就是類簡名，不區分大小寫。 -->
    <package name="com.powernode.mybatis.pojo"/>
</typeAliases>
總結: 所有別名不區分大小寫，namespace 不能使用別名機制。

6. mybatis-config.xml 文件中的 mappers 標籤。
   <mapper resource="CarMapper.xml"/> 要求類的根路徑下必須有: CarMapper.xml
   <mapper url="file:///d:/CarMapper.xml"/> 要求在 d:/下有 CarMapper.xml
   <mapper class="全限定介面名，帶有包名"/>

   mapper 標籤的屬性可以有三個:
   (1) resource: 這種方式是從類的根路徑下開始查找資源，採用這種方式的話，你的配置文件需要放到類路徑當中才行。
   (2) url: 這種方式是一種絕對路徑的方式，這種方式不要求配置文件必須放在類路徑當中，哪裡都行，只要提供一個絕對路徑就行。這種方式使用極少，因為移植性太差。
   (3) class: 這個位置提供的是 mapper 介面的全限定介面名，必須帶有包名的。
              思考: mapper 標籤的作用是指定 SqlMapper.xml 文件的路徑，指定介面有什麼用?
              <mapper class="com.powernode.mybatis.mapper.CarMapper"/>
              如果你的 class 指定是: com.powernode.mybatis.mapper.CarMapper
              那麼 mybatis 框架會自動去 com/powernode/mybatis/mapper 目錄下查找 CarMapper.xml 文件。
              注意: 也就是說，如果你採用這種方式，那麼你必須保證 CarMapper.xml 文件和 CarMapper 介面必須在同一個目錄底下。並且名子一致。
              CarMapper 介面 -> CarMapper.xml
              LogMapper 介面 -> LogMapper.xml
              ......
   提醒: !!!!!!!!!!!!!!!
   -> 在 IDEA 的 resources 目錄下新建多重目錄的話: 必須是這樣創建:
          com/powernode/mybatis/mapper
      不能這樣:
          com.powernode.mybatis.mapper



