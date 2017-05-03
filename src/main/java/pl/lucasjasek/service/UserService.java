package pl.lucasjasek.service;


import pl.lucasjasek.model.User;
import pl.lucasjasek.model.security.UserRole;
import pl.lucasjasek.model.security.VerificationToken;

import java.util.Set;

public interface UserService {

    User findByUsername(String username);

    User findByEmail(String email);

    boolean checkUserExists(String username, String email);

    boolean checkUsernameExists(String username);

    boolean checkEmailExists(String email);

    void save (User user);

    User createUser(User user, Set<UserRole> userRoles);

    User saveUser (User user);

    void createVerificationToken(User user, String token);

    String validateVerificationToken(String token);

    VerificationToken generateNewVerificationToken(String token);

    User getUser(String verificationToken);
}
