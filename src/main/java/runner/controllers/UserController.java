package runner.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import runner.entities.User;
import runner.services.UserServices;
import java.util.Optional;
@RequestMapping("/user")
@RestController
public class UserController {
    @Autowired
    private UserServices userServices;
    @GetMapping(value = "/read/{id}")
    public ResponseEntity<User> readById(@PathVariable Long id) throws Exception {
        if(new ResponseEntity<>(userServices.readUser(id), HttpStatus.OK) == null) throw new Exception("Error , the user id is null") ;
        else
            return new ResponseEntity<>(userServices.readUser(id), HttpStatus.OK);
    }
    @PostMapping(value = "/create")
    public ResponseEntity<User> create(@RequestBody User user) {
        return new ResponseEntity<>(userServices.createUser(user), HttpStatus.CREATED);
    }
    @PutMapping(value = "/update/{id}")
    public ResponseEntity<User> update(@RequestBody User user,@PathVariable Long id) {
        return new ResponseEntity<>(userServices.updateUser(id,user), HttpStatus.OK);
    }
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long id) {
        return new ResponseEntity<>(userServices.deleteUser(id), HttpStatus.OK);
    }
    @GetMapping(value = "/home")
    public String displayHome() {
        return "Hello World";
    }
}