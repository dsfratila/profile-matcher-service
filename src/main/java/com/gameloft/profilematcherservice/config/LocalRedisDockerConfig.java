package com.gameloft.profilematcherservice.config;

import com.gameloft.profilematcherservice.domain.model.PlayerProfile;
import com.gameloft.profilematcherservice.domain.model.PlayerProfileClan;
import com.gameloft.profilematcherservice.domain.model.PlayerProfileDevice;
import com.gameloft.profilematcherservice.domain.repo.PlayerProfileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

import javax.annotation.PreDestroy;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

@Configuration
@Profile("local-redis-docker")
@Slf4j
public class LocalRedisDockerConfig {

    private GenericContainer<?> redisContainer;

    @Bean
    public LettuceConnectionFactory redis() {

        final Integer redisContainerLocalPort = startRedisDocker();
        log.info("Started local redis docker on port {}.", redisContainerLocalPort);
        return new LettuceConnectionFactory("localhost", redisContainerLocalPort);
    }

    @Bean
    public CommandLineRunner initRedisData(final PlayerProfileRepository playerProfileRepository) {

        return args -> playerProfileRepository.save(PlayerProfile.builder()
                .id("97983be2-98b7-11e7-90cf-082e5f28d836")
                .credential("apple_credential")
                .created(ZonedDateTime.parse("2021-01-10T13:37:17Z"))
                .modified(ZonedDateTime.parse("2021-01-23T13:37:17Z"))
                .lastSession(ZonedDateTime.parse("2021-01-23T23:37:17Z"))
                .totalSpent(new BigDecimal("400"))
                .totalRefund(new BigDecimal("0"))
                .totalTransactions(5L)
                .lastPurchase(ZonedDateTime.parse("2021-01-22T13:37:17Z"))
                .level(3L)
                .xp(1000L)
                .totalPlaytime(144L)
                .country("CA")
                .language("fr")
                .birthdate(ZonedDateTime.parse("2000-01-10T13:37:17Z"))
                .genre("male")
                .clan(
                        new PlayerProfileClan("123456", "Hello world clan"))
                .devices(List.of(
                        new PlayerProfileDevice(1, "apple iphone 11", "vodafone", "123")))
                .inventory(Map.of(
                        "cash", 123,
                        "coins", 123,
                        "item_1", 1,
                        "item_34", 3,
                        "item_55", 2
                ))
                .build()).subscribe();
    }

    private Integer startRedisDocker() {

        redisContainer = new GenericContainer<>(DockerImageName.parse("redis:6.2.6"))
                .withExposedPorts(6379);
        redisContainer.start();
        return redisContainer.getFirstMappedPort();
    }

    @PreDestroy
    public void stopServer() {

        log.info("Stopping redis...");
        redisContainer.stop();
        log.info("Redis was stopped!");
    }

}
