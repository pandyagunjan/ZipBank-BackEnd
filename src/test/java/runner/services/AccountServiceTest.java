package runner.services;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import runner.AppRunner;
import runner.entities.Customer;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AppRunner.class)
@TestPropertySource(properties = {
        "DB_USER=newuser",
        "DB_PASS=password",
        "DB_URL=jdbc:mysql://localhost:3306/moneymanagement"
})

public class AccountServiceTest {
    @Autowired
    private AccountServices accountServices;

    @Test
    public void createAccountTest() {
    }
    @Test
    public void readAccountTest() {
    }
    @Test
    public void removeAccountTest() {
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
