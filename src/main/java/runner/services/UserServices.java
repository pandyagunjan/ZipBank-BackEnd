package runner.services;

import runner.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import runner.repositories.UserRepo;

import java.util.Optional;

@Service
public class UserServices {
    //CRUD methods
    @Autowired
    private UserRepo userRepo;

    public Optional<User> createUser(User user)
    {
        //Re-direct to POST in USER controller
        return null;
    }

    public User readUser(Long id)
    {
        //Re-direct to GET in USER controller
        return userRepo.findUserById(id);
    }
    public Boolean deleteUser(Long id)
    {
        //Re-direct to DELETE in USER controller
        return true;
    }

    public Optional<User> updateUser(Long id ,User user)
    {
        //Re-direct to PUT in USER controller
        return null;
    }

}
