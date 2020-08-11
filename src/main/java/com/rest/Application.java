package com.rest;

import com.rest.Entity.Person;
import com.rest.Repository.PersonRepository;
import com.rest.Security.SecurityConfiguration;
import com.rest.VaadinUI.MainView;
import com.rest.services.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.time.LocalDate;


@SpringBootApplication(scanBasePackageClasses = {SecurityConfiguration.class, MainView.class, Application.class,
        PersonService.class}, exclude = ErrorMvcAutoConfiguration.class)
@EnableJpaRepositories(basePackageClasses = {PersonRepository.class})
@EntityScan(basePackageClasses = {Person.class})
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    @Bean
    public CommandLineRunner loadData(PersonRepository personRepository) {
        return (args) -> {
            // save a couple of customers
            personRepository.save(new Person(1L, "Harry", "Herring", "380675553311", "Herring@gmail.com", LocalDate.of(1991, 1, 20), true));
            personRepository.save(new Person(2L, "Molly", "Malcolm", "380675553311", "Malcolm@gmail.com", LocalDate.of(1993, 6, 29), false));
            personRepository.save(new Person(3L, "Jack", "Jackson", "380683337799", "Malcolm@gmail.com", LocalDate.of(1901, 6, 11), false));

            // fetch all customers
            log.info("Customers found with findAll():");
            log.info("-------------------------------");
            for (Person customer : personRepository.findAll()) {
                log.info(customer.toString());
            }
            log.info("");

            // fetch an individual customer by ID
            Person customer1 = personRepository.findById(1L).get();
            log.info("Customer found with findOne(1L):");
            log.info("--------------------------------");
            log.info(customer1.toString());
            log.info("");

            // fetch customers by last name
            log.info("Customer found with findByFirstName('Malcolm'):");
            log.info("--------------------------------------------");
            for (Person customer2 : personRepository.findBySecondNameStartsWithIgnoreCase("Malcolm")) {
                log.info(customer2.toString());
            }
            log.info("");
        };
    }
}