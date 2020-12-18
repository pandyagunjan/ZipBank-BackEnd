package entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;
@Entity
public class TransactionHistory {
    @Id
    private Long id;
    private String accountType;
    private String transactionType;
    private Double balanceAfterTransaction;
    private LocalDate dateOfTransaction;


}
