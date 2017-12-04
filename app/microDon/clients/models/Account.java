package microDon.clients.models;

import java.time.ZonedDateTime;

public class Account extends BankinBaseModel {

    private String name;

    private Double balance;

    private Integer status;

    //private Item item;

    private Bank bank;

    private ZonedDateTime lastRefreshDate;

    //private Loan loanDetails;

    //private Savings savingsDetails;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public ZonedDateTime getLastRefreshDate() {
        return lastRefreshDate;
    }

    public void setLastRefreshDate(ZonedDateTime lastRefreshDate) {
        this.lastRefreshDate = lastRefreshDate;
    }
}
