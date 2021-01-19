package runner.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import runner.AppRunner;
import runner.entities.Account;
import runner.entities.Transaction;
import runner.enums.AccountType;
import runner.services.AccountServices;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//@WebMvcTest(controllers = CustomerController.class)
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AppRunner.class)
@AutoConfigureMockMvc
@TestPropertySource(properties = {"DB_USER=newuser",
        "DB_PASS=password",
        "DB_URL=jdbc:mysql://localhost:3306/moneymanagement"})

public class AccountControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    AccountServices accountServices;

    Account account1;
    Account account2;
    Transaction transaction;
    List<Transaction> transactionSet;
    String jsonString;
    ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setUp() throws JSONException {
        transaction = new Transaction("Withdrawal to CHECKING XXXXXXXX0987",10.00, 100.00, LocalDate.now());
        transactionSet = new ArrayList<Transaction>();
        transactionSet.add(transaction);
        account1 = new Account(1L,"12345", AccountType.CHECKING,100.00,"abcdefg", transactionSet);
        account2 = new Account(2L,"54321", AccountType.SAVINGS,0.00,"gfedcba", transactionSet);

        //payload for transfer,withdraw,deposit <--structure to micmic production but not necessarily used here in testing
        JSONObject depositPayload = new JSONObject();
        JSONObject accountPayload = new JSONObject();
        accountPayload.put("accountNumber","09865");
        accountPayload.put("routingNumber","11111");
        JSONArray accounts = new JSONArray();
        accounts.put(0,accountPayload);
        depositPayload.put("transactionAmount","10.00");
        depositPayload.put("accounts",accounts);
        jsonString = depositPayload.toString();
    }

    @WithMockUser
    @Test
    public void readAllAccountsTest() throws Exception {
        Set<Account> accountSet = new HashSet<>();
        accountSet.add(account1);
        accountSet.add(account2);
        Mockito.when(accountServices.getAllAccounts(any())).thenReturn(accountSet);

        mockMvc.perform(get("/myaccount"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(2)));
    }

    @WithMockUser
    @Test
    public void readAccountByUrlTest() throws Exception {
        Mockito.when(accountServices.getAccountByEncryptedUrl(any())).thenReturn(account1);

        mockMvc.perform(get("/myaccount/{encryptedUrl}","12345"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance", is(account1.getBalance())))
                .andExpect(jsonPath("$.transactions",hasSize(1)));
    }

    @WithMockUser
    @Test
    public void createAccountTest(){
    }

    @WithMockUser
    @Test
    public void deleteAccountTest() throws Exception {
        Mockito.when(accountServices.removeAccount(any())).thenReturn(true);

        mockMvc.perform(delete("/myaccount/{encryptedUrl}/delete","12345"))
                .andExpect(status().isOk());
    }

    @WithMockUser
    @Test
    public void updateAccountDepositTest() throws Exception {

        Mockito.when(accountServices.deposit(any(),any())).thenReturn(account1);

        mockMvc.perform(put("/myaccount/{encryptedUrl}/deposit","12345")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance", is(account1.getBalance())))
                .andExpect(jsonPath("$.transactions", hasSize(1)));
    }

    @WithMockUser
    @Test
    public void updateAccountWithdrawTest() throws Exception {
        Mockito.when(accountServices.withdraw(any(),any())).thenReturn(account1);

        mockMvc.perform(put("/myaccount/{encryptedUrl}/withdraw","12345")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance", is(account1.getBalance())))
                .andExpect(jsonPath("$.transactions", hasSize(1)));
    }

    @WithMockUser
    @Test
    public void updateAccountTransferTest() throws Exception {
        Mockito.when(accountServices.withdraw(any(),any())).thenReturn(account1);

        mockMvc.perform(put("/myaccount/{encryptedUrl}/transfer","12345")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance", is(account1.getBalance())))
                .andExpect(jsonPath("$.transactions", hasSize(1)));
    }

}
