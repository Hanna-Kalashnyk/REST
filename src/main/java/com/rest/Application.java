package com.rest;

import com.rest.Entity.Person;
import com.rest.Repository.PersonRepository;
import com.rest.Security.SecurityConfiguration;
import com.rest.VaadinUI.MainView;
import com.rest.services.PersonService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//@ActiveProfiles("production-mode")
@SpringBootApplication(scanBasePackageClasses = {SecurityConfiguration.class, MainView.class, Application.class,
        PersonService.class}, exclude = ErrorMvcAutoConfiguration.class)
@EnableJpaRepositories(basePackageClasses = {PersonRepository.class})
@EntityScan(basePackageClasses = {Person.class})
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}