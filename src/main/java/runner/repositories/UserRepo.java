package runner.repositories;

import runner.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo<delete> extends CrudRepository<User,Long> {

    User findUserById(Long Id);

   // List<User> findAll();
   //update  --save
    //create -- save
   // delete --delete
}
