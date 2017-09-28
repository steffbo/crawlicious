package de.sremer.crawlicious.controller;

import de.sremer.crawlicious.model.User;
import de.sremer.crawlicious.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProfileController {

    static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

    private UserService userService;

    @Autowired
    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = {"/profile"}, method = RequestMethod.GET)
    public ModelAndView profile() {

        User user = userService.getCurrentUser();
        if (user != null) {
            return profileById(user.getId());
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home");
        return modelAndView;
    }

    @RequestMapping(value = {"/profile/{id}"}, method = RequestMethod.GET)
    public ModelAndView profileById(@PathVariable(value = "id") long id) {
        ModelAndView modelAndView = new ModelAndView();

        LOG.debug(String.format("ID: %d", id));
        User user = userService.getOne(id);

        modelAndView.addObject("user", user);
        modelAndView.setViewName("profile");
        return modelAndView;
    }

}
