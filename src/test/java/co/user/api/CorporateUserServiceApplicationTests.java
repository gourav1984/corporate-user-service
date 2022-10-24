package co.user.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("test")
class CorporateUserServiceApplicationTests {

	@Autowired
	Environment environment;
	@Test
	void contextLoads() {
		assertEquals("test", this.environment.getActiveProfiles()[0]);
	}

}
