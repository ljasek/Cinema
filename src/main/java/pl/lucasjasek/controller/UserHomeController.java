package pl.lucasjasek.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.lucasjasek.model.User;
import pl.lucasjasek.model.security.UserRole;
import pl.lucasjasek.registration.OnRegistrationCompleteEvent;
import pl.lucasjasek.repositories.RoleRepository;
import pl.lucasjasek.service.UserService;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@Controller
public class UserHomeController {

    private final UserService userService;
    private final RoleRepository roleRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public UserHomeController(UserService userService, RoleRepository roleRepository, ApplicationEventPublisher eventPublisher) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.eventPublisher = eventPublisher;
    }

    @GetMapping("/logowanie")
    public String signin(@ModelAttribute("alert") String alert, Model model) {

        model.addAttribute("alert", alert);

        return "signin";
    }

    @GetMapping("/rejestracja")
    public String signup(@ModelAttribute("alert") String alert, Model model) {
        User user = new User();

        model.addAttribute("user", user);
        model.addAttribute("alert", alert);

        return "signup";
    }

    @PostMapping("/rejestracja")
    public String signupPost(@ModelAttribute("user") User user, WebRequest request, Model model, RedirectAttributes ra) {

        if(userService.checkUserExists(user.getUsername(), user.getEmail()))  {

            if (userService.checkEmailExists(user.getEmail())) {
                model.addAttribute("emailExists", true);
            }

            if (userService.checkUsernameExists(user.getUsername())) {
                model.addAttribute("usernameExists", true);
            }

            return "signup";
        } else {
            Set<UserRole> userRoles = new HashSet<>();
            userRoles.add(new UserRole(user, roleRepository.findByName("ROLE_USER")));

            User registered = userService.createUser(user, userRoles);

            String appUrl = request.getContextPath();
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, appUrl));

            ra.addFlashAttribute("alert", "confirmEmail");

            return "redirect:/logowanie";
        }
    }

    @GetMapping("/potwierdzenieRejestracji")
    public String confirmRegistration(@RequestParam("token") String token, RedirectAttributes ra) {

        String result = userService.validateVerificationToken(token);

        if ("validToken".equals(result)) {

            ra.addFlashAttribute("alert", result);

        return "redirect:/logowanie";
        }

        ra.addFlashAttribute("alert", result);

        return "redirect:/rejestracja";
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
}
