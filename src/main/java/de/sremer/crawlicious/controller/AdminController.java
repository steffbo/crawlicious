package de.sremer.crawlicious.controller;

import de.sremer.crawlicious.model.User;
import de.sremer.crawlicious.service.UserService;
import de.sremer.crawlicious.service.mail.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

@Controller
public class AdminController {

    static final Logger LOG = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private UserService userService;

    @Autowired
    @Qualifier("mailgunapi")
    private MailService mailService;

    @RequestMapping(value = {"/admin"}, method = RequestMethod.GET)
    public ModelAndView adminHome() {
        ModelAndView modelAndView = new ModelAndView();
        User currentUser = userService.getCurrentUser();
        modelAndView.addObject("userName", "Welcome " + currentUser.getName() + "!");
        modelAndView.addObject("adminMessage", "Content Available Only for Users with Admin Role");
        modelAndView.setViewName("admin/home");
        return modelAndView;
    }

    @RequestMapping(value = {"/test"}, method = RequestMethod.GET)
    public ModelAndView test(HttpServletRequest request, Model model) {
        ModelAndView modelAndView = new ModelAndView();
        Set<User> lastUsers = userService.listLastUsers(10);
        modelAndView.addObject("users", lastUsers);
        modelAndView.setViewName("test");
        modelAndView.addObject("userService", userService);
        return modelAndView;
    }

}