package de.sremer.crawlicious.repository;

import de.sremer.crawlicious.model.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;

@SpringBootTest
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void findRoleByRole() {

        Role admin = roleRepository.findRoleByRole("ROLE_ADMIN");
        assertSame(1, admin.getId());
    }

    @Test
    public void findAll() {
        List<Role> all = roleRepository.findAll();
        assertSame(2, all.size());
    }

}