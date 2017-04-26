package pl.lucasjasek.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.lucasjasek.model.User;
import pl.lucasjasek.model.security.VerificationToken;

public interface VerificationTokenRepository extends CrudRepository<VerificationToken, Long> {

    VerificationToken findByToken(String token);

    VerificationToken findByUser(User user);
}
