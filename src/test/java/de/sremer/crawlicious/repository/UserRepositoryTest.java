package de.sremer.crawlicious.repository;

import de.sremer.crawlicious.model.Role;
import de.sremer.crawlicious.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;

@SpringBootTest
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void save() {
        User user = new User();
        user.setEmail("user@mail.com");
        user.setName("user");
        user.setRegisteredOn(System.currentTimeMillis());
        user.setPassword("foo");

        Role role = roleRepository.findRoleByRole("ROLE_ADMIN");
        user.addRole(role);

        userRepository.save(user);

        List<User> users = userRepository.findUserByName("user");
        assertSame(users.get(0), user);
    }

    @Test
    public void findUserByEmail() {
    }

    @Test
    public void findUserByName() {
    }

}