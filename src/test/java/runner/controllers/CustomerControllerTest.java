package runner.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import runner.AppRunner;
import runner.entities.*;
import runner.enums.AccountType;
import runner.services.CustomerServices;
import java.util.HashSet;
import java.util.Set;
import static org.hamcrest.CoreMatchers.is;
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
    Address address,addressUpdate;
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
        addressUpdate = new Address(1L,"Address Line Updated", "Address Line Updated", "Bear","DE","19701");
    }

    @WithMockUser
    @Test
    public void createCustomerTest()  {

        try {
            String jsonRequest = objectMapper.writeValueAsString(customer);

            Mockito.when(customerServices.createCustomer(any())).thenReturn(customer);

           mockMvc.perform(post("/openaccount")
                    .content(jsonRequest)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id", is(1)))
                    .andReturn();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @WithMockUser
    @Test
    public void readCustomer() {
              Mockito.when(customerServices.readCustomerByLogin(any())).thenReturn(customer);
        try {
            mockMvc.perform(get("/myaccount/profile"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.firstName",is("John")))
                    .andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @WithMockUser
    @Test
    public void readCustomerNullTest() {
        Mockito.when(customerServices.readCustomerByLogin(any())).thenReturn(null);
        try {
            mockMvc.perform(get("/myaccount/profile"))
                    .andExpect(status().isNotFound())
                    .andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @WithMockUser
//    @Test
//    public void updateCustomerPhone() throws Exception {
//        String jsonRequest = objectMapper.writeValueAsString("548-458-4584");
//        Mockito.when(customerServices.readCustomerByLogin(any())).thenReturn(customer);
//        Mockito.when(customerServices.updateCustomerPhoneNumber(any(),any())).thenReturn(0);
//        mockMvc.perform(put("/myaccount/profile/phone")
//                .content(jsonRequest)
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//    }

//    @WithMockUser
//    @Test
//    public void updateCustomerPhoneCustomerNotfound() throws Exception {
//        String jsonRequest = objectMapper.writeValueAsString("548-584-8999");
//        Mockito.when(customerServices.readCustomerByLogin(any())).thenReturn(null);
//        Mockito.when(customerServices.updateCustomerPhoneNumber(any(),any())).thenReturn(1);
//
//        mockMvc.perform(put("/myaccount/profile/phone")
//                .content(jsonRequest)
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNotFound());
//    }

//    @WithMockUser
//    @Test
//    public void updateCustomerPhoneFormat() throws Exception {
//        String jsonRequest = objectMapper.writeValueAsString("54845");
//        Mockito.when(customerServices.readCustomerByLogin(any())).thenReturn(customer);
//        Mockito.when(customerServices.updateCustomerPhoneNumber(any(),any())).thenReturn(2);
//        mockMvc.perform(put("/myaccount/profile/phone")
//                .content(jsonRequest)
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isBadRequest());
//    }


//
//    @WithMockUser
//    @Test
//    public void updateCustomerEmail() throws Exception {
//        String jsonRequest = objectMapper.writeValueAsString("test@gmail.com");
//        Mockito.when(customerServices.readCustomerByLogin(any())).thenReturn(customer);
//        Mockito.when(customerServices.updateCustomerEmail(any(),any())).thenReturn(0);
//        mockMvc.perform(put("/myaccount/profile/email")
//                .content(jsonRequest)
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//    }

//    @WithMockUser
//    @Test
//    public void updateCustomerEmailCustomerNotFound() throws Exception {
//        String jsonRequest = objectMapper.writeValueAsString("test@gmail.com");
//        Mockito.when(customerServices.readCustomerByLogin(any())).thenReturn(null);
//        Mockito.when(customerServices.updateCustomerEmail(any(),any())).thenReturn(1);
//
//        mockMvc.perform(put("/myaccount/profile/email")
//                .content(jsonRequest)
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNotFound());
//    }


//    @WithMockUser
//    @Test
//    public void updateCustomerEmailFormat() throws Exception {
//        String jsonRequest = objectMapper.writeValueAsString("test");
//        Mockito.when(customerServices.readCustomerByLogin(any())).thenReturn(customer);
//        Mockito.when(customerServices.updateCustomerEmail(any(),any())).thenReturn(2);
//        mockMvc.perform(put("/myaccount/profile/email")
//                .content(jsonRequest)
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isBadRequest());
//    }

    @WithMockUser
    @Test
    public void updateCustomerAddress() throws Exception {
        String jsonRequest = objectMapper.writeValueAsString(addressUpdate);
        Mockito.when(customerServices.readCustomerByLogin(any())).thenReturn(customer);
        Mockito.when(customerServices.updateCustomerAddress(any(),any())).thenReturn(customer);
        mockMvc.perform(put("/myaccount/profile/address")
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @WithMockUser
    @Test
    public void updateCustomerAddressCustomerNotFound() throws Exception {
        String jsonRequest = objectMapper.writeValueAsString(addressUpdate);
        Mockito.when(customerServices.readCustomerByLogin(any())).thenReturn(null);
        Mockito.when(customerServices.updateCustomerAddress(any(),any())).thenReturn(null);
        mockMvc.perform(put("/myaccount/profile/address")
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @WithMockUser
    @Test
    public void updateCustomerDeleteTest() throws Exception {
        Mockito.when(customerServices.readCustomerByLogin(any())).thenReturn(customer);
        Mockito.when(customerServices.deleteCustomer(any())).thenReturn(0);
                mockMvc.perform(delete("/myaccount/profile/delete"))
                .andExpect(status().isOk());
    }

    @WithMockUser
    @Test
    public void updateCustomerDeleteTestAccountWithBalanceFound() throws Exception {
        Mockito.when(customerServices.readCustomerByLogin(any())).thenReturn(customer);
        Mockito.when(customerServices.deleteCustomer(any())).thenReturn(2);
        mockMvc.perform(delete("/myaccount/profile/delete"))
                .andExpect(status().isForbidden());
    }

    @WithMockUser
    @Test
    public void updateCustomerDeleteCustomerNotfound() throws Exception {
        Mockito.when(customerServices.readCustomerByLogin(any())).thenReturn(customer);
        Mockito.when(customerServices.deleteCustomer(any())).thenReturn(1);
        mockMvc.perform(delete("/myaccount/profile/delete"))
                .andExpect(status().isNotFound());
    }
}
