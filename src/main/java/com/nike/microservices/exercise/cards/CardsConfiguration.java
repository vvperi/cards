package com.nike.microservices.exercise.cards;

import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;


@Configuration
@ComponentScan
@EntityScan("com.nike.microservices.exercise.cards")
@EnableJpaRepositories("com.nike.microservices.exercise.cards")
@PropertySource("classpath:db-config.properties")
public class CardsConfiguration {

    protected Logger logger;

    public CardsConfiguration() {
        logger = Logger.getLogger(getClass().getName());
    }

    @Bean
    public DataSource dataSource() {
        logger.info("dataSource() invoked");

        DataSource dataSource = (new EmbeddedDatabaseBuilder()).addScript("classpath:testdb/schema.sql")
                .addScript("classpath:testdb/data.sql").build();

        logger.info("dataSource = " + dataSource);

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Map<String, Object>> cards = jdbcTemplate.queryForList("SELECT name FROM T_CARDS");
        logger.info("System has " + cards.size() + " cards");

        for (Map<String, Object> item : cards) {
            String name = (String) item.get("name");
            jdbcTemplate.update("UPDATE T_CARDS SET DECKNAME = ? WHERE NAME = ?", "Updated_Deck", name);
        }

        return dataSource;
    }
}
