package com.portfolio.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;

@SpringBootApplication(
        exclude = { DataSourceAutoConfiguration.class }
)
public class UserPortfolioBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserPortfolioBackendApplication.class, args);
    }

}
