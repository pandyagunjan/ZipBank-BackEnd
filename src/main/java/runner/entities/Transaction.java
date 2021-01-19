package runner.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonView;
import runner.views.Views;


import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonView(Views.AccountSpecific.class)
    private String transactionDescription;

    @JsonView(Views.AccountSpecific.class)
    private Double transactionAmount;

    @JsonView(Views.AccountSpecific.class)
    private Double transactionBalance;

    @JsonView(Views.AccountSpecific.class)
    private LocalDate transactionDate;

    @JsonBackReference
    @ManyToMany(mappedBy = "transactions")
    private Set<Account> accounts = new HashSet<>();

    public Transaction(){
    }

    //for test only
    public Transaction(Double transactionAmount, Set<Account> accounts) {
        this.transactionAmount = transactionAmount;
        this.accounts = accounts;
    }

    //for test only
    public Transaction(String transactionDescription, Double transactionAmount, Double transactionBalance, LocalDate transactionDate) {
        this.transactionDescription = transactionDescription;
        this.transactionAmount = transactionAmount;
        this.transactionBalance = transactionBalance;
        this.transactionDate = transactionDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransactionDescription() {
        return transactionDescription;
    }

    public void setTransactionDescription(String transactionDescription) {
        this.transactionDescription = transactionDescription;
    }

    public Double getTransactionBalance() {
        return transactionBalance;
    }

    public void setTransactionBalance(Double transactionBalance) {
        this.transactionBalance = transactionBalance;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(Double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }

}
