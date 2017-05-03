package pl.lucasjasek.registration.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import pl.lucasjasek.model.User;
import pl.lucasjasek.registration.OnRegistrationCompleteEvent;
import pl.lucasjasek.service.SendEmailService;
import pl.lucasjasek.service.UserService;

import java.util.UUID;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    private final UserService service;
    private final SendEmailService emailService;

    @Autowired
    public RegistrationListener(UserService service, SendEmailService emailService) {
        this.service = service;
        this.emailService = emailService;
    }

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        service.createVerificationToken(user, token);

        String confirmationUrl = event.getAppUrl() + "/potwierdzenieRejestracji.html?token=" + token;
        emailService.sendConfirmRegistrationEmail(user, confirmationUrl);
    }
}