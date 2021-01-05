package runner.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonView;
import runner.enums.AccountType;
import runner.views.Views;

import javax.persistence.*;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonView(Views.AccountNumber.class)
    private String accountNumber;

    @JsonView(Views.AccountDetails.class)
    private String routingNumber = "091000022";

    @JsonView(Views.AccountType.class)
    @Enumerated(EnumType.STRING)
    private AccountType accountType; //enum

    @JsonView(Views.AccountActions.class)
    private Double balance;

    @JsonView(Views.AccountDetails.class)
    private LocalDate dateOfOpening;

    @JsonView(Views.AccountDetails.class)
    private Double interestRate;

    @JsonView(Views.AllAccounts.class) //delete this later in production
    private String encryptedUrl;

    @JsonView(Views.AccountSpecific.class)
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "account_transaction",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "transaction_id"))
    private Set<Transaction> transactions = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public Account() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void addToTransactionsList(Transaction transaction){
        transactions.add(transaction);
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

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
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

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactionsList) {
        this.transactions = transactionsList;
    }

    public String getEncryptedUrl() {
        return encryptedUrl;
    }

    public void setEncryptedUrl(String encryptedURL) {
        this.encryptedUrl = encryptedURL;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}