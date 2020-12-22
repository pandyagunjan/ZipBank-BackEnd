package runner.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import runner.entities.Account;
import runner.services.AccountServices;

@RequestMapping("/account")
@RestController
public class AccountController {
    @Autowired
    private AccountServices accountServices;

    @GetMapping(value = "/read/{id}")
    public ResponseEntity<Account> readById(@PathVariable Long id) {
        return new ResponseEntity<>(accountServices.readAccount(id), HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Account> create(@RequestBody Account account) {
        return new ResponseEntity<>(accountServices.createAccount(account), HttpStatus.CREATED);
    }

}
