使用 mybatis 完成 CRUD

1.  什麼是 CRUD
    C: Create 增
    R: Retrieve 查（檢索）
    U: Update 改
    D: Delete 刪

2. insert
   <insert id="insertCar">
       INSERT INTO car(car_num, brand, guide_price, produce_time, car_type)
       VALUES('1001', '豐田霸道', 30.0, '2000-10-11', '燃油車')
   </insert>
   這樣寫的問題是？
   -> 值，顯然是寫死到配置文件當中的。
      這個在實際開發中是不存在的。
      一定是前端的 form 表單提交過來資料。然後將值傳遞給 Sql 語句。

   -> 例如：JDBC 的程式碼是怎麼寫的？
           String sql = "INSERT INTO car(car_num, brand, guide_price, produce_time, car_type) VALUES(?, ?, ?, ?, ?)";
           ps.setString(1, xxx);
           ps.setString(2, yyy);
           ......

   -> 在 JDBC 當中占位符採用的是 ?，在 mybatis 當中是什麼呢？
      和 ? 等效的寫法是： #{}
      在 mybatis 當中不能使用 ? 占位符，必須使用 #{} 來代替 JDBC 當中的 ?
      #{} 和 JDBC 當中的 ? 是等效的。

   -> java 程式中使用 Map 可以給 SQL 語句的占位符傳值：
      ex: Map<String, Object> map = new HashMap<>();
          map.put("k1", "1111");
          map.put("k2", "比亞迪汗");
          map.put("k3", 10.0);
          map.put("k4", "2020-11-11");
          map.put("k5", "電車");

          INSERT INTO car(car_num, brand, guide_price, produce_time, car_type) VALUES(#{k1}, #{k2}, #{k3}, #{k4}, #{k5});
          注意：#{這裡寫什麼 ？ 寫 map 集合中的 key，如果 key 不存在，獲取的是 null}

          -> 一般 map 集合的 key 起名的時候要見名之意
             ex: Map<String, Object> map = new HashMap<>();
                 map.put("carNum", "1111");
                 map.put("brand", "比亞迪汗");
                 map.put("guidePrice", 10.0);
                 map.put("produceTime", "2020-11-11");
                 map.put("carType", "電車");

                 INSERT INTO car(car_num, brand, guide_price, produce_time, car_type) VALUES(#{carNum}, #{brand}, #{guidePrice}, #{produceTime}, #{carType});

