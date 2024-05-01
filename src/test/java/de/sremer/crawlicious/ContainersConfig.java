package de.sremer.crawlicious;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration
public class ContainersConfig {

    static String POSTGRES_IMAGE = "postgres:13.14";

    @Bean
    @ServiceConnection
    PostgreSQLContainer<?> postgres() {
        DockerImageName postgres = DockerImageName.parse(POSTGRES_IMAGE);
        return new PostgreSQLContainer<>(postgres);
    }
}
