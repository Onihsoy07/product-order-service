package com.example.productorderservice.config;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApiTestConfig {

    @Autowired
    private DataBaseCleanUpConfig dataBaseCleanUpConfig;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() throws Exception {
        if (RestAssured.port == RestAssured.UNDEFINED_PORT) {
            RestAssured.port = port;
            dataBaseCleanUpConfig.afterPropertiesSet();
        }
        dataBaseCleanUpConfig.execute();
    }

}
