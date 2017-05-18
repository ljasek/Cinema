package pl.lucasjasek.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.lucasjasek.model.User;
import pl.lucasjasek.model.security.VerificationToken;
import pl.lucasjasek.registration.OnRegistrationCompleteEvent;
import pl.lucasjasek.service.PasswordResetService;
import pl.lucasjasek.service.SendEmailService;
import pl.lucasjasek.service.UserService;
import pl.lucasjasek.service.VerificationTokenService;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.UUID;

@Controller
public class UserHomeController {

    public static final String CONFIRM_EMAIL = "confirmEmail";
    public static final String VALID_TOKEN = "validToken";
    public static final String RESET_PASSWORD_EMAIL = "resetPasswordEmail";
    public static final String USER_EMAIL_NOT_EXIST = "userEmailNotExist";
    public static final String SUCCESS_SAVE_PASSWORD = "successSavePassword";

    private final UserService userService;
    private final ApplicationEventPublisher eventPublisher;
    private final SendEmailService emailService;
    private final PasswordResetService passwordResetService;
    private final VerificationTokenService verificationTokenService;

    @Autowired
    public UserHomeController(UserService userService, ApplicationEventPublisher eventPublisher, SendEmailService emailService,
                              PasswordResetService passwordResetService, VerificationTokenService verificationTokenService) {
        this.userService = userService;
        this.eventPublisher = eventPublisher;
        this.emailService = emailService;
        this.passwordResetService = passwordResetService;
        this.verificationTokenService = verificationTokenService;
    }

    @GetMapping("/logowanie")
    public String signin(@ModelAttribute("alert") String alert, Model model) {

        model.addAttribute("alert", alert);

        return "signin";
    }

    @GetMapping("/rejestracja")
    public String signup(@ModelAttribute("alert") String alert, @ModelAttribute("token") String token, Model model) {
        User user = new User();

        model.addAttribute("user", user);
        model.addAttribute("alert", alert);
        model.addAttribute("token", token);

        return "signup";
    }

    @PostMapping("/rejestracja")
    public String signupPost(@ModelAttribute("user") User user, HttpServletRequest request, Model model, RedirectAttributes ra) {

        if(userService.checkUserExists(user.getUsername(), user.getEmail()))  {

            if (userService.checkEmailExists(user.getEmail())) {
                model.addAttribute("emailExists", true);
            }

            if (userService.checkUsernameExists(user.getUsername())) {
                model.addAttribute("usernameExists", true);
            }

            return "signup";
        }

        User registered = userService.createUser(user);

        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, getBaseUrl(request)));

        ra.addFlashAttribute("alert", CONFIRM_EMAIL);

        return "redirect:/logowanie";
    }

    @GetMapping("/potwierdzenieRejestracji")
    public String confirmRegistration(@RequestParam("token") String token, RedirectAttributes ra) {

        String result = verificationTokenService.validateVerificationToken(token);

        if (VALID_TOKEN.equals(result)) {

            ra.addFlashAttribute("alert", result);

        return "redirect:/logowanie";
        }

        ra.addFlashAttribute("alert", result);
        ra.addFlashAttribute("token", token);

        return "redirect:/rejestracja";
    }

    @GetMapping("/potwierdzenieRejestracji-nowyToken")
    public String confirmRegistrationNewToken(@RequestParam("token") String existingToken, HttpServletRequest request, RedirectAttributes ra) {

        VerificationToken newToken = verificationTokenService.generateNewVerificationToken(existingToken);
        User user = verificationTokenService.getUser(newToken.getToken());
        emailService.resendRegistrationToken(newToken, user, getBaseUrl(request));

        ra.addFlashAttribute("alert", CONFIRM_EMAIL);

        return "redirect:/logowanie";
    }

    @GetMapping("/resetHasla")
    public String resetPassword(Model model) {

        String email = "";
        model.addAttribute("email", email);

        return "forgotPassword";
    }

    @PostMapping("/resetHasla")
    public String resetPasswordPost(@ModelAttribute("email") String userEmail, HttpServletRequest request, RedirectAttributes ra) {

        User user = userService.findByEmail(userEmail);
        if (user != null) {
            String token = UUID.randomUUID().toString();
            passwordResetService.createPasswordResetTokenForUser(user, token);
            emailService.sendPasswordResetToken(token, user, getBaseUrl(request));

            ra.addFlashAttribute("alert", RESET_PASSWORD_EMAIL);

            return "redirect:/logowanie";
        }

        ra.addFlashAttribute("alert", USER_EMAIL_NOT_EXIST);

        return "redirect:/resetHasla";
    }

    @GetMapping("/zmianaHasla")
    public String showChangePasswordPage(@RequestParam("id") long id, @RequestParam("token") String token, RedirectAttributes ra) {

        String result = passwordResetService.validatePasswordResetToken(id, token);
        if (result != null) {

            ra.addFlashAttribute("alert", result);

            return "redirect:/logowanie";
        }
        return "redirect:/zapisHasla";
    }

    @GetMapping("/zapisHasla")
    public String savePassword(Model model) {

        String newPassword = "";
        model.addAttribute("newPassword", newPassword);

        return "updatePassword";
    }

    @PostMapping("/zapisHasla")
    public String savePasswordPost(@ModelAttribute("newPassword") String newPassword, RedirectAttributes ra) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userService.saveUserPassword(user, newPassword);
        passwordResetService.resetChangePasswordPrivilege();

        ra.addFlashAttribute("alert", SUCCESS_SAVE_PASSWORD);

        return "redirect:/logowanie";
    }

    @GetMapping("/panelUzytkownika")
    public String userFront() {

        return "userFront";
    }

    @GetMapping("/uzytkownik/profil")
    public String profile(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());

        model.addAttribute("user", user);

        return "profile";
    }

    @PostMapping("/uzytkownik/profil")
    public String profilePost(@ModelAttribute("user") User newUser, Model model) {
        User user = userService.findByUsername(newUser.getUsername());
        user.setUsername(newUser.getUsername());
        user.setFirstName(newUser.getFirstName());
        user.setLastName(newUser.getLastName());
        user.setEmail(newUser.getEmail());

        model.addAttribute("user", user);

        userService.saveUser(user);

        return "profile";
    }

    private static String getBaseUrl(HttpServletRequest request) {
        StringBuilder baseUrl = new StringBuilder();
        baseUrl.append(request.getScheme());
        baseUrl.append("://");
        baseUrl.append(request.getServerName());
        baseUrl.append((request.getServerPort() == 80) ? "" : ":" + request.getServerPort());
        baseUrl.append(request.getContextPath());

        return baseUrl.toString();
    }
}