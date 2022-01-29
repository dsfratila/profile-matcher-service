package com.gameloft.profilematcherservice.config;

import com.gameloft.profilematcherservice.utils.FileUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.MediaType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.annotation.PreDestroy;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.gameloft.profilematcherservice.utils.FileUtils.*;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

@Configuration
@Profile("mock")
@Slf4j
public class HttpServiceMockConfig {

    private ClientAndServer mockServer;

    @Bean
    public ClientAndServer startServer() {

        mockServer = ClientAndServer.startClientAndServer(8085);
        configureGetActiveCampaignsMock();
        return mockServer;
    }

    @SneakyThrows
    private void configureGetActiveCampaignsMock() {

        mockServer.when(
                request()
                        .withPath("/v1/campaigns/active")
        ).respond(
                response(readAsString("/static/json/active-campaign.json"))
                        .withContentType(MediaType.APPLICATION_JSON));
    }

    @PreDestroy
    public void stopServer() {

        log.info("Stopping mock http server...");
        mockServer.stop();
        log.info("Mock http server was stopped!");
    }

}
