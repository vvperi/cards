package com.nike.microservices.exercise.services.cards;

import com.nike.microservices.exercise.cards.CardsConfiguration;
import com.nike.microservices.exercise.cards.CardsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

import java.util.logging.Logger;

@EnableAutoConfiguration
@EnableDiscoveryClient
@Import(CardsConfiguration.class)
public class CardsServer {
    @Autowired
    protected CardsRepository cardsRepository;

    protected Logger logger = Logger.getLogger(CardsServer.class.getName());

    public static void main(String[] args) {
        System.setProperty("spring.config.name", "cards-server");

        SpringApplication.run(CardsServer.class, args);
    }
}
