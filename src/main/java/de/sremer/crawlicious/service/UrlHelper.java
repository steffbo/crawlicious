package de.sremer.crawlicious.service;

import de.sremer.crawlicious.model.Tag;
import de.sremer.crawlicious.model.TagUrl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UrlHelper {

    public String getUrlForTag(String queryString, String tag) {
        if (queryString != null && queryString.contains("tags")) {
            if (queryString.contains(tag)) {
                return null;
            }
            String[] split = queryString.split("&");
            String tags = Arrays.stream(split).filter(s -> s.startsWith("tags=")).findFirst().get();
            return "?" + tags + "%20" + tag;
        } else {
            return "?tags=" + tag;
        }
    }

    public String getUrlWithoutTag(String url, String[] tags, String tag) {
        if (tags == null) {
            return url;
        }

        List<String> tagList = Arrays.asList(tags);
        tagList = tagList.stream().filter(i -> !i.equals(tag)).toList();
        if (tagList.isEmpty()) {
            return url;
        } else {
            return "?tags=" + String.join("%20", tagList);
        }
    }

    public List<TagUrl> getTagUrls(HttpServletRequest request, List<Tag> tagList) {
        return getTagUrls(request, tagList, null);
    }

    public List<TagUrl> getTagUrls(HttpServletRequest request, List<Tag> tagList, List<Tag> relatedTags) {
        ArrayList<TagUrl> tagUrls = new ArrayList<>();
        String decodedQuery = request.getQueryString() != null ? URLDecoder.decode(request.getQueryString(), StandardCharsets.UTF_8) : "";
        String[] requestTags = getTagsFromRequest(request);

        for (Tag tag : tagList) {
            String name = tag.getName();

            boolean isQueryNotNull = request.getQueryString() != null;
            boolean isRelatedTagsNotNull = relatedTags != null;
            boolean isTagInQuery = isQueryNotNull && decodedQuery.contains(tag.getName());
            boolean isTagInRelatedTags = isRelatedTagsNotNull && relatedTags.contains(tag);

            String urlForTag = getUrlForTag(request.getQueryString(), tag.getName());
            String urlWithoutTag = getUrlWithoutTag(request.getRequestURI(), requestTags, tag.getName());

            if (isRelatedTagsNotNull && !isTagInRelatedTags) {
                tagUrls.add(new TagUrl(name, "", "", false));
                continue;
            }
            if (isTagInRelatedTags || !isRelatedTagsNotNull) {
                if (isTagInQuery) {
                    tagUrls.add(new TagUrl(name, urlWithoutTag, "tag-delete", true));
                    continue;
                }
                tagUrls.add(new TagUrl(name, urlForTag, "tag", true));
                continue;
            }
            tagUrls.add(new TagUrl(name, "?tags=" + name, "tag", true));
        }
        return tagUrls;
    }

    private String[] getTagsFromRequest(HttpServletRequest request) {
        if (request.getQueryString() == null || !request.getQueryString().contains("tags=")) {
            return null;
        }
        return request.getParameter("tags").split(" ");
    }
}
