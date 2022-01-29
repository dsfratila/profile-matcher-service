package com.gameloft.profilematcherservice.domain.repo;

import com.gameloft.profilematcherservice.domain.model.PlayerProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class RedisPlayerProfileRepository implements PlayerProfileRepository {

    private static final String KEY_PREFIX = "player:profile:";

    private final ReactiveRedisOperations<String, PlayerProfile> playerProfileRedisOps;

    @Override
    public Mono<PlayerProfile> findById(final String id) {

        return playerProfileRedisOps.opsForValue().get(KEY_PREFIX + id);
    }

    @Override
    public Mono<Boolean> save(final PlayerProfile playerProfile) {

        return playerProfileRedisOps.opsForValue().set(KEY_PREFIX + playerProfile.id(), playerProfile);
    }

}
