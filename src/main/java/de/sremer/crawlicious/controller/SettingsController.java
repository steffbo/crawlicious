package de.sremer.crawlicious.controller;

import de.sremer.crawlicious.model.User;
import de.sremer.crawlicious.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/settings")
@RequiredArgsConstructor
public class SettingsController {

    private final UserService userService;

    @GetMapping
    public ModelAndView settings() {

        var modelAndView = new UserModelAndView(userService);
        modelAndView.setViewName("settings");
        modelAndView.addObject("user", userService.getCurrentUser());
        return modelAndView;
    }

    @PostMapping(value = {"/privateProfile"})
    public ModelAndView privateProfile(@RequestParam(value = "privateProfile") String privateProfile) {

        User currentUser = userService.getCurrentUser();
        currentUser.setPrivateProfile(privateProfile.equals("true"));
        userService.update(currentUser);

        var modelAndView = new UserModelAndView(userService);
        modelAndView.setViewName("redirect:/settings");
        modelAndView.addObject("user", currentUser);
        return modelAndView;
    }

}
