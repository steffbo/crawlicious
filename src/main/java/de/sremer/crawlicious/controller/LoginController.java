package de.sremer.crawlicious.controller;

import de.sremer.crawlicious.model.User;
import de.sremer.crawlicious.service.SecurityService;
import de.sremer.crawlicious.service.UserService;
import de.sremer.crawlicious.service.mail.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.UUID;

@Controller
public class LoginController {

    static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    @Qualifier("mailgunapi")
    private MailService mailService;

    @RequestMapping(value = {"/"})
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home");
        modelAndView.addObject("users", userService.listLastUsers(10));
        return modelAndView;
    }

    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView registration() {
        ModelAndView modelAndView = new ModelAndView();

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
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("registration");
        User userExists = userService.findUserByEmail(user.getEmail());
        if (userExists != null) {
            bindingResult.rejectValue("email", "error.user",
                    "There is already a user registered with the E-Mail provided");
        }
        if (!bindingResult.hasErrors()) {
            userService.saveUser(user);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.setViewName("registration");
        }
        return modelAndView;
    }

    @RequestMapping(value = {"/password_reset"}, method = RequestMethod.GET)
    public ModelAndView passwordResetForm() {

        ModelAndView modelAndView = new ModelAndView("password_reset");
        return modelAndView;
    }

    @RequestMapping(value = {"/password_reset"}, method = RequestMethod.POST)
    public ModelAndView passwordResetSubmitted(String email) {

        User userExists = userService.findUserByEmail(email);
        if (userExists != null) {
            String token = UUID.randomUUID().toString();
            userService.createPasswordResetTokenForUser(userExists, token);
            mailService.send(userExists.getEmail(),
                    "Woofl.es Password reset",
                    "Your password reset token: " + token + "\n" +
                            "http://woofl.es/password_reset_token?token=" + token);
            ModelAndView modelAndView = new ModelAndView("password_reset_thanks");
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("password_reset");
            modelAndView.addObject("emaildoesnotexist", true);
            return modelAndView;
        }
    }

    @RequestMapping(value = {"/password_reset_token"}, method = RequestMethod.GET)
    public ModelAndView resetPasswordToken(@RequestParam String token) {
        String result = securityService.validatePasswordResetToken(token);
        if (result.equals("valid")) {
            ModelAndView modelAndView = new ModelAndView("password_update");
            modelAndView.addObject("token", token);
            return modelAndView;
        }
        return new ModelAndView("password_reset");
    }

    @RequestMapping(value = "/password_update", method = RequestMethod.POST)
    public ModelAndView savePassword(String token, String password) {
        User user = securityService.getUserForToken(token);
        securityService.invalidateToken(token);
        if (user != null) {
            userService.changeUserPassword(user, password);
            return new ModelAndView("redirect:/");
        }
        return new ModelAndView("/?error=invalid_token");
    }

}