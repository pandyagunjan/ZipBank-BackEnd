package repositories;

import entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends CrudRepository<User,Long> {

    User findUserById(Integer Id);

  //  List<User> findAll();

    //update  --save
    //create -- save
    //delete --delete
}
