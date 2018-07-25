package com.amyz.projects.awsreadservices;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.amyz.projects.awsreadservices"})
public class AwsReadServicesApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(AwsReadServicesApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {}
}
