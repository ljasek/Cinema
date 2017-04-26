package pl.lucasjasek.registration.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import pl.lucasjasek.model.User;
import pl.lucasjasek.registration.OnRegistrationCompleteEvent;
import pl.lucasjasek.service.UserService;

import java.util.UUID;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    private final UserService service;
    private final JavaMailSender mailSender;

    @Autowired
    public RegistrationListener(UserService service, JavaMailSender mailSender) {
        this.service = service;
        this.mailSender = mailSender;
    }

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        service.createVerificationToken(user, token);

        String recipientAddress = user.getEmail();
        String subject = "Potwierdzenie rejestracji";
        String confirmationUrl = event.getAppUrl() + "/potwierdzenieRejestracji.html?token=" + token;

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText("Kliknij w link aby potwierdzić rejestrację  " + "http://localhost:8080" + confirmationUrl);
        mailSender.send(email);
    }
}