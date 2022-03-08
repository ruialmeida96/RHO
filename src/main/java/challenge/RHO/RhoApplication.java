package challenge.RHO;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class RhoApplication {

	public static void main(String[] args) {
		SpringApplication.run(RhoApplication.class, args);
	}
}
