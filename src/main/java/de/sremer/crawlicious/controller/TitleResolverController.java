package de.sremer.crawlicious.controller;

import de.sremer.crawlicious.service.external.TitleResolverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
        return titleResolverService.getWebsiteTitle(url);
    }
}
