package services;

import entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.UserRepo;

import java.util.Optional;

@Service
public class UserServices {
    //CRUD methods
    @Autowired
    private UserRepo userRepo;

    public Optional<User> createUser()
    {
        //Re-direct to POST in USER controller
        return null;
    }

    public Optional<User> readUser()
    {
        //Re-direct to GET in USER controller
        return null;
    }
    public Boolean removeUser()
    {
        //Re-direct to DELETE in USER controller
        return true;
    }

    public Optional<User> updateUser()
    {
        //Re-direct to PUT in USER controller
        return null;
    }

}
