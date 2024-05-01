package de.sremer.crawlicious.controller;

import de.sremer.crawlicious.model.User;
import de.sremer.crawlicious.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @GetMapping
    public ModelAndView adminHome() {
        var modelAndView = new UserModelAndView(userService);
        User currentUser = userService.getCurrentUser();
        modelAndView.addObject("userName", "Welcome " + currentUser.getName() + "!");
        modelAndView.addObject("adminMessage", "Content Available Only for Users with Admin Role");
        modelAndView.setViewName("admin/home");
        return modelAndView;
    }

    @GetMapping("/users")
    public ModelAndView adminUsers(HttpServletRequest request, Model model) {
        var modelAndView = new UserModelAndView(userService);
        Set<User> lastUsers = userService.listLastUsers(20);
        modelAndView.addObject("users", lastUsers);
        modelAndView.setViewName("admin/users");
        return modelAndView;
    }

    @GetMapping("/users/delete/{id}")
    public ModelAndView adminUsers(@PathVariable UUID id) {
        userService.deleteUser(id);
        return new UserModelAndView(userService, "redirect:/admin/users");
    }

    @GetMapping("/test")
    public ModelAndView test(HttpServletRequest request, Model model) {
        var modelAndView = new UserModelAndView(userService);
        Set<User> lastUsers = userService.listLastUsers(10);
        modelAndView.addObject("users", lastUsers);
        modelAndView.setViewName("test");
        return modelAndView;
    }

}