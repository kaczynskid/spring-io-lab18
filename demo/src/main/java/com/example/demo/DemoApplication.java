package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}

@RestController
class GreetingController {

	@GetMapping("/greet/{name}")
	Greeting sayHello(@PathVariable String name) {
		return new Greeting(String.format("Hello %s!", name));
	}
}

@lombok.Value
class Greeting {

	private String message;
}

