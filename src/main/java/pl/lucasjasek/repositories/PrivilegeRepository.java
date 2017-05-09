package pl.lucasjasek.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.lucasjasek.model.security.Privilege;

public interface PrivilegeRepository extends CrudRepository<Privilege, Integer> {

    Privilege findByName(String name);
}