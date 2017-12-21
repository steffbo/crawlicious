package de.sremer.crawlicious.controller;

import de.sremer.crawlicious.model.User;
import de.sremer.crawlicious.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SettingsController {

    static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

    private UserService userService;

    @Autowired
    public SettingsController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = {"/settings"}, method = RequestMethod.GET)
    public ModelAndView settings() {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("settings");
        modelAndView.addObject("user", userService.getCurrentUser());
        return modelAndView;
    }

    @PostMapping(value = {"/settings/privateProfile"})
    public ModelAndView privateProfile(@RequestParam(value = "privateProfile", required = true) String privateProfile) {

        User currentUser = userService.getCurrentUser();
        currentUser.setPrivateProfile(privateProfile.equals("true"));
        userService.update(currentUser);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/settings");
        modelAndView.addObject("user", currentUser);
        return modelAndView;
    }

}
