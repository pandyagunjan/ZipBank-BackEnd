package runner.controllers;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import runner.entities.Account;
import runner.entities.Transaction;
import runner.services.AccountServices;
import runner.views.Views;

import java.util.Optional;
import java.util.Set;


@RequestMapping("/myaccount")
@RestController
public class AccountController {

    @Autowired
    private AccountServices accountServices;

    /**
     * This controller is used only for JWT testing purposes
     * */
    @GetMapping(value = "/test")
    public String testJWT() {
        return "Hello World";
    }

    //get accounts for the authenticated user only, THIS is the homepage once user has logged in
    //@JsonView(Views.AllAccounts.class)
    @GetMapping
    public ResponseEntity<Set<Account>> readAllAccount() {
       String currentPrincipalName = SecurityContextHolder.getContext().getAuthentication().getName();
       return new ResponseEntity<>(accountServices.getAllAccounts(currentPrincipalName), HttpStatus.OK);
    }

    @JsonView(Views.AccountSpecific.class)
    @GetMapping(value = "/{encryptedUrl}")
    public ResponseEntity<Account> readAccountByUrl(@PathVariable String encryptedUrl){
        return new ResponseEntity<>(accountServices.getAccountByEncryptedUrl(encryptedUrl), HttpStatus.OK);
    }


    @PostMapping(value = "/create")
    public ResponseEntity<Account> create(@RequestBody Account account) throws Exception {
        String currentPrincipalName = SecurityContextHolder.getContext().getAuthentication().getName();
        return new ResponseEntity<>(accountServices.createAccount(account, currentPrincipalName), HttpStatus.CREATED);
    }

    //REMOVE if not needed
    @PutMapping(value = "/update/{id}")
    public ResponseEntity<Optional<Account>> update(@RequestBody Account account, @PathVariable Long id) throws Exception {
        return new ResponseEntity<>(accountServices.updateAccount(id,account), HttpStatus.OK);
    }

    //This needs to be rewritten with "encryptedUrl/delete", need to doublecheck if deleting account deletes User due to cascade.ALL
    @DeleteMapping(value = "/{encryptedUrl}/delete")
    public ResponseEntity<Boolean> deleteById(@PathVariable String encryptedUrl){
        return new ResponseEntity<>(accountServices.removeAccount(encryptedUrl), HttpStatus.OK);
    }

    @JsonView(Views.AccountSpecific.class)
    @PutMapping(value = "/{encryptedUrl}/deposit")
    public ResponseEntity<Account> updateAccountDeposit(@RequestBody Transaction transaction, @PathVariable String encryptedUrl) throws Exception {
        return new ResponseEntity<>(accountServices.deposit(transaction,encryptedUrl), HttpStatus.OK);
    }

    @JsonView(Views.AccountSpecific.class)
    @PutMapping(value = "/{encryptedUrl}/withdraw")
    public ResponseEntity<Account> updateAccountWithdraw(@RequestBody Transaction transaction, @PathVariable String encryptedUrl) throws Exception {
        return new ResponseEntity<>(accountServices.withdraw(transaction,encryptedUrl), HttpStatus.OK);
    }

    //same method as withdraw since JSON payload is same, only front end is different
    @JsonView(Views.AccountSpecific.class)
    @PutMapping(value = "/{encryptedUrl}/transfer")
    public ResponseEntity<Account> updateAccountTransfer(@RequestBody Transaction transaction, @PathVariable String encryptedUrl) throws Exception {
        return new ResponseEntity<>(accountServices.withdraw(transaction,encryptedUrl), HttpStatus.OK);
    }

    @GetMapping("/loggedout")
    public ResponseEntity<?> updateAccountTransfer(){
        String currentPrincipalName = SecurityContextHolder.getContext().getAuthentication().getName();
        return new ResponseEntity<>(accountServices.logout(currentPrincipalName), HttpStatus.OK);
    }
}