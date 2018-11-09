package eu.isakels.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Clock;

@SpringBootApplication
public class PaymentApp {

    public static void main(String[] args) {
        SpringApplication.run(PaymentApp.class, args);
    }

    // Clock is required only for testing, that's a slight smell of course
    @Bean
    public Clock clock() {
        return Clock.systemUTC();
    }
}
