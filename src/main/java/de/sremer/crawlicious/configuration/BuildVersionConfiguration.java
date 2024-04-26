package de.sremer.crawlicious.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BuildVersionConfiguration {

    @Value("${application-version}")
    private String version;

    @Bean(name = "buildVersion")
    public String getVersion() {
        return version;
    }
}
