package runner.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import runner.entities.Account;
import runner.entities.User;
import runner.services.AccountServices;
import java.util.Optional;
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
    @PutMapping(value = "/update/{id}")
    public ResponseEntity<Optional<Account>> update(@RequestBody Account account, @PathVariable Long id) {
        return new ResponseEntity<>(accountServices.updateAccount(id,account), HttpStatus.OK);
    }
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long id) {
        return new ResponseEntity<>(accountServices.removeAccount(id), HttpStatus.OK);
    }
}