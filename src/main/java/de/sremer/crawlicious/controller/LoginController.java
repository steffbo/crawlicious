package de.sremer.crawlicious.controller;

import de.sremer.crawlicious.model.User;
import de.sremer.crawlicious.service.MailService;
import de.sremer.crawlicious.service.SecurityService;
import de.sremer.crawlicious.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;
    private final SecurityService securityService;
    private final MailService mailService;

    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public ModelAndView login() {
        var modelAndView = new UserModelAndView(userService);
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView registration() {
        var modelAndView = new UserModelAndView(userService);

        if (userService.getCurrentUser() == null) {
            User user = new User();
            modelAndView.addObject("user", user);
            modelAndView.setViewName("registration");
        } else {
            modelAndView.setViewName("redirect:/");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Validated User user, BindingResult bindingResult) {
        var modelAndView = new UserModelAndView(userService);
        modelAndView.setViewName("registration");
        User userExists = userService.findUserByEmail(user.getEmail());
        if (userExists != null) {
            bindingResult.rejectValue("email", "error.user",
                    "There is already a user registered with the E-Mail provided");
        }
        if (!bindingResult.hasErrors()) {
            userService.saveUser(user);
            modelAndView.addObject("successMessage", "User has been registered successfully");
        }
        return modelAndView;
    }

    @RequestMapping(value = {"/password_reset"}, method = RequestMethod.GET)
    public ModelAndView passwordResetForm() {
        var modelAndView = new UserModelAndView(userService);
        modelAndView.setViewName("password_reset");
        return modelAndView;
    }

    @RequestMapping(value = {"/password_reset"}, method = RequestMethod.POST)
    public ModelAndView passwordResetSubmitted(String email) {
        var modelAndView = new UserModelAndView(userService);
        User userExists = userService.findUserByEmail(email);
        if (userExists != null) {
            String token = UUID.randomUUID().toString();
            userService.createPasswordResetTokenForUser(userExists, token);
            mailService.send(userExists.getEmail(),
                    "Woofl.es Password reset",
                    "Your password reset token: " + token + "\n" +
                            "https://woofl.es/password_reset_token?token=" + token);
            modelAndView.setViewName("password_reset_thanks");
        } else {
            modelAndView.setViewName("password_reset");
            modelAndView.addObject("emaildoesnotexist", true);
        }
        return modelAndView;
    }

    @RequestMapping(value = {"/password_reset_token"}, method = RequestMethod.GET)
    public ModelAndView resetPasswordToken(@RequestParam String token) {
        String result = securityService.validatePasswordResetToken(token);
        var modelAndView = new UserModelAndView(userService);
        if (result.equals("valid")) {
            modelAndView.setViewName("password_update");
            modelAndView.addObject("token", token);
            return modelAndView;
        }
        modelAndView.setViewName("password_reset");
        return modelAndView;
    }

    @RequestMapping(value = "/password_update", method = RequestMethod.POST)
    public ModelAndView savePassword(String token, String password) {
        var modelAndView = new UserModelAndView(userService);
        User user = securityService.getUserForToken(token);
        securityService.invalidateToken(token);
        if (user != null) {
            userService.changeUserPassword(user, password);
            modelAndView.setViewName("redirect:/");
            return modelAndView;
        }
        modelAndView.setViewName("/?error=invalid_token");
        return modelAndView;
    }

    @RequestMapping(value = "/access-denied")
    public ModelAndView denied() {
        return new UserModelAndView(userService, "denied");
    }
}
