package pl.lucasjasek.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.lucasjasek.model.security.RememberMeToken;

public interface RememberMeTokenRepository extends CrudRepository<RememberMeToken, String> {

    RememberMeToken findBySeries(String series);
    RememberMeToken findByUsername(String username);
}
