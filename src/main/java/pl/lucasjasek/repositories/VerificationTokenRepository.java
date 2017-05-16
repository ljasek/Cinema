package pl.lucasjasek.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pl.lucasjasek.model.User;
import pl.lucasjasek.model.security.VerificationToken;

import java.util.Date;

public interface VerificationTokenRepository extends CrudRepository<VerificationToken, Long> {

    VerificationToken findByToken(String token);

    VerificationToken findByUser(User user);

    @Modifying
    @Query("delete from VerificationToken t where t.expiryDate <= :now")
    void deleteAllExpiredSince(@Param("now") Date now);
}