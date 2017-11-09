package de.sremer.crawlicious.util;

import de.sremer.crawlicious.model.Tag;
import de.sremer.crawlicious.service.TagService;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MyUtility {

    /**
     * Parses a string that the users entered as the tags for a posting.
     * Tags can be separated by almost everything except _
     *
     * @param userInputTags
     * @return Set of Tags
     */
    public static Set<Tag> parseTags(TagService tagService, String userInputTags) {

        Pattern pattern = Pattern.compile("([^.,_*~+-\\/&]*)*");
        Matcher matcher = pattern.matcher(userInputTags);
        Set<Tag> tagList = new HashSet<>();
        while (matcher.find()) {
            String tagString = matcher.group(0);
            if (!tagString.isEmpty()) {
                tagList.add(tagService.getTagByName(tagString));
            }
        }
        return tagList;
    }

    public static String getStringFromTags(Set<Tag> tags) {
        return tags.stream().map(Tag::getName).collect(Collectors.joining(" "));
    }
}
