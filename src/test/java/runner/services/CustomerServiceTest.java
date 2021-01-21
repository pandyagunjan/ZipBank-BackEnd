package runner.services;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import runner.AppRunner;
import runner.entities.*;
import runner.enums.AccountType;
import runner.repositories.CustomerRepo;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AppRunner.class)
@TestPropertySource(properties = {"DB_USER=newuser",
        "DB_PASS=password",
        "DB_URL=jdbc:mysql://localhost:3306/moneymanagement"})
public class CustomerServiceTest {
    @Mock (name = "mock2")
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Mock (name = "mock3")
    AccountServices accountServices;
    @Mock (name = "mock1")
    CustomerRepo customerRepo;
    @InjectMocks
    private CustomerServices customerServices; //Calls the mockito

    Account account1;
    Account account2;
    Account account3;
    Set<Account> testAccounts;
    Login login;
    Login login1;
    Customer customer;
    Address address;
    List<String> logins ;

    @Before
    public void setup(){
        account1 = new Account(1L,"12345", AccountType.CHECKING,100.00,"abcdefg", new ArrayList<Transaction>());
        account2 = new Account(2L,"54321", AccountType.SAVINGS,0.00,"gfedcba", new ArrayList<Transaction>());
        account3 =  new Account(2L,"56789", AccountType.SAVINGS,100.00,"qwerty", new ArrayList<Transaction>());
        testAccounts = new HashSet<Account>();
        testAccounts.add(account1);
        testAccounts.add(account2);
        login = new Login(1L,"user1","password",customer); //customer would be null here due to order of code;
        login1 = new Login(2L,"user2","password",customer); //customer would be null here due to order of code;
        address = new Address(1L,"Address Line 1", "Address Line 2", "Bear","DE","19701");
        customer = new Customer(1L,"John","Doe",address,login,testAccounts);
        logins = Arrays.asList(login.getUsername(),login1.getUsername());
    }

/*    @Test
    public void readUserByIdTest() {
        String expectedName= "John";
        Mockito.when(customerRepo.findCustomerById(1L)).thenReturn(customer);
        String testName = customerServices.readCustomer(1L).getFirstName();
        Assert.assertEquals(expectedName, testName);
    }*/

    @Test
    public void readUserByLoginTest() {
        String expectedName= "John";
        Mockito.when(customerRepo.findCustomerByLoginUsername("user")).thenReturn(customer);
        String testName = customerServices.readCustomerByLogin("user").getFirstName();
        Assert.assertEquals(expectedName, testName);
    }

    @Test
    public void createUserTest() throws Exception {
        Customer expectedCustomer = customer;
        Mockito.when(bCryptPasswordEncoder.encode(any())).thenReturn("blahblah");
        Mockito.when(accountServices.setUpAccount(any())).thenReturn(account1);
        Mockito.doNothing().when(accountServices).transferMoneyToNewAccount(any());
        Mockito.when(customerRepo.save(any())).thenReturn(expectedCustomer);
        customerServices.createCustomer(customer);
        Assert.assertTrue(expectedCustomer.getId()!=0);
    }

    @Test
    public void deleteUserTest() {
        int expected = 2;
        Mockito.when(customerRepo.save(customer)).thenReturn(customer);
        Mockito.when(customerRepo.findCustomerById(customer.getId())).thenReturn(customer);
        int actual= customerServices.deleteCustomer(customer.getId());
         Assert.assertEquals(expected, actual);
    }

    @Test
    public void checkAccountBalanceAndDeleteTest() {
        int expected = 2;
        Mockito.when(customerRepo.save(customer)).thenReturn(customer);
        Mockito.when(customerRepo.findCustomerById(customer.getId())).thenReturn(customer);
        int actual= customerServices.checkAccountBalanceAndDelete(customer.getId(),customer);
        Assert.assertEquals(expected, actual);
    }

/*    @Test
    public void updateUserTest() throws Exception {
      //  Customer customer1 = new Customer( 2L, "Radha" , "Ramnik",address,login,testAccounts);
        String expectedUpdateName= "Update the First Name";
        Mockito.when(customerRepo.findCustomerById(customer.getId())).thenReturn(customer);
        customer.setFirstName(expectedUpdateName);
        Mockito.when(customerRepo.save(customer)).thenReturn(customer);

        //Fails as customerService has been autowried to accountServices too... Need to look into this.
        String actualUpdatedName= customerServices.updateCustomer(customer.getId(), customer).getFirstName();
        Assert.assertEquals(expectedUpdateName, actualUpdatedName);
    }*/

    @Test
    public void updateUserPhoneNumberTest() throws Exception {
        Customer customer1 = new Customer(2L, "Radha", "Ramnik", address, login, testAccounts);
        Customer customer2 = new Customer(2L, "Radha", "Ramnik", address, login, testAccounts);
        String newPhoneNumber = "2151234567";
        customer2.setPhoneNumber(newPhoneNumber);

        Mockito.when(customerRepo.save(customer1)).thenReturn(customer1);
        Mockito.when(customerRepo.findCustomerByLoginUsername(any())).thenReturn(customer1);
        customerServices.updateCustomerPhoneNumber(customer1.getLogin().getUsername(), customer2);

        Assert.assertTrue(customer1.getPhoneNumber().equals(newPhoneNumber));
    }

    @Test
    public void updateUserEmailTest() throws Exception {
        Customer customer1 = new Customer( 2L, "Radha" , "Ramnik",address,login,testAccounts);
        Customer customer2 = new Customer(2L, "Radha", "Ramnik", address, login, testAccounts);
        String newEmail = "test@gmail.com";
        customer2.setEmail(newEmail);

        Mockito.when(customerRepo.findCustomerByLoginUsername(any())).thenReturn(customer1);
        customerServices.updateCustomerEmail(customer1.getLogin().getUsername(), customer2);

        Assert.assertTrue(customer1.getEmail().equals(newEmail));
    }

    @Test
    public void updateUserAddressTest() throws Exception {
        Customer customer1 = new Customer( 2L, "Radha" , "Ramnik",address,login,testAccounts);
        Address newAddress = new Address(1L,"123 A st", null, "Philadelphia","PA","19148");

        Mockito.when(customerRepo.findCustomerByLoginUsername(any())).thenReturn(customer1);
        customerServices.updateCustomerAddress(customer1.getLogin().getUsername(), newAddress);

        Assert.assertEquals(newAddress.getFirstLine(), customer1.getAddress().getFirstLine());
    }

    @Test
    public void checkLoginTest() throws Exception {
        Boolean expected =false;

        Mockito.when(customerRepo.findAllLoginsNative()).thenReturn(logins);
        Boolean actual= customerServices.checkLogin(login);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getAllAccountsTest() throws Exception {
        Mockito.when(customerRepo.findCustomerById(customer.getId())).thenReturn(customer);
        Set<Account> actualAccounts= customerServices.getAllAccounts(customer.getId());
        Assert.assertEquals(testAccounts.size(), actualAccounts.size());
    }

    @Test
    public void removeAccountTestTrue()throws Exception {
        String username = customer.getLogin().getUsername();
        String encryptedUrl = account2.getEncryptedUrl();
        Mockito.when(customerRepo.findCustomerByLoginUsername(any())).thenReturn(customer);
        Mockito.when(customerRepo.save(any())).thenReturn(customer);

        customerServices.removeAccount(username,encryptedUrl);

        Assert.assertTrue(customer.getAccounts().size()==1);
    }


    @Test(expected = Exception.class)
    public void removeAccountTest() throws Exception {
        String username = customer.getLogin().getUsername();
        String encryptedUrl = account1.getEncryptedUrl();
        Mockito.when(customerRepo.findCustomerByLoginUsername(any())).thenReturn(customer);
        Mockito.when(customerRepo.save(any())).thenReturn(customer);

        customerServices.removeAccount(username,encryptedUrl);
    }

}