package de.sremer.crawlicious.service;

import de.sremer.crawlicious.model.PasswordResetToken;
import de.sremer.crawlicious.model.Role;
import de.sremer.crawlicious.model.User;
import de.sremer.crawlicious.repository.PasswordRestTokenRepository;
import de.sremer.crawlicious.repository.RoleRepository;
import de.sremer.crawlicious.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("userService")
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PostingService postingService;
    private final PasswordRestTokenRepository passwordResetTokenRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public List<User> findUserByName(String name) {
        return userRepository.findUserByName(name);
    }

    public void saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRegisteredOn(System.currentTimeMillis());
        user.setEnabled(true);
        Role userRole = roleRepository.findRoleByRole("ROLE_USER");
        user.setRoles(new HashSet<>(List.of(userRole)));
        userRepository.save(user);
    }

    public void update(User user) {
        userRepository.save(user);
    }

    @Transactional
    public User getOne(long id) {
        return userRepository.findUserById(id);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(email);
        List<GrantedAuthority> authorities = getUserAuthority(user.getRoles());
        return buildUserForAuthentication(user, authorities);
    }

    private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
        Set<GrantedAuthority> roles = new HashSet<>();
        for (Role role : userRoles) {
            roles.add(new SimpleGrantedAuthority(role.getRole()));
        }

        return new ArrayList<>(roles);
    }

    private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(), user.isEnabled(), true, true, true, authorities);
    }

    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null ? findUserByEmail(auth.getName()) : null;
    }

    public Set<User> listLastUsers(int amount) {
        Page<User> registeredOn = listAllByPage(PageRequest.of(0, amount, Sort.by(Sort.Direction.DESC, "registeredOn")));
        return new TreeSet<>(registeredOn.getContent());
    }

    public void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken myToken = new PasswordResetToken(token, user);
        passwordResetTokenRepository.save(myToken);
    }

    public void changeUserPassword(User user, String password) {
        user.setPassword(bCryptPasswordEncoder.encode(password));
        userRepository.save(user);
    }

    public Page<User> listAllByPage(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public void deleteUser(String id) {
        User user = getOne(Long.parseLong(id));
        postingService.deleteAllPostingsByUser(user);
        userRepository.delete(user);
    }

    public Optional<String> getHighestCurrentRole() {
        if (getCurrentUser() == null) {
            return Optional.empty();
        }
        return getCurrentUser().getRoles().stream()
                .sorted()
                .map(Role::getRole)
                .findFirst();
    }
}