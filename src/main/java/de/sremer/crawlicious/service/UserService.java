package de.sremer.crawlicious.service;

import de.sremer.crawlicious.model.User;

public interface UserService {
    User findUserByName(String name);
    void saveUser(User user);
}
