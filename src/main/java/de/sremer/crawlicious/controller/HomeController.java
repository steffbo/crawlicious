package de.sremer.crawlicious.controller;

import de.sremer.crawlicious.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {

    private final UserService userService;

    @RequestMapping(value = {"/"})
    public ModelAndView home() {
        var modelAndView = new UserModelAndView(userService);
        modelAndView.setViewName("home");
        modelAndView.addObject("users", userService.listLastUsers(10));
        return modelAndView;
    }
}
