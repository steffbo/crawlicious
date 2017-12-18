package de.sremer.crawlicious.model;

import lombok.Data;

@Data
public class TagUrl {

    String name;
    String url;
    String cssClass;
    boolean showTag;

    public TagUrl(String name, String url, String cssClass, boolean showTag) {
        this.name = name;
        this.url = url;
        this.cssClass = cssClass;
        this.showTag = showTag;
    }
}
