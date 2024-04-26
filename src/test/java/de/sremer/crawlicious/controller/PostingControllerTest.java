package de.sremer.crawlicious.controller;

import de.sremer.crawlicious.ContainersConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Import(ContainersConfig.class)
public class PostingControllerTest {

    @Test
    public void test() {
        assertTrue(true);
    }
}
