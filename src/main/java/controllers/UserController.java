package controllers;

import entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import services.UserServices;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserServices userServices;

//    @GetMapping(value = "/user/read/{id}")
//    public ResponseEntity<User> readById(@PathVariable Long id) {
//        return new ResponseEntity<>(userServices.readUser(id), HttpStatus.OK);
//    }
//
////    @GetMapping(value = "/readAll")
////    public ResponseEntity<List<User>> readAll() {
////        return new ResponseEntity<>(userServices.readAll(), HttpStatus.OK);
////    }
//    @PostMapping(value = "/user/create")
//    public ResponseEntity<User> create(@RequestBody User user) {
//        return new ResponseEntity<>(userServices.createUser(user), HttpStatus.CREATED);
//    }
//
//    @PutMapping(value = "/user/update/{id}")
//    public ResponseEntity<User> update(@RequestBody User user,@PathVariable Long id) {
//        return new ResponseEntity<>(userServices.updateUser(id,user), HttpStatus.OK);
//    }
//
//    @DeleteMapping(value = "/user/delete/{id}")
//    public ResponseEntity<User> deleteById(@PathVariable Long id) {
//        return new ResponseEntity<>(userServices.deleteUser(id), HttpStatus.OK);
//    }

        @GetMapping(value = "/home")
    public String displayHome() {
        return "Hello World";
    }

}
