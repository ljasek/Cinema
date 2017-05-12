package pl.lucasjasek.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lucasjasek.model.User;
import pl.lucasjasek.model.security.PasswordResetToken;
import pl.lucasjasek.repositories.PasswordResetTokenRepository;
import pl.lucasjasek.service.PasswordResetService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
@Transactional
public class PasswordResetServiceImpl implements PasswordResetService {

    public static final String TOKEN_INVALID = "invalidToken";
    public static final String TOKEN_EXPIRED = "expiredPasswordResetToken";

    private final PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    public PasswordResetServiceImpl(PasswordResetTokenRepository passwordResetTokenRepository) {
        this.passwordResetTokenRepository = passwordResetTokenRepository;
    }


    @Override
    public void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken myToken = new PasswordResetToken(token, user);
        passwordResetTokenRepository.save(myToken);
    }

    @Override
    public String validatePasswordResetToken(long id, String token) {

        PasswordResetToken passToken = passwordResetTokenRepository.findByToken(token);
        if ((passToken == null) || (passToken.getUser().getUserId() != id)) {
            return TOKEN_INVALID;
        }

        Calendar cal = Calendar.getInstance();
        if ((passToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            return TOKEN_EXPIRED;
        }

        User user = passToken.getUser();

        List<GrantedAuthority> grantedAuths = new ArrayList<>();
        grantedAuths.add(new SimpleGrantedAuthority("CHANGE_PASSWORD_PRIVILEGE"));

        Authentication auth = new UsernamePasswordAuthenticationToken(user, null, grantedAuths);
        SecurityContextHolder.getContext().setAuthentication(auth);

        passwordResetTokenRepository.delete(passToken);

        return null;
    }

    @Override
    public void resetChangePasswordPrivilege() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }
}