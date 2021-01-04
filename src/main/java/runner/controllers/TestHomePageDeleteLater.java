package runner.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import runner.entities.Account;
import runner.services.AccountServices;
import runner.services.CustomerServices;
import runner.services.LoginServices;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class TestHomePageDeleteLater {
    @Autowired
    CustomerServices customerServices;

    @Autowired
    AccountServices accountServices;

    @Autowired
    LoginServices loginServices;

    @GetMapping(value = "/")
    public String homePage(){
        return "Hello World";
    }

    //get accounts for the authenticated user only: THIS METHOD HAS BEEN MOVED TO ACCOUNTCONTROLLER
/*    @GetMapping(value = "/myaccount")
    public ResponseEntity<Set<Account>> readAllAccount(){
        String currentPrincipalName = SecurityContextHolder.getContext().getAuthentication().getName();
        return new ResponseEntity<>(customerServices.getAllAccounts(currentPrincipalName), HttpStatus.OK);
    }*/


    //get account for specific encrypted URL: THIS METHOD HAS BEEN MOVED TO ACCOUNTCONTROLLER
/*    @GetMapping(value = "/{accountEncryptedUrl}")
    public ResponseEntity<Account> readAccountById(@PathVariable String accountEncryptedUrl) throws Exception {
        String currentPrincipalName = SecurityContextHolder.getContext().getAuthentication().getName();
        Long accountId = accountServices.getAllAccounts(currentPrincipalName).stream()
                .filter(a->a.getEncryptedUrl().equals(accountEncryptedUrl))
                .collect(Collectors.toList()).get(0).getId();
        return new ResponseEntity<>(accountServices.readAccount(accountId), HttpStatus.OK);
    }*/

}