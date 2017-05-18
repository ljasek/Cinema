package pl.lucasjasek.registration.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import pl.lucasjasek.model.User;
import pl.lucasjasek.registration.OnRegistrationCompleteEvent;
import pl.lucasjasek.service.SendEmailService;
import pl.lucasjasek.service.VerificationTokenService;

import java.util.UUID;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    private static final String CONFIRM_REGISTRATION_PATH = "/potwierdzenieRejestracji.html?token=";

    private final VerificationTokenService verificationTokenService;
    private final SendEmailService emailService;

    @Autowired
    public RegistrationListener(VerificationTokenService verificationTokenService, SendEmailService emailService) {
        this.verificationTokenService = verificationTokenService;
        this.emailService = emailService;
    }

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        verificationTokenService.createVerificationToken(user, token);

        String confirmationUrl = event.getAppUrl() + CONFIRM_REGISTRATION_PATH + token;
        emailService.sendConfirmRegistrationEmail(user, confirmationUrl);
    }
}