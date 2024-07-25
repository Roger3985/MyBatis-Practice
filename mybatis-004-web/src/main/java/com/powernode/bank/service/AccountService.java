package com.powernode.bank.service;

import com.powernode.bank.exceptions.MoneyNotEnoughException;
import com.powernode.bank.exceptions.TransferException;

/**
 * 注意：業務類當中的業務方法的名字在起名的時候，最好見名知意。能夠體現出具體的業務是做什麼的。
 * 帳戶業務類。
 * @author Roger
 * @version 1.0
 * @since 1.0
 */
public interface AccountService {

    /**
     * 帳戶轉帳業務。
     * @param fromActno 轉出帳號。
     * @param toActno 轉入帳號。
     * @param money 轉帳金額。
     */
    void transfer(String fromActno, String toActno, double money) throws MoneyNotEnoughException, TransferException;
}
