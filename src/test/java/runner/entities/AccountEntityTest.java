package runner.entities;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import runner.AppRunner;
import runner.enums.AccountType;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AppRunner.class)
@TestPropertySource(properties = {"DB_USER=newuser",
        "DB_PASS=password",
        "DB_URL=jdbc:mysql://localhost:3306/moneymanagement"})
public class AccountEntityTest {

    Account account1;
    Account account2;
    Account account3;
    Set<Account> testAccounts;
    Login login;
    Customer customer;
    Transaction transaction;
    Set<Account> transactionAccount;
    Transaction withdrawalTransaction;
    Transaction depositTransaction;
    ArrayList<Transaction> myTransactionList;
    Address address;
    @Before
    public void setup(){
        account1 = new Account(1L,"12345", AccountType.CHECKING,100.00,"abcdefg", new ArrayList<Transaction>());
        account2 = new Account(2L,"54321", AccountType.SAVINGS,0.00,"gfedcba", new ArrayList<Transaction>());
        account3 =  new Account(2L,"56789", AccountType.SAVINGS,100.00,"qwerty", new ArrayList<Transaction>());
        testAccounts = new HashSet<Account>();
        testAccounts.add(account1);
        testAccounts.add(account2);
        login = new Login(1L,"user","password",customer); //customer would be null here due to order of code;
        address = new Address(1L,"Address Line 1", "Address Line 2", "Bear","DE","19701");
        customer = new Customer(1L,"John","Doe",address,login,testAccounts);
    }

    @Test
    public void testClassSignatureAnnotations() {
        Assert.assertTrue(Customer.class.isAnnotationPresent(Entity.class));
    }
    @Test
    public void testCreateJson() throws JsonProcessingException {
        ObjectMapper objectMapper= new ObjectMapper();
        //   Customer expectedCustomer = new Customer( 1L, "Radha" , "Ramnik","Patel","234324");
        String json = objectMapper.writeValueAsString(account1);
        Account actualAccount=objectMapper.readValue(json, Account.class);
        Assert.assertEquals(account1.getId(), actualAccount.getId());

    }

}

