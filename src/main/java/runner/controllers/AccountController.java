package runner.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import runner.services.AccountServices;

@RestController
public class AccountController {
    @Autowired
    private AccountServices accountServices;
}
