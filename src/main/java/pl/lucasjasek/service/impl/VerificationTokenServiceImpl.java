package pl.lucasjasek.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lucasjasek.model.User;
import pl.lucasjasek.model.security.VerificationToken;
import pl.lucasjasek.repositories.UserRepository;
import pl.lucasjasek.repositories.VerificationTokenRepository;
import pl.lucasjasek.service.VerificationTokenService;

import java.util.Calendar;
import java.util.UUID;

@Service
@Transactional
public class VerificationTokenServiceImpl implements VerificationTokenService{

    public static final String TOKEN_INVALID = "invalidToken";
    public static final String TOKEN_EXPIRED = "expiredToken";
    public static final String TOKEN_VALID = "validToken";

    private final UserRepository userRepository;
    private final VerificationTokenRepository tokenRepository;

    @Autowired
    public VerificationTokenServiceImpl(UserRepository userRepository, VerificationTokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
    }


    @Override
    public void createVerificationToken(User user, String token) {
        VerificationToken myToken = new VerificationToken(token, user);
        tokenRepository.save(myToken);
    }

    @Override
    public VerificationToken generateNewVerificationToken(String existingVerificationToken) {
        VerificationToken newToken = tokenRepository.findByToken(existingVerificationToken);
        newToken.updateToken(UUID.randomUUID().toString());
        newToken = tokenRepository.save(newToken);
        return newToken;
    }

    @Override
    public String validateVerificationToken(String token) {
        VerificationToken verificationToken = tokenRepository.findByToken(token);

        if (verificationToken == null) {
            return TOKEN_INVALID;
        }

        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();

        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            return TOKEN_EXPIRED;
        }

        user.setEnabled(true);
        userRepository.save(user);
        tokenRepository.delete(verificationToken);

        return TOKEN_VALID;
    }

    @Override
    public User getUser(String verificationToken) {
        VerificationToken token = tokenRepository.findByToken(verificationToken);

        if (token != null) {
            return token.getUser();
        }
        return null;
    }
}