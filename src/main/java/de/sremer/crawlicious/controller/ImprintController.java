package de.sremer.crawlicious.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ImprintController {

    @GetMapping(value = {"/imprint"})
    public ModelAndView index() {
        return new ModelAndView("imprint");
    }
}
