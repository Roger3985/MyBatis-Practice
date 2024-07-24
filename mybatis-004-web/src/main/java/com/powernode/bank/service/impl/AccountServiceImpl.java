package com.powernode.bank.service.impl;

import com.powernode.bank.service.AccountService;

public class AccountServiceImpl implements AccountService {

    @Override
    public void transfer(String fromActno, String toActno, double money) {
        // 1. 判斷轉出帳號的餘額是否充足。(select)
        // 2. 如果轉出帳號餘額不足，提示用戶。
        // 3. 如果轉出帳號餘額充足，更新轉出帳號餘額。(update)
        // 4. 更新轉入帳戶餘額。(update)
    }
}
