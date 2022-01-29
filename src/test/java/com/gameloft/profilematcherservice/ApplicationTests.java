package com.gameloft.profilematcherservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.gameloft.profilematcherservice.utils.FileUtils.readAsString;

@ActiveProfiles({"mock", "local-redis-docker"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class ApplicationTests {

    private WebTestClient webTestClient;

    @Autowired
    private ApplicationContext applicationContext;

    @BeforeEach
    public void init() {

        webTestClient = WebTestClient.bindToApplicationContext(applicationContext).build();
    }

    @Test
    void getClientConfigWhenPlayerFoundAndMatchesCampaign() {

        webTestClient.get().uri("/get_client_config/{id}", "97983be2-98b7-11e7-90cf-082e5f28d836")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .json(readAsString("static/json/player-config.json"));

    }

    @Test
    void getClientConfigWhenPlayerNotFound() {

        webTestClient.get().uri("/get_client_config/{id}", "123")
                .exchange()
                .expectStatus().isNotFound();
    }

}
