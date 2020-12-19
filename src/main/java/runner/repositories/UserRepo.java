package runner.repositories;

import runner.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends CrudRepository<User,Long> {

    User findUserById(Long Id);

  //  List<User> findAll();

    //update  --save
    //create -- save
    //delete --delete
}
