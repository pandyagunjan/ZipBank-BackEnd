package runner.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import runner.AppRunner;
import runner.entities.Account;
import runner.entities.Customer;
import runner.entities.Login;
import runner.entities.Transaction;
import runner.enums.AccountType;
import runner.repositories.AccountRepo;

import java.util.HashSet;
import java.util.Set;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AppRunner.class)
@TestPropertySource(properties = {
        "DB_USER=newuser",
        "DB_PASS=password",
        "DB_URL=jdbc:mysql://localhost:3306/moneymanagement"
})

public class AccountServiceTest {
    @Mock
    AccountRepo accountRepo;
    @InjectMocks
    AccountServices accountServices;
    Account account1;
    Account account2;
    Login login;
    Customer customer;
    Set<Account> testAccounts;
    Transaction transaction;
    Set<Account> transactionAccount;

    @Before
    public void setup(){
        transactionAccount = new HashSet<Account>();
        transactionAccount.add(account1);
        transaction = new Transaction(1.00,transactionAccount);
        account1 = new Account(1L,"123", AccountType.CHECKING,100.00,"abcdefg", new HashSet<Transaction>());
        account2 = new Account(2L,"321", AccountType.SAVINGS,0.00,"gfedcba", new HashSet<Transaction>());
        testAccounts = new HashSet<Account>();
        testAccounts.add(account1);
        testAccounts.add(account2);
        login = new Login(1L,"user","password",customer); //customer would be null here due to order of code;
        customer = new Customer(1L,"John","Doe",login,testAccounts);
    }

    //PREFACE: we know the behavior of the accountRepo, this is unit test for accountServices; therefore, the results are set by the tester fro the repo

    @Test
    public void getAllAccountsTest(){
        //given: when accountServices.getAlAccounts calls accountRepo.findAccountsByCustomer_LoginUsername, it will return the test Accounts;
        Set<Account> expectedAccounts = testAccounts;
        Mockito.when(accountRepo.findAccountsByCustomer_LoginUsername(login.getUsername())).thenReturn(testAccounts);
        //when: telling accountServices to get all accounts
        Set<Account> actualAccounts = accountServices.getAllAccounts(login.getUsername());
        //the two sets should be identical
        Assert.assertTrue(actualAccounts.containsAll(expectedAccounts));
    }

    @Test
    public void findAccountByEncryptedUrlTest(){
        Account expectedAccount = account1;
        Mockito.when(accountRepo.findAccountByEncryptedUrl(account1.getEncryptedUrl())).thenReturn(account1);

        Account actualAccount = accountServices.findAccountByEncryptedUrl(account1.getEncryptedUrl());

        Assert.assertTrue(expectedAccount.equals(actualAccount));
    }

    @Test
    public void createAccountTest() {
        Account expectedAccount = account1;
        Mockito.when(accountRepo.findAccountByAccountNumber(String.valueOf(Math.floor(Math.random() * 1000000000)))).thenReturn(null);
        Mockito.when(accountRepo.save(expectedAccount)).thenReturn(expectedAccount);

        Account actualAccount = accountServices.createAccount(expectedAccount);

        Assert.assertTrue(expectedAccount.getAccountNumber().length()==10);
    }

    @Test
    public void removeAccountTestFalse() {
        String encryptedUrl = account1.getEncryptedUrl();
        Mockito.when(accountRepo.deleteAccountByEncryptedUrl(encryptedUrl)).thenReturn(account1);
        Mockito.when(accountRepo.findAccountByEncryptedUrl(encryptedUrl)).thenReturn(account1);

        Boolean deleted = accountServices.removeAccount(encryptedUrl);

        Assert.assertFalse(deleted);
    }

    @Test
    public void removeAccountTestTrue() {
        String encryptedUrl = account2.getEncryptedUrl();
        Mockito.when(accountRepo.deleteAccountByEncryptedUrl(encryptedUrl)).thenReturn(account2);
        Mockito.when(accountRepo.findAccountByEncryptedUrl(encryptedUrl)).thenReturn(account2);

        Boolean deleted = accountServices.removeAccount(encryptedUrl);

        Assert.assertTrue(deleted);
    }

    @Test void transferMoneyTestTrue(){

    }

    @Test
    public void withdrawTest() {
    }

    @Test
    public void depositTest() {
    }

    @Test
    public void transferTest() {
    }


}
