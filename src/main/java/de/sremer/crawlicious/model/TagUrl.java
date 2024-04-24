package de.sremer.crawlicious.model;

public record TagUrl(
        String name,
        String url,
        String cssClass,
        boolean showTag) {
}
