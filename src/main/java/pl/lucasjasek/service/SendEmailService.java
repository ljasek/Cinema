package pl.lucasjasek.service;

import pl.lucasjasek.model.User;
import pl.lucasjasek.model.security.VerificationToken;

public interface SendEmailService {

    void resendRegistrationToken(VerificationToken newToken, User user);

    void sendConfirmRegistrationEmail(User user, String confirmationUrl);

    void sendPasswordResetToken(String token, User user);

    void sendEmailWithTemplate(User user, String subject, String text );

    void sendEmail(String recipientAddress, String subject, String text );
}