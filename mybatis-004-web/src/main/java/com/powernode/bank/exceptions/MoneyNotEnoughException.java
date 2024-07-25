package com.powernode.bank.exceptions;

/**
 * 餘額不足異常。
 */
public class MoneyNotEnoughException extends Exception {
    public MoneyNotEnoughException() {}
    public MoneyNotEnoughException(String msg) {
        super(msg);
    }
}
