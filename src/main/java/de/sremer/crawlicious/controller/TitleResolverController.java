package de.sremer.crawlicious.controller;

import de.sremer.crawlicious.service.external.TitleResolverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/title")
public class TitleResolverController {

    private TitleResolverService titleResolverService;

    @Autowired
    TitleResolverController(TitleResolverService titleResolverService) {
        this.titleResolverService = titleResolverService;
    }

    @GetMapping(value = "/resolve", produces = MediaType.TEXT_PLAIN_VALUE)
    public String resolve(@RequestParam(value = "url", required = true) String url) {
        ModelAndView modelAndView = new ModelAndView();
        String title = titleResolverService.getWebsiteTitle(url);
        return title;
//        return Collections.singletonMap("title", title);
    }
}
