package runner.repositories;

import runner.entities.Login;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepo extends CrudRepository<Login,Long> {

    Login findLoginById(Long id);


}
