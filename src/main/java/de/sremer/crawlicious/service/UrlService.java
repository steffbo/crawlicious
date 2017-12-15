package de.sremer.crawlicious.service;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UrlService {

    public String getUrlForTag(String queryString, String tag) {

        if (queryString != null && queryString.contains("tags")) {
            if (queryString.contains(tag)) {
                return null;
            }
            return "?" + queryString + "+" + tag;
        } else {
            return "?tag=" + tag;
        }
    }

    public String getUrlWithoutTag(String url, String[] tags, String tag) {

        List<String> tagList = Arrays.asList(tags);
        tagList = tagList.stream().filter(i -> !i.equals(tag)).collect(Collectors.toList());
        if (tagList.isEmpty()) {
            return url;
        } else {
            return "?tags=" + String.join("+", tagList);
        }
    }
}
