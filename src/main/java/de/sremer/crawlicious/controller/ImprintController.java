package de.sremer.crawlicious.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/imprint")
public class ImprintController {

    @GetMapping
    public ModelAndView index() {
        return new ModelAndView("imprint");
    }
}
