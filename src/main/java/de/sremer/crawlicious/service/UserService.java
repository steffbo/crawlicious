package de.sremer.crawlicious.service;

import de.sremer.crawlicious.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface UserService {
    User findUserByEmail(String email);

    List<User> findUserByName(String name);

    /**
     * Actually create user.
     *
     * @param user
     */
    void saveUser(User user);

    void update(User user);

    User getOne(long id);

    User getCurrentUser();

    void changeUserPassword(User user, String password);

    Page<User> listAllByPage(Pageable pageable);

    Set<User> listLastUsers(int amount);

    void createPasswordResetTokenForUser(User user, String token);
}
