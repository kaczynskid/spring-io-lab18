package com.example.demo;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(DemoApplication.class);
		app.run(args);
	}

	@Bean
	ApplicationRunner init(Environment env) {
		return args -> {
			String template = env.getProperty("greet.template");
			if (template == null) {
				throw new MissingGreetTemplate();
			}
			log.info("Greet template is: {}", template);
		};
	}

}

class MissingGreetTemplate extends RuntimeException implements ExitCodeGenerator {

	public MissingGreetTemplate() {
		super("Greeting template undefined");
	}

	@Override
	public int getExitCode() {
		return 11;
	}
}

@RestController
@AllArgsConstructor
class GreetingController {

	private GreetingService greeter;

	@GetMapping("/greet/{name}")
	Greeting sayHello(@PathVariable String name) {
		return greeter.greet(name);
	}
}

@Component
@AllArgsConstructor
@EnableConfigurationProperties(GreetingConfig.class)
class GreetingService {

	private final GreetingConfig config;

	Greeting greet(String name) {
		return new Greeting(String.format(config.getTemplate(), name));
	}
}

@Data
@ConfigurationProperties(prefix = "greet")
class GreetingConfig {
	private String template = "Hi %s";
}

@lombok.Value
class Greeting {

	private String message;

	@JsonCreator
	public static Greeting of(@JsonProperty("message") String message) {
		return new Greeting(message);
	}
}
