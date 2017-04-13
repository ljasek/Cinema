package pl.lucasjasek.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.lucasjasek.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);

    User findByEmail(String email);
}
