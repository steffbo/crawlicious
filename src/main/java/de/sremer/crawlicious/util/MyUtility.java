package de.sremer.crawlicious.util;

import de.sremer.crawlicious.model.Tag;
import de.sremer.crawlicious.service.TagService;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MyUtility {

    /**
     * Parses a string that the users entered as the tags for a posting.
     * Tags can be separated by , ; ~ & and space.
     *
     * @param userInputTags
     * @return Set of Tags
     */
    public static Set<Tag> parseTags(TagService tagService, String userInputTags) {

        Pattern pattern = Pattern.compile("([^,;~& ]*)*");
        Matcher matcher = pattern.matcher(userInputTags);
        Set<Tag> tagList = new HashSet<>();
        while (matcher.find()) {
            String tagString = matcher.group(0);
            if (!tagString.isEmpty()) {
                tagString = tagString.trim();
                tagList.add(tagService.getTagByName(tagString));
            }
        }
        return tagList;
    }

    public static String getStringFromTags(Set<Tag> tags) {
        return tags.stream().map(Tag::getName).collect(Collectors.joining(" "));
    }

    public static String getWebsiteTitle(String url) {
        InputStream response = null;
        try {
            response = new URL(url).openStream();
            Scanner scanner = new Scanner(response);
            String responseBody = scanner.useDelimiter("\\A").next();
            String title = responseBody.substring(responseBody.indexOf("<title>") + 7, responseBody.indexOf("</title>"));
            return title;
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return "";
    }

    public static void main(String[] args) {
        System.out.println(getWebsiteTitle("https://stackoverflow.com/questions/40099397"));
    }
}
