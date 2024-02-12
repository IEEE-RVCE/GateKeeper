package org.ieeervce.gatekeeper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class GateKeeperApplication {

	@GetMapping("/")
	String index(){
		return "works";
	}
	@GetMapping("/login")
	String login(){
		return "no";
	}
	public static void main(String[] args) {
		SpringApplication.run(GateKeeperApplication.class, args);
	}

}
