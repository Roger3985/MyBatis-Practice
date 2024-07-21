package com.powernode.junit.service;

/**
 * 數學業務類
 * @author Roger
 * @version 1.0
 * @since 1.0
 */
public class MathService {

    /**
     * 求和的業務方法
     * @param a
     * @param b
     * @return
     */
    public int sum(int a, int b) {
        return a + b;
    }

    /**
     * 相減的業務方法
     * @param a
     * @param b
     * @return
     */
    public int sub(int a, int b) {
        return a - b;
    }
}
