package com.luhtom.expensetracker;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import com.luhtom.expensetracker.entity.Customer;

@SpringBootApplication
public class Application /* implements CommandLineRunner */ {
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // @Bean
    // public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
    //     return args -> {

    //         System.out.println("Let's inspect the beans provided by Spring Boot:");

    //         String[] beanNames = ctx.getBeanDefinitionNames();
    //         Arrays.sort(beanNames);
    //         for (String beanName : beanNames) {
    //             System.out.println(beanName);
    //         }

    //     };
    // }

    // @Autowired
    // JdbcTemplate jdbcTemplate;

    // @Override
    // public void run(String... strings) throws Exception {

    //     log.info("Creating tables");

    //     jdbcTemplate.execute("DROP TABLE users IF EXISTS");
    //     jdbcTemplate.execute("""
    //             CREATE TABLE users (
    //                     id INTEGER PRIMARY KEY AUTO_INCREMENT,
    //                     username TEXT NOT NULL UNIQUE,
    //                     password TEXT NOT NULL,
    //                     email TEXT NOT NULL UNIQUE,
    //                     full_name TEXT NOT NULL,
    //                     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    //                 );
    //                 """);

    //     List<Object[]> splitUpNames = Arrays.asList("John Woo",
    //             "Jeff Dean",
    //             "Josh Bloch",
    //             "Josh Long").stream()
    //             .map(name -> name.split(" "))
    //             .collect(Collectors.toList());

    //     splitUpNames.forEach(name -> log.info( //
    //             String.format("Inserting customer record for %s %s", name[0], name[1])));

    //     jdbcTemplate.batchUpdate("""
    //             INSERT INTO customers(first_name, last_name)
    //             VALUES (?,?)""",
    //             splitUpNames);

    //     log.info("Querying for customer records where first_name = 'Josh':");
    //     jdbcTemplate.query("""
    //             SELECT id, first_name, last_name
    //             FROM customers
    //             WHERE first_name = ?""",
    //             (rs, rowNum) -> new Customer(
    //                     rs.getLong("id"),
    //                     rs.getString("first_name"),
    //                     rs.getString("last_name")),
    //             "Josh")
    //             .forEach(customer -> log.info(customer.toString()));
    // }
}
