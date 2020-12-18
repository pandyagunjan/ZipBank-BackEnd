package repositories;

import entities.Login;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoginRepo extends CrudRepository<Login,Long> {

    Login findLoginById(Long id);


}
