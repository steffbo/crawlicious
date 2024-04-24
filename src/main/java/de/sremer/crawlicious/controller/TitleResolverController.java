package de.sremer.crawlicious.controller;

import de.sremer.crawlicious.service.external.TitleResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/title")
@RequiredArgsConstructor
public class TitleResolverController {

    private final TitleResolver titleResolver;

    @GetMapping(value = "/resolve", produces = MediaType.TEXT_PLAIN_VALUE)
    public String resolve(@RequestParam(value = "url") String url) {
        return titleResolver.getWebsiteTitle(url);
    }
}
