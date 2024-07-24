package com.powernode.bank.pojo;

/**
 * 帳號類：封裝帳戶資料。
 * @author Roger
 * @version 1.0
 * @since 1.0
 */
public class Account {

    private Integer id;
    private String actno;
    private Double balance;

    public Account() {
    }

    public Account(Integer id, String actno, Double balance) {
        this.id = id;
        this.actno = actno;
        this.balance = balance;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getActno() {
        return actno;
    }

    public void setActno(String actno) {
        this.actno = actno;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", actno='" + actno + '\'' +
                ", balance=" + balance +
                '}';
    }
}
