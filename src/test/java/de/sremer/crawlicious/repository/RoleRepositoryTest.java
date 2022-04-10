package de.sremer.crawlicious.repository;

import de.sremer.crawlicious.model.Role;
import de.sremer.crawlicious.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void findRoleByRole() {

        Role admin = roleRepository.findRoleByRole("ROLE_ADMIN");
        Assert.assertEquals(1, admin.getId());
    }

    @Test
    public void findAll() {
        List<Role> all = roleRepository.findAll();
        Assert.assertEquals(2, all.size());
    }

}