package com.powernode.junit.service;

import org.junit.Assert;
import org.junit.Test;

/**
 * 單元測試類
 * @author Roger
 * @version 1.0
 * @since 1.0
 */
public class MathServiceTest { // 名字規範：你要測試的類名 + Test

    /*
       單元測試方法寫多少個？
       一般是一個業務方法對應一個測試方式：
       測試方法的規範： public void testXxxx(){}
       測試方法的方法名： 以 test 開始。假設測試的方法是 sum，這個測試的方法名： testSum
       @Test 註解非常重要，被這個註解標注的方法就是一個單元測試方法。
     */
    @Test
    public void testSum() {
        /*
           單元測試中有兩個重要的概念
           (1) 實際值（被測試的業務邏輯方法的真正執行結果）
           (2) 期望值（執行業務邏輯方法之後，你期望的的執行結果是多少）
         */
        MathService mathService = new MathService();
        // 獲取實際值
        int actual = mathService.sum(1, 2);
        // 期望值
        int expected = 3;
        // 加上斷言進行測試
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testSub() {
        MathService mathService = new MathService();
        // 實際值
        int actual = mathService.sub(10, 5);
        // 期望值
        int expected = 5;
        // 添加斷言機制
        Assert.assertEquals(expected, actual);
    }
}
