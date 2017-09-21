package de.sremer.crawlicious.service;

import de.sremer.crawlicious.model.Role;
import de.sremer.crawlicious.model.User;
import de.sremer.crawlicious.repository.RoleRepository;
import de.sremer.crawlicious.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public List<User> findUserByName(String name) {
        return userRepository.findUserByName(name);
    }

    @Override
    public void saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRegisteredOn(System.currentTimeMillis());
        user.setEnabled(true);
        Role userRole = roleRepository.findRoleByRole("admin");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        userRepository.save(user);
    }

}