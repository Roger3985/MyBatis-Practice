package com.powernode.bank.service.impl;

import com.powernode.bank.dao.AccountDao;
import com.powernode.bank.dao.impl.AccountDaoImpl;
import com.powernode.bank.exceptions.MoneyNotEnoughException;
import com.powernode.bank.exceptions.TransferException;
import com.powernode.bank.pojo.Account;
import com.powernode.bank.service.AccountService;
import com.powernode.bank.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;

public class AccountServiceImpl implements AccountService {

    private AccountDao accountDao = new AccountDaoImpl();

    @Override
    public void transfer(String fromActno, String toActno, double money) throws MoneyNotEnoughException, TransferException {

        // 添加交易控制程式碼
        SqlSession sqlSession = SqlSessionUtil.openSession();

        // 1. 判斷轉出帳號的餘額是否充足。(select)
        Account fromAct = accountDao.selectByActon(fromActno);
        if (fromAct.getBalance() < money) {
            // 2. 如果轉出帳號餘額不足，提示用戶。
            throw new MoneyNotEnoughException("對不起，餘額不足");
        }
        // 3. 如果轉出帳號餘額充足，更新轉出帳號餘額。(update)
        // 先更新內存中的 java 物件 account 的餘額。
        Account toAct = accountDao.selectByActon(toActno);
        fromAct.setBalance(fromAct.getBalance() - money);
        toAct.setBalance(toAct.getBalance() + money);
        int count = accountDao.updateByActno(fromAct);

        // 模擬異常
        String s = null;
        s.toString();

        // 4. 更新轉入帳戶餘額。(update)
        count += accountDao.updateByActno(toAct);
        if (count != 2) {
            throw new TransferException("轉帳異常，未知原因");
        }

        // 提交交易
        sqlSession.commit();
        // 關閉交易
        SqlSessionUtil.close(sqlSession);
    }
}
