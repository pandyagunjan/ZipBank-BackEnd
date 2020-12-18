package entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
@Entity
public abstract class Account {
    @Id
    protected Long Id;
    protected String accountNumber;
    protected String routingNumber;
    protected Double balance;
    protected LocalDate dateOfOpening;
    protected Double interestRate;
    public Account() {
    }
//    public Account(Long id, String accountNumber, String routingNumber, Double balance, LocalDate dateOfOpening, Double interestRate) {
//        Id = id;
//        this.accountNumber = accountNumber;
//        this.routingNumber = routingNumber;
//        this.balance = balance;
//        this.dateOfOpening = dateOfOpening;
//        this.interestRate = interestRate;
//    }
    public Long getId() {
        return Id;
    }
    public void setId(Long id) {
        Id = id;
    }
    public String getAccountNumber() {
        return accountNumber;
    }
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
    public String getRoutingNumber() {
        return routingNumber;
    }
    public void setRoutingNumber(String routingNumber) {
        this.routingNumber = routingNumber;
    }
    public Double getBalance() {
        return balance;
    }
    public void setBalance(Double balance) {
        this.balance = balance;
    }
    public LocalDate getDateOfOpening() {
        return dateOfOpening;
    }
    public void setDateOfOpening(LocalDate dateOfOpening) {
        this.dateOfOpening = dateOfOpening;
    }
    public Double getInterestRate() {
        return interestRate;
    }
    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }
}