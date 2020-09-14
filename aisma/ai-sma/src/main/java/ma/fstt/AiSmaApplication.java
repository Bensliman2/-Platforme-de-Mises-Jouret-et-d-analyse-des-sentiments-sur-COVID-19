package ma.fstt;

//import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class AiSmaApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(AiSmaApplication.class).headless(false).run(args);
		//SpringApplication.run(AiSmaApplication.class, args);
	}

}
