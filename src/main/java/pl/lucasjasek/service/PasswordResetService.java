package pl.lucasjasek.service;

import pl.lucasjasek.model.User;

public interface PasswordResetService {

    void createPasswordResetTokenForUser(User user, String token);

    String validatePasswordResetToken(long id, String token);

    void resetChangePasswordPrivilege();
}