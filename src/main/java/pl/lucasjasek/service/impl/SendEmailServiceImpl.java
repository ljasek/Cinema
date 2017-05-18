package pl.lucasjasek.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import pl.lucasjasek.model.User;
import pl.lucasjasek.model.security.VerificationToken;
import pl.lucasjasek.service.SendEmailService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class SendEmailServiceImpl implements SendEmailService{

    private static final Logger LOG = LoggerFactory.getLogger(SendEmailServiceImpl.class);

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    private static final String CONFIRM_REGISTRATION_EMAIL_SUBJECT = "Potwierdzenie rejestracji";
    private static final String RESEND_REGISTRATION_TOKEN_SUBJECT = "Potwierdzenie rejestracji - nowy token";
    private static final String PASSWORD_RESET_SUBJECT = "Reset hasła użytkownika";
    private static final String CONFIRM_REGISTRATION_TEXT = "Kliknij w link aby potwierdzić rejestrację   ";
    private static final String PASSWORD_RESET_TEXT = "Kliknij w link aby zresetować hasło   ";
    private static final String CONFIRMATION_URL = "/potwierdzenieRejestracji.html?token=";
    private static final String CHANGE_PASSWORD_URL = "/zmianaHasla.html?id=";
    private static final String CONFIRMATION_EMAIL_TEMPLATE_NAME = "email/confirmationTemplate";

    @Autowired
    public SendEmailServiceImpl(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    @Override
    public void resendRegistrationToken(VerificationToken newToken, User user, String appUrl) {
        String emailText = CONFIRM_REGISTRATION_TEXT + appUrl + CONFIRMATION_URL + newToken.getToken();
        sendEmailWithTemplate(user, RESEND_REGISTRATION_TOKEN_SUBJECT, emailText);
    }

    @Override
    public void sendConfirmRegistrationEmail(User user, String confirmationUrl) {
        String emailText = CONFIRM_REGISTRATION_TEXT + confirmationUrl;
        sendEmailWithTemplate(user, CONFIRM_REGISTRATION_EMAIL_SUBJECT, emailText);
    }

    @Override
    public void sendPasswordResetToken(String token, User user, String appUrl) {
        StringBuilder emailText = new StringBuilder();
        emailText.append(PASSWORD_RESET_TEXT);
        emailText.append(appUrl);
        emailText.append(CHANGE_PASSWORD_URL);
        emailText.append(user.getUserId());
        emailText.append("&token=");
        emailText.append(token);

        sendEmailWithTemplate(user, PASSWORD_RESET_SUBJECT, emailText.toString());
    }

    @Async
    @Override
    public void sendEmailWithTemplate(User user, String subject, String text) {
        LOG.info("Send email with template");
        LOG.debug("Send email to: {}", user.getEmail());

        Context ctx = new Context();
        ctx.setVariable("name", user.getFirstName());
        ctx.setVariable("subject", subject);
        ctx.setVariable("description", text);

        String htmlContent = this.templateEngine.process(CONFIRMATION_EMAIL_TEMPLATE_NAME, ctx);

        MimeMessage email = this.mailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(email, true);
            message.setTo(user.getEmail());
            message.setSubject(subject);
            message.setText(htmlContent, true);

        } catch (MessagingException e) {
            LOG.info(e.getMessage());
        }

        mailSender.send(email);
    }

    @Async
    @Override
    public void sendEmail(String recipientAddress, String subject, String text ){
        LOG.info("Send email");
        LOG.debug("Send email to: {}", recipientAddress);

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(text);
        try {
            mailSender.send(email);
        }
        catch (MailException e){
            LOG.info("Error Sending Email: {}", e.getMessage());
        }
    }
}