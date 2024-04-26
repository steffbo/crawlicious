package de.sremer.crawlicious.service;

import de.sremer.crawlicious.ContainersConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Import(ContainersConfig.class)
public class PostingServiceTest {

    @Test
    public void testGetPostings() {

        assertTrue(true);
    }
}