package com.gameloft.profilematcherservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gameloft.profilematcherservice.domain.model.PlayerProfile;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public ReactiveRedisOperations<String, PlayerProfile> playerProfileRedisOps(
            final ReactiveRedisConnectionFactory factory,
            final ObjectMapper objectMapper) {

        final RedisSerializationContext.RedisSerializationContextBuilder<String, PlayerProfile> builder =
                RedisSerializationContext.newSerializationContext(new StringRedisSerializer());
        final Jackson2JsonRedisSerializer<PlayerProfile> valueSerializer = new Jackson2JsonRedisSerializer<>(PlayerProfile.class);
        valueSerializer.setObjectMapper(objectMapper);
        final RedisSerializationContext<String, PlayerProfile> context = builder
                .value(valueSerializer)
                .build();
        return new ReactiveRedisTemplate<>(factory, context);
    }

}
