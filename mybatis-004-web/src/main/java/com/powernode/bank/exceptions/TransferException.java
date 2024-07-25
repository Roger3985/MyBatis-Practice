package com.powernode.bank.exceptions;

/**
 * 轉帳時異常。
 */
public class TransferException extends Exception {
    public TransferException() {}
    public TransferException(String msg) {
        super(msg);
    }
}
