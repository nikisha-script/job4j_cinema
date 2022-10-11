package ru.job4j.cinema;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class Application {

    @Value("${server.port:9000}")
    private int port;

    @PostConstruct
    private void printAppUrl() {
        System.out.printf("Go to http://localhost:%d/index%n", port);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
