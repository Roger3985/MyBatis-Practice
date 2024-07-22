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
   <img width="70" height="70" src="img.png" alt="Spring boot">