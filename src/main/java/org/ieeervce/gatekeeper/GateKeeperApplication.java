package org.ieeervce.gatekeeper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@RestController
public class GateKeeperApplication {

	@GetMapping("/")
	String index(){
		return "GateKeeper";
	}

	public static void main(String[] args) {
		SpringApplication.run(GateKeeperApplication.class, args);
	}


}
