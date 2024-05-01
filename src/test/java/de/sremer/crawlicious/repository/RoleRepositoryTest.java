package de.sremer.crawlicious.repository;

import de.sremer.crawlicious.ContainersConfig;
import de.sremer.crawlicious.model.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertSame;

@SpringBootTest
@Import(ContainersConfig.class)
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void findRoleByRole() {
        Role admin = roleRepository.findRoleByRole("ROLE_ADMIN");
        assertThat(admin).isNotNull();
    }

    @Test
    public void findAll() {
        List<Role> all = roleRepository.findAll();
        assertSame(2, all.size());
    }

}