package pl.lucasjasek.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pl.lucasjasek.model.User;
import pl.lucasjasek.model.security.PasswordResetToken;

import java.util.Date;

public interface PasswordResetTokenRepository extends CrudRepository<PasswordResetToken, Integer> {

    PasswordResetToken findByToken(String token);

    PasswordResetToken findByUser(User user);

    @Modifying
    @Query("delete from PasswordResetToken t where t.expiryDate <= :now")
    void deleteAllExpiredSince(@Param("now") Date now);
}