package com.alpha.bankApp.configuration;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@CrossOrigin(origins = { "http://localhost:3000",
		"http://localhost:5173" } ,allowedHeaders = "*", allowCredentials = "true", methods = { RequestMethod.GET,
				RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS, RequestMethod.PATCH,
				RequestMethod.HEAD, RequestMethod.TRACE })
@Configuration
public class ApplicationConfiguration {
	@Bean
	public OpenAPI usersMicroserviceOpenAPI() {

		Server localhost = new Server();
		localhost.setUrl("http://localhost:8082");
		localhost.setDescription("Local environment");

		Server mainServer = new Server();
		mainServer.setUrl("http://106.51.76.167:8082");
		mainServer.setDescription("Deployment environment");

		Contact contact = new Contact();
		contact.setEmail("info@qsp.in");
		contact.setName("QSP_Demo_Bank");
		contact.setUrl("https://www.demobank.in");

		License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

		Info info = new Info().title("QSP_DEMO_BANK RESTful Web Service documentation").version("1.0").contact(contact)
				.description("This API exposes endpoints to manage QSP_DEMO_BANK.")
				.termsOfService("https://www.qspdemobank.com/terms").license(mitLicense);

		return new OpenAPI().info(info).servers(List.of(localhost, mainServer));
	}

}
