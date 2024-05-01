package de.sremer.crawlicious.repository;

import de.sremer.crawlicious.ContainersConfig;
import de.sremer.crawlicious.model.Role;
import de.sremer.crawlicious.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Import(ContainersConfig.class)
public class UserRepositoryTest {

    private static final String EMAIL = "foo@bar.com";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    public void save() {
        // given
        User user = new User();
        user.setEmail("user@mail.com");
        user.setName("user");
        user.setRegisteredOn(System.currentTimeMillis());
        user.setPassword("foo");

        Role role = roleRepository.findRoleByRole("ROLE_ADMIN");
        user.addRole(role);

        userRepository.save(user);

        // when
        List<User> users = userRepository.findUserByName("user");

        // then
        assertThat(users.get(0).getId()).isEqualTo(user.getId());
    }

    @Test
    public void findUserByEmail() {
        // given
        userRepository.save(createUser());

        // when
        User user = userRepository.findUserByEmail(EMAIL);

        // then
        assertThat(user).isNotNull();
    }

    private User createUser() {
        User user = new User();
        user.setEmail(EMAIL);
        user.setName("user");
        user.setRegisteredOn(System.currentTimeMillis());
        user.setPassword("foo");
        return user;
    }

}