package pl.lucasjasek.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.lucasjasek.model.User;
import pl.lucasjasek.model.security.PasswordResetToken;

public interface PasswordResetTokenRepository extends CrudRepository<PasswordResetToken, Integer> {

    PasswordResetToken findByToken(String token);

    PasswordResetToken findByUser(User user);
}