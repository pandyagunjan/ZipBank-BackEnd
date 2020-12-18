package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import services.TransactionServices;

@RestController
public class TransactionController {
    @Autowired
    private TransactionServices transactionServices;
}
