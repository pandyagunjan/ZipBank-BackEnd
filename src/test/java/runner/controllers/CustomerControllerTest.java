package runner.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.hamcrest.Matchers;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import runner.AppRunner;
import runner.entities.*;
import runner.enums.AccountType;
import runner.services.AccountServices;
import runner.services.CustomerServices;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AppRunner.class)
@AutoConfigureMockMvc
@TestPropertySource(properties = {"DB_USER=newuser",
        "DB_PASS=password",
        "DB_URL=jdbc:mysql://localhost:3306/moneymanagement"})

public class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerServices customerServices;
    ObjectMapper objectMapper = new ObjectMapper();
    Account account1;
    Account account2;
    Account account3;
    Set<Account> testAccounts;
    Login login;
    Customer customer;
    Address address;
    @Before
    public void setup(){
        account1 = new Account(1L,"12345", AccountType.CHECKING,100.00,"abcdefg", new HashSet<Transaction>());
        account2 = new Account(2L,"54321", AccountType.SAVINGS,0.00,"gfedcba", new HashSet<Transaction>());
        account3 =  new Account(2L,"56789", AccountType.SAVINGS,100.00,"qwerty", new HashSet<Transaction>());
        testAccounts = new HashSet<Account>();
        testAccounts.add(account1);
        testAccounts.add(account2);
        login = new Login(1L,"user","password",customer); //customer would be null here due to order of code;
        customer = new Customer(1L,"John","Doe",login,testAccounts);
        address = new Address(1L,"Address Line 1", "Address Line 2", "Bear","DE","19701");
    }

    @WithMockUser
    @Test
    public void createUserTest() throws Exception {
        String jsonRequest = objectMapper.writeValueAsString(customer);
        Mockito.when(customerServices.createCustomer(any())).thenReturn(customer);

              MvcResult result=   mockMvc.perform(post("/openaccount")
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id",is(1)))
                      .andReturn();
    }

}
