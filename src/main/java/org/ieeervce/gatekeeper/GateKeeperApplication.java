package org.ieeervce.gatekeeper;

import org.ieeervce.gatekeeper.dto.Email.EmailDTO;
import org.ieeervce.gatekeeper.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@RestController
@EnableAsync
public class GateKeeperApplication {
	@GetMapping("/")
	String index() throws Exception{
		return "gatekeeper";
	}

	public static void main(String[] args) {
		SpringApplication.run(GateKeeperApplication.class, args);
	}


}
