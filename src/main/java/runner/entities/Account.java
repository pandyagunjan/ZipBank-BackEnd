package runner.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import runner.enums.AccountType;
import javax.persistence.*;
import java.time.LocalDate;

import java.util.Set;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private transient String accountNumber;
    @Column(nullable = false)
    private String routingNumber;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountType accountType; //enum
    @Column(nullable = false)
    private Double balance;
    @Column(nullable = false)
    private LocalDate dateOfOpening;
    @Column(nullable = false)
    private Double interestRate;

    @JsonBackReference(value = "name1")
    @OneToMany(mappedBy = "account" ,cascade= CascadeType.ALL, fetch=FetchType.EAGER)
    private Set<Transaction> transactionsList;

   // @JsonBackReference (value = "name2")
//    @ManyToOne
//    @JoinColumn(name = "user_id")//, insert = "false" , update="false")
//    private User user;

    @ManyToOne(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private Customer customer;

    public Account() {
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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

    public Set<Transaction> getTransactionsList() {
        return transactionsList;
    }
    public void setTransactionsList(Set<Transaction> transactionsList) {
        this.transactionsList = transactionsList;
    }

    public Customer getUser() {
        return customer;
    }

    public void setUser(Customer customer) {
        this.customer = customer;
    }
}