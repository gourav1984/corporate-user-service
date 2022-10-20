package co.user.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("co.user.api.data")
public class CorporateUserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CorporateUserServiceApplication.class, args);
	}

}
