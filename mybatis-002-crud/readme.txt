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

   -> java 程式中使用 POJO 類給 SQL 語句的占位符傳值：
      Car car = new Car(null, "3333", "比亞迪秦", 30.0, "2020-11-11", "新能源");
      注意：占位符 #{}，大括號裡面寫：pojo 類的屬性名
      INSERT INTO car(car_num, brand, guide_price, produce_time, car_type)
      VALUES(#{carNum}, #{brand}, #{guidePrice}, #{produceTime}, #{carType});

      -> (1) 把 SQL 語句寫成這個德性：
         INSERT INTO car(car_num, brand, guide_price, produce_time, car_type)
         VALUES(#{xyz}, #{brand}, #{guidePrice}, #{produceTime}, #{carType});
         (2) 出現了什麼問題呢？
             -> There is no getter for property and named 'xyz' in 'class com.powernode.mybatis.pojo.Car'
                mybatis 去找：Car 類中的 getXyz() 方法去了。沒找到。報錯了。
         (3) 怎麼解決的？
             -> 可以在 Car 類中提供一個 getXyz() 方法。這樣問題就解決的。
         (4) 通過這個測試，得出一個結論： (因為 mybatis is a ORM Framework，所以 mybatis #{carNum} 通過反射機制調用 getCarNum)
             -> 嚴格意義上來說：如果使用 POJO 物件傳遞值的話，#{} 這個大括號中到底寫什麼？
                寫的是 get 方法的方法名去掉 get，然後剩下的單詞首字母小寫，然後放進去。
                例如：getUsername --> #{username}
                     getEmail --> #{email}
                     ......
         (5) 也就是說 mybatis 在底層給 ? 傳值的時候，先要獲取值，怎麼獲取的？
             -> 調用了 pojo 物件的 get 方法，例如：car.getCarNum(). car.getCarType(). car.getBrand().

3. delete
   * 需求： 根據 id 刪除資料
     將 id = 24 的資料刪除
     ex: int count = sqlSession.delete("deleteById", 24);
         <delete id="deleteById">
             DELETE FROM car
             WHERE id = #{id}
         </delete>
         注意：如果占位符只有一個，那麼 #{} 的大括號可以隨意，但是最好是見名知意。

4. update
   * 需求：根據 id 修改某條紀錄
     實現：
         <update id="updateById">
             UPDATE car
             SET car_num=#{carNum}, brand=#{brand}, guide_price=#{guidePrice}, produce_time=#{produceTime}, car_type=#{carType}
             WHERE id=#{id}
         </update>
         Car car = new Car(4, "9999", "凱美瑞", 30.3, "1999-11-10", "燃油車");
         int count = sqlSession.update("updateById", car);




