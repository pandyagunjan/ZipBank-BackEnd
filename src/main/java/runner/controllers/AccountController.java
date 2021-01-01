package runner.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import runner.entities.Account;
import runner.entities.Transaction;
import runner.security.filters.JwtAuthorizationFilter;
import runner.services.AccountServices;
import runner.services.CustomerServices;
import runner.services.LoginServices;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequestMapping("/myaccount")
@RestController
public class AccountController {

    @Autowired
    private AccountServices accountServices;

    @Autowired
    private CustomerServices customerServices;
    /**
     * This controller is used only for JWT testing purposes
     * */
    @GetMapping(value = "/test")
    public String testJWT() {
        return "Hello World";
    }

    //get accounts for the authenticated user only, THIS is the homepage once user has logged in
    @GetMapping
    public ResponseEntity<Set<Account>> readAllAccount() {
        String currentPrincipalName = SecurityContextHolder.getContext().getAuthentication().getName();
        return new ResponseEntity<>(customerServices.getAllAccounts(currentPrincipalName), HttpStatus.OK);
    }

    //REMOVE if not needed
    @GetMapping(value = "/{id}")
    public ResponseEntity<Account> readAccount(@PathVariable Long id) throws Exception {
        return new ResponseEntity<>(accountServices.readAccount(id), HttpStatus.OK);
    }

    //REMOVE if not needed
    @PostMapping(value = "/create")
    public ResponseEntity<Account> create(@RequestBody Account account) {
        return new ResponseEntity<>(accountServices.createAccount(account), HttpStatus.CREATED);
    }

    //REMOVE if not needed
    @PutMapping(value = "/update/{id}")
    public ResponseEntity<Optional<Account>> update(@RequestBody Account account, @PathVariable Long id) throws Exception {
        return new ResponseEntity<>(accountServices.updateAccount(id,account), HttpStatus.OK);
    }

    //This needs to be rewritten with "encryptedUrl/delete"
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long id) throws Exception {
        return new ResponseEntity<>(accountServices.removeAccount(id), HttpStatus.OK);
    }

    @PutMapping(value = "/{encryptedUrl}/deposit")
    public ResponseEntity<Account> updateAccountDeposit(@RequestBody Transaction transaction, @PathVariable String encryptedUrl) throws Exception {
        return new ResponseEntity<>(accountServices.deposit(transaction,encryptedUrl), HttpStatus.OK);
    }

    @PutMapping(value = "/{encryptedUrl}/withdraw")
    public ResponseEntity<Account> updateAccountWithdraw(@RequestBody Transaction transaction, @PathVariable String encryptedUrl) throws Exception {
        return new ResponseEntity<>(accountServices.withdraw(transaction,encryptedUrl), HttpStatus.OK);
    }

    //same method as withdraw since JSON payload is same, only front end is different
    @PutMapping(value = "/<encryptedUrl}/transfer")
    public ResponseEntity<Account> updateAccountTransfer(@RequestBody Transaction transaction, @PathVariable String encryptedUrl) throws Exception {
        return new ResponseEntity<>(accountServices.withdraw(transaction,encryptedUrl), HttpStatus.OK);
    }
}