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
    public User createUser(User user)
    {
        return (User) userRepo.save(user);
    }
    public User readUser(Long id)
    {
        return userRepo.findUserById(id);
    }
    public Boolean deleteUser(Long id)
    {
        User userFromDB = userRepo.findUserById(id);
        userRepo.delete(userFromDB);
        return userRepo.existsById(id);
    }

    public User updateUser(Long id ,User user)
    {
        User userFromDB = userRepo.findUserById(id);
        userFromDB.setFirstName(user.getFirstName());
        userFromDB.setLastName(user.getLastName());
        userFromDB.setDateOfBirth(user.getDateOfBirth());
        userFromDB.setSocialSecurity(user.getSocialSecurity());
        userFromDB.setAddress(user.getAddress());
        userFromDB.setAccounts(user.getAccounts());
        return userFromDB;
    }
}