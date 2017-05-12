package pl.lucasjasek.service;

import pl.lucasjasek.model.User;
import pl.lucasjasek.model.security.VerificationToken;

public interface VerificationTokenService {

    void createVerificationToken(User user, String token);

    VerificationToken generateNewVerificationToken(String existingVerificationToken);

    String validateVerificationToken(String token);

    User getUser(String verificationToken);
}