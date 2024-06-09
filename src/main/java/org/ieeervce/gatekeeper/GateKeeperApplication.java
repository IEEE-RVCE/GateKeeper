package org.ieeervce.gatekeeper;

import org.ieeervce.gatekeeper.dto.Email.EmailDTO;
import org.ieeervce.gatekeeper.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
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
	@Autowired
	EmailService emailService ;
	@GetMapping("/")
	String index() throws Exception{
		EmailDTO emailDTO = new EmailDTO();
		emailDTO.setRecipient("chiragwork2403@gmail.com");
		emailDTO.setMessageBody("hey");
		emailDTO.setSubject("Test mail");
        return emailService.sendSimpleMail(emailDTO);
	}

	public static void main(String[] args) {
		SpringApplication.run(GateKeeperApplication.class, args);
	}


}
