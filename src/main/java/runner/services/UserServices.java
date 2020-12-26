package runner.services;
import runner.entities.Account;
import runner.entities.Address;
import runner.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import runner.repositories.UserRepo;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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

        User user = userRepo.findUserById(id);
        if(user !=null)
        {
            return user;
        }
        else
            return null;
    }


    public Boolean deleteUser(Long id)
    {
        User userFromDB = userRepo.findUserById(id);
        userRepo.delete(userFromDB);
        return !userRepo.existsById(id);
    }

    public User updateUser(Long id ,User user) throws Exception {

        User userFromDB = userRepo.findUserById(id);
        Set<Account> accountSetFromDB = new HashSet<>();
       // Address address = new Address();

        if(userFromDB !=null) {

            userFromDB.setFirstName(user.getFirstName());
            userFromDB.setMiddleName(user.getMiddleName());
            userFromDB.setLastName(user.getLastName());
            userFromDB.setDateOfBirth(user.getDateOfBirth());
            userFromDB.setSocialSecurity(user.getSocialSecurity());
            userFromDB.setEmail(user.getEmail());
            userFromDB.setPhoneNumber(user.getPhoneNumber());
           // address=userFromDB.getAddress();

         //   userFromDB.setAddress(user.getAddress());
           // userFromDB.setLogin(user.getLogin());
            accountSetFromDB = userFromDB.getAccounts();
            for(Account account:user.getAccounts())
            {
                accountSetFromDB.add(account);
            }
            userFromDB.setAccounts(accountSetFromDB);
            userRepo.save(userFromDB);
            return userFromDB;
        }
        else

          throw new Exception("id not found to be udapted");

    }
}