package runner.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import runner.entities.Account;
import runner.services.AccountServices;
import java.util.Optional;
@RequestMapping("/account")
@RestController
public class AccountController {
    @Autowired
    private AccountServices accountServices;
    @GetMapping(value = "/{id}")
    public ResponseEntity<Account> readAccount(@PathVariable Long id) throws Exception {
        return new ResponseEntity<>(accountServices.readAccount(id), HttpStatus.OK);
    }
    @PostMapping(value = "/create")
    public ResponseEntity<Account> create(@RequestBody Account account) {
        return new ResponseEntity<>(accountServices.createAccount(account), HttpStatus.CREATED);
    }
    @PutMapping(value = "/update/{id}")
    public ResponseEntity<Optional<Account>> update(@RequestBody Account account, @PathVariable Long id) throws Exception {
        return new ResponseEntity<>(accountServices.updateAccount(id,account), HttpStatus.OK);
    }
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long id) throws Exception {
        return new ResponseEntity<>(accountServices.removeAccount(id), HttpStatus.OK);
    }
    @PutMapping(value = "/deposit/{id}")
    public ResponseEntity<Account> updateAccountDeposit(@RequestBody Double amount,@PathVariable Long id) throws Exception {
        return new ResponseEntity<>(accountServices.deposit(amount,id), HttpStatus.OK);
    }
    @PutMapping(value = "/withdraw/{id}")
    public ResponseEntity<Account> updateAccountWithdraw(@RequestBody Double amount,@PathVariable Long id) throws Exception {
        return new ResponseEntity<>(accountServices.withdraw(amount,id), HttpStatus.OK);
    }
    @PutMapping(value = "/transfer/{fromId}/{toId}")
    public ResponseEntity<Account> updateAccountTransfer(@RequestBody Double amount,@PathVariable Long fromId, @PathVariable Long toId) throws Exception {
        return new ResponseEntity<>(accountServices.transfer(amount,fromId, toId), HttpStatus.OK);
    }
}