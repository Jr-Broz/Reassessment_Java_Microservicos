package Reassessment;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching 
@SpringBootApplication
public class InfnetApplication {

	public static void main(String[] args) {
		SpringApplication.run(InfnetApplication.class, args);
	}

} 

// ./mvnw clean compile
// ./mvnw spring-boot:run
// ./mvnw clean spring-boot:run
// docker compose down -v