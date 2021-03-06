package de.sremer.crawlicious.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.info.BuildProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Properties {

    public static String BUILD_VERSION;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final BuildProperties buildProperties;

    public Properties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    private String getBuildVersion() {
        return buildProperties.getVersion() + "." + buildProperties.get("minor-version");
    }

    @PostConstruct
    public void setWooflesBuild() {
        log.info("build version " + getBuildVersion());
        BUILD_VERSION = getBuildVersion();
    }
}
