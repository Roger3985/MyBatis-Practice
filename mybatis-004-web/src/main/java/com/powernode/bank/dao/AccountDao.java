package com.powernode.bank.dao;

import com.powernode.bank.pojo.Account;

/**
 * 帳戶的 Dao 物件，負責 t_act 表中資料的 CRUD。
 * 強調一下：DAO 物件中沒有任何一個方法和業務不掛鉤。沒有任何業務邏輯在裡面。
 * DAO 中的方法就是做 CRUD 的。所以方法大部分是：insertXXX deleteXXX updateXXX selectXXX。
 * @author Roger
 * @version 1.0
 * @since 1.0
 */
public interface AccountDao {

    /**
     * 根據帳號查詢帳號信息。
     * @param actno 帳號。
     * @return 帳號資訊。
     */
    Account selectByActon(String actno);

    /**
     * 更新帳號信息。
     * @param act 被更新的帳號物件。
     * @return 1 表示更新成功，其他值表示更新失敗。
     */
    int updateByActno(Account act);

}
