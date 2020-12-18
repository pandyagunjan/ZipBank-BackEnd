package services;

import entities.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.AccountRepo;

import java.util.Optional;

@Service
public class AccountServices {

    @Autowired
    private AccountRepo accountRepo;
    //CRUD methods

    public Optional<Account> createAccount()
    {
        //Re-direct to POST in ACCOUNT controller
        return null;
    }

    public Optional<Account> readAccount()
    {
        //Re-direct to GET in ACCOUNT controller
        return null;
    }
    public Boolean removeAccount()
    {
        //Re-direct to DELETE in ACCOUNT controller
        return true;
    }

    public Optional<Account> updateAccount()
    {
        //Re-direct to PUT in ACCOUNT controller
        return null;
    }

    public Double withdraw(Double amount, Long Id)
    {
        //Login to withdraw from the account
        return 0d;
    }
    public Double deposit(Double amount , Long Id)
    {
        //Login to withdraw from the account
        return 0d;
    }


    public Double transfer(Double amount , Long fromId ,Long toId)
    {
        //Login to withdraw from the account
        return 0d;
    }

}
