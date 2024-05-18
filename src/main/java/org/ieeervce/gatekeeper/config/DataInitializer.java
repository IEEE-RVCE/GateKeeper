package org.ieeervce.gatekeeper.config;


import org.ieeervce.gatekeeper.service.InitializerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private InitializerService initializerService;

    @Override
    public void run(String... args) throws Exception {
        initializerService.initializeRoles();
    }
}