package de.sremer.crawlicious.service;

import de.sremer.crawlicious.model.User;

import java.util.List;

public interface UserService {
    User findUserByEmail(String email);
    List<User> findUserByName(String name);
    void saveUser(User user);
}
