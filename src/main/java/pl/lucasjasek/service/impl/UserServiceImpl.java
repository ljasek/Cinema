package pl.lucasjasek.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lucasjasek.model.User;
import pl.lucasjasek.repositories.RoleRepository;
import pl.lucasjasek.repositories.UserRepository;
import pl.lucasjasek.service.UserService;

import java.util.Arrays;

@Service
@Transactional
public class UserServiceImpl implements UserService{

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User createUser(User user) {
        User localUser = userRepository.findByUsername(user.getUsername());

        if (localUser != null) {
            LOG.info("Użytkownik o nazwie {} już istnieje. ", user.getUsername());
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
        localUser = userRepository.save(user);

        return localUser;
    }

    @Override
    public void saveUserPassword(User user, String password) {
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    @Override
    public boolean checkUserExists(String username, String email){
        if (checkUsernameExists(username) || checkEmailExists(username)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean checkUsernameExists(String username) {
        if (null != findByUsername(username)) {
            return true;
        }

        return false;
    }

    @Override
    public boolean checkEmailExists(String email) {
        if (null != findByEmail(email)) {
            return true;
        }
        return false;
    }

    @Override
    public User saveUser (User user) {
        return userRepository.save(user);
    }
}