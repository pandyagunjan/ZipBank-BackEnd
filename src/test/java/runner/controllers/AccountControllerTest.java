package runner.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import runner.entities.Account;
import runner.entities.Customer;

@WebMvcTest(controllers = CustomerController.class)
@ActiveProfiles("test")

public class AccountControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountController accountController;

    private Account acccount;

    ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setUp() {
        accountController = Mockito.mock(AccountController.class);
        mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();
        this.acccount = new Account();
    }
}
