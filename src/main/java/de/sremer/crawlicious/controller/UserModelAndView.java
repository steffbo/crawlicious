package de.sremer.crawlicious.controller;

import de.sremer.crawlicious.service.UserService;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

public class UserModelAndView extends ModelAndView {

    private final UserService userService;

    public UserModelAndView(UserService userService) {
        super();
        this.userService = userService;
        addObjects();
    }

    public UserModelAndView(UserService userService, String viewName) {
        super(viewName);
        this.userService = userService;
        addObjects();
    }

    private void addObjects() {
        Optional<String> role = userService.getHighestCurrentRole();
        this.addObject("userRole", role);
        this.addObject("isUnknownUser", role.isEmpty());
        this.addObject("isUser", role.isPresent());
        this.addObject("isAdmin", role.stream().anyMatch(r -> r.equals("ROLE_ADMIN") || r.equals("ROLE_PREVIOUS_ADMINISTRATOR")));
        this.addObject("isImpersonating", role.stream().anyMatch(r -> r.equals("ROLE_PREVIOUS_ADMINISTRATOR")));
    }
}
