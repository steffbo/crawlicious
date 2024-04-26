package de.sremer.crawlicious;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Import(ContainersConfig.class)
public class MyApplicationTests {

	@Test
	public void contextLoads(ApplicationContext applicationContext) {
		assertNotNull(applicationContext);
	}

}
