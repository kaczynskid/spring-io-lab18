package io.spring.lab.dashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;
import org.springframework.context.annotation.Bean;

import com.netflix.turbine.streaming.servlet.TurbineStreamServlet;

@SpringBootApplication
@EnableHystrixDashboard
@EnableTurbine
public class DashboardApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(DashboardApplication.class);
		app.setAllowBeanDefinitionOverriding(true);
		app.run(args);
	}

	// org.springframework.cloud.netflix.turbine.TurbineHttpConfiguration
	@Bean
	public ServletRegistrationBean<TurbineStreamServlet> turbineStreamServlet(WebEndpointProperties properties) {
		return new ServletRegistrationBean<>(
				new TurbineStreamServlet(),
				properties.getBasePath() + "/turbine.stream");
	}
}
